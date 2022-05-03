package xyz.riocode.scoutpro.scrape.template;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.model.CompetitionStatistic;
import xyz.riocode.scoutpro.model.GameStatistic;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.scrape.helper.ScrapeHelper;
import xyz.riocode.scoutpro.scrape.model.ScrapeField;
import xyz.riocode.scoutpro.scrape.repository.ScrapeFieldRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Component("TMStatsTemplate")
public class TMStatsScrapeTemplateImpl implements ScrapeTemplate{

    private final ScrapeFieldRepository scrapeFieldRepository;

    public TMStatsScrapeTemplateImpl(ScrapeFieldRepository scrapeFieldRepository) {
        this.scrapeFieldRepository = scrapeFieldRepository;
    }

    @Override
    public void scrape(String pageContent, Player player) {
        Map<String, String> scrapeFields = scrapeFieldRepository.findByScrapeSite_Name("TM Stats").stream()
                .collect(Collectors.toMap(ScrapeField::getName, ScrapeField::getSelector));
        Document page = ScrapeHelper.createDocument(pageContent);
        scrapeCompetitionsStats(page, player, scrapeFields);
        scrapeGamesStats(page, player, scrapeFields);
        player.setStatisticLastCheck(LocalDateTime.now());
    }

    protected void scrapeCompetitionsStats(Document doc, Player player, Map<String, String> scrapeFields) {
        Elements competitionElements = ScrapeHelper.getElements(doc, "div.large-8 div.box:nth-of-type(2) div.responsive-table tbody tr");
        for (Element e : competitionElements) {
            String competitionName = ScrapeHelper.getElementData(e, "td:nth-of-type(2) a");
            String appearances = ScrapeHelper.getElementData(e, "td:nth-of-type(3) a");
            String goals = ScrapeHelper.getElementData(e, "td:nth-of-type(4)");
            String assists = ScrapeHelper.getElementData(e, "td:nth-of-type(5)");
            String yellowCards = ScrapeHelper.getElementData(e, "td:nth-of-type(6)");
            String redCards = ScrapeHelper.getElementData(e, "td:nth-of-type(8)");
            String minutesPlayed = ScrapeHelper.getElementData(e, "td:nth-of-type(9)");
            player.getCompetitionStatistics().add(CompetitionStatistic.builder()
                                .competitionName(competitionName)
                                .appearances(appearances)
                                .goals(goals)
                                .assists(assists)
                                .yellowCards(yellowCards)
                                .redCards(redCards)
                                .minutesPlayed(minutesPlayed)
                                .player(player)
                                .build()
            );
        }
    }

    protected void scrapeGamesStats(Document doc, Player player, Map<String, String> scrapeFields) {
        Elements gameElements = ScrapeHelper.getElements(doc, "div.large-8 div.box:nth-of-type(n+3) div.responsive-table tbody tr");
        for (Element e : gameElements) {
            LocalDate dateOfGame = LocalDate.parse(ScrapeHelper.getElementData(e, "tr td:nth-of-type(2)"),
                    DateTimeFormatter.ofPattern("M/d/yy").withLocale(Locale.US));
            String homeAwayFlag = ScrapeHelper.getElementData(e, "td:nth-of-type(3)");
            String playerTeam = ScrapeHelper.getAttributeValue(e, "td:nth-of-type(4) a", "title");
            String opponentTeam = null;
            String result = null;
            String position = "";
            String goals = "";
            String assists = "";
            boolean yellowCard = false;
            boolean redCard = false;
            String minutesPlayed = "";
            String notPlayingReason = "";
            if (ScrapeHelper.getAttributeValue(e, "td:nth-of-type(4)", "colspan").isEmpty()) {
                opponentTeam = ScrapeHelper.getAttributeValue(e, "td:nth-of-type(6) a", "title");
                result = ScrapeHelper.getElementData(e, "td:nth-of-type(8) a span");
                if (ScrapeHelper.getElement(e, "td:nth-of-type(9) a") != null) {
                    position = ScrapeHelper.getElementData(e, "td:nth-of-type(9) a");
                    goals = ScrapeHelper.getElementData(e, "td:nth-of-type(10)");
                    assists = ScrapeHelper.getElementData(e, "td:nth-of-type(11)");
                    yellowCard = !ScrapeHelper.getElementData(e, "td:nth-of-type(12)").isEmpty();
                    redCard = !ScrapeHelper.getElementData(e, "td:nth-of-type(14)").isEmpty();
                    minutesPlayed = ScrapeHelper.getElementData(e, "td:nth-of-type(15)");
                } else {
                    notPlayingReason = ScrapeHelper.getElementDataOwn(e, "td:nth-of-type(9)");
                }
            } else {
                opponentTeam = ScrapeHelper.getAttributeValue(e, "td:nth-of-type(5) a", "title");
                result = ScrapeHelper.getElementData(e, "td:nth-of-type(7) a span");
                if (ScrapeHelper.getElement(e, "td:nth-of-type(8) a") != null) {
                    position = ScrapeHelper.getElementData(e, "td:nth-of-type(8) a");
                    goals = ScrapeHelper.getElementData(e, "td:nth-of-type(9)");
                    assists = ScrapeHelper.getElementData(e, "td:nth-of-type(10)");
                    yellowCard = !ScrapeHelper.getElementData(e, "td:nth-of-type(11)").isEmpty();
                    redCard = !ScrapeHelper.getElementData(e, "td:nth-of-type(13)").isEmpty();
                    minutesPlayed = ScrapeHelper.getElementData(e, "td:nth-of-type(14)");
                } else {
                    notPlayingReason = ScrapeHelper.getElementDataOwn(e, "td:nth-of-type(8)");
                }
            }

            player.getGameStatistics().add(GameStatistic.builder()
                            .dateOfGame(dateOfGame)
                            .playerTeam(playerTeam)
                            .opponentTeam(opponentTeam)
                            .homeAwayFlag(homeAwayFlag)
                            .result(result)
                            .position(position)
                            .goals(goals)
                            .assists(assists)
                            .yellowCard(yellowCard)
                            .redCard(redCard)
                            .minutesPlayed(minutesPlayed)
                            .notPlayedReason(notPlayingReason)
                            .player(player)
                            .build()
            );
        }
    }
}
