package xyz.riocode.scoutpro.scrape.template;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import xyz.riocode.scoutpro.model.CompetitionStatistic;
import xyz.riocode.scoutpro.model.GameStatistic;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.model.PositionStatistic;
import xyz.riocode.scoutpro.scrape.helper.ScrapeHelper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatisticsScrapeTemplateImpl extends WebDriverAbstractScrapeTemplate {

    @Override
    public Player scrape(Player player) {
        Document page = getPage(player.getWhoScoredUrl());
        return scrape(player, page);
    }

    @Override
    public Player scrape(Player player, Document page) {
        scrapeCompetitionStatistics(page, player);
        scrapePositionStatistics(page, player);
        scrapeGameStatistics(page, player);
        return player;
    }

    private void scrapeCompetitionStatistics(Document doc, Player player){
        Elements table = ScrapeHelper.getElements(doc, "div#statistics-table-summary tbody#player-table-statistics-body tr");
        if(!(table.size() == 1 && ScrapeHelper.getElementData(table.get(0), "td").contains("There are no results to display"))) {
            for (int i = 0; i < table.size(); i++) {
                CompetitionStatistic competitionStatistic = null;
                if ((i + 1) != table.size()) {
                    competitionStatistic = extractCoreStats(table.get(i));
                } else {
                    competitionStatistic = extractCoreStatsAverage(table.get(i));
                }
                competitionStatistic.setPlayer(player);
                player.getCompetitionStatistics().add(competitionStatistic);
            }
        }
        player.setStatisticLastCheck(LocalDateTime.now());
    }

    private void scrapePositionStatistics(Document doc, Player player){
        Elements table = ScrapeHelper.getElements(doc, "div#player-positional-statistics tbody tr");
        for (Element row : table) {
            PositionStatistic positionStatistic = new PositionStatistic();
            positionStatistic.setPosition(ScrapeHelper.getElementDataOwn(row, "td.position-name"));
            positionStatistic.setApps(Integer.parseInt(ScrapeHelper.getElementData(row, "td:nth-of-type(2)")));
            positionStatistic.setGoals(Integer.parseInt(ScrapeHelper.getElementData(row, "td:nth-of-type(3)")));
            positionStatistic.setAssists(Integer.parseInt(ScrapeHelper.getElementData(row, "td:nth-of-type(4)")));
            positionStatistic.setRating(new BigDecimal(ScrapeHelper.getElementData(row, "td.rating")));
            positionStatistic.setPlayer(player);
            player.getPositionStatistics().add(positionStatistic);
        }
    }

    private CompetitionStatistic extractCoreStats(Element e){
        CompetitionStatistic competitionStatistic = new CompetitionStatistic();
        competitionStatistic.setCompetition(ScrapeHelper.getElementDataOwn(e, "td.tournament a.tournament-link"));
        String apps = ScrapeHelper.getElementData(e, "td:nth-of-type(2)");
        if(apps.contains("(")){
            apps = apps.replace(")", "");
            String startApps = apps.split("\\(")[0];
            competitionStatistic.setStartedApps(NumberUtils.isCreatable(startApps)?Integer.parseInt(startApps):0);
            String subApps = apps.split("\\(")[1];
            competitionStatistic.setSubApps(NumberUtils.isCreatable(subApps)?Integer.parseInt(subApps):0);
        } else {
            competitionStatistic.setStartedApps(NumberUtils.isCreatable(apps)?Integer.parseInt(apps):0);
            competitionStatistic.setSubApps(0);
        }
        String mins = ScrapeHelper.getElementData(e, "td[class*=minsPlayed]");
        competitionStatistic.setMins(NumberUtils.isCreatable(mins)?Integer.parseInt(mins):0);
        String goals = ScrapeHelper.getElementData(e, "td[class*=goal]");
        competitionStatistic.setGoals(NumberUtils.isCreatable(goals)?Integer.parseInt(goals):0);
        String assists = ScrapeHelper.getElementData(e, "td[class*=assistTotal]");
        competitionStatistic.setAssists(NumberUtils.isCreatable(assists)?Integer.parseInt(assists):0);
        String yellowCards = ScrapeHelper.getElementData(e, "td[class*=yellowCard]");
        competitionStatistic.setYellowCards(NumberUtils.isCreatable(yellowCards)?Integer.parseInt(yellowCards):0);
        String redCards = ScrapeHelper.getElementData(e, "td[class*=redCard]");
        competitionStatistic.setRedCards(NumberUtils.isCreatable(redCards)?Integer.parseInt(redCards):0);
        String shotsPerGame = ScrapeHelper.getElementData(e, "td[class*=shotsPerGame]");
        competitionStatistic.setShotsPerGame(NumberUtils.isCreatable(shotsPerGame)?new BigDecimal(shotsPerGame):BigDecimal.ZERO);
        String passSuccess = ScrapeHelper.getElementData(e, "td[class*=passSuccess]");
        competitionStatistic.setPassSuccess(NumberUtils.isCreatable(passSuccess)?new BigDecimal(passSuccess):BigDecimal.ZERO);
        String aerialsWon = ScrapeHelper.getElementData(e, "td[class*=aerialWonPerGame]");
        competitionStatistic.setAerialsWon(NumberUtils.isCreatable(aerialsWon)?new BigDecimal(aerialsWon):BigDecimal.ZERO);
        String manOfTheMatch = ScrapeHelper.getElementData(e, "td[class*=manOfTheMatch]");
        competitionStatistic.setManOfTheMatch(NumberUtils.isCreatable(manOfTheMatch)?Integer.parseInt(manOfTheMatch):0);
        String rating = ScrapeHelper.getElementData(e, "td[class*=rating]");
        competitionStatistic.setRating(NumberUtils.isCreatable(rating)?new BigDecimal(rating):BigDecimal.ZERO);

        return competitionStatistic;
    }

    private CompetitionStatistic extractCoreStatsAverage(Element e){
        CompetitionStatistic competitionStatistic = new CompetitionStatistic();
        Elements elements = ScrapeHelper.getElements(e, "td strong");
        competitionStatistic.setCompetition(elements.get(0).text());
        competitionStatistic.setStartedApps(NumberUtils.isCreatable(elements.get(1).text())?Integer.parseInt(elements.get(1).text()):0);
        competitionStatistic.setSubApps(0);
        competitionStatistic.setMins(NumberUtils.isCreatable(elements.get(2).text())?Integer.parseInt(elements.get(2).text()):0);
        competitionStatistic.setGoals(NumberUtils.isCreatable(elements.get(3).text())?Integer.parseInt(elements.get(3).text()):0);
        competitionStatistic.setAssists(NumberUtils.isCreatable(elements.get(4).text())?Integer.parseInt(elements.get(4).text()):0);
        competitionStatistic.setYellowCards(NumberUtils.isCreatable(elements.get(5).text())?Integer.parseInt(elements.get(5).text()):0);
        competitionStatistic.setRedCards(NumberUtils.isCreatable(elements.get(6).text())?Integer.parseInt(elements.get(6).text()):0);
        competitionStatistic.setShotsPerGame(NumberUtils.isCreatable(elements.get(7).text())?new BigDecimal(elements.get(7).text()):BigDecimal.ZERO);
        competitionStatistic.setPassSuccess(NumberUtils.isCreatable(elements.get(8).text())?new BigDecimal(elements.get(8).text()):BigDecimal.ZERO);
        competitionStatistic.setAerialsWon(NumberUtils.isCreatable(elements.get(9).text())?new BigDecimal(elements.get(9).text()):BigDecimal.ZERO);
        competitionStatistic.setManOfTheMatch(NumberUtils.isCreatable(elements.get(10).text())?Integer.parseInt(elements.get(10).text()):0);
        competitionStatistic.setRating(NumberUtils.isCreatable(elements.get(11).text())?new BigDecimal(elements.get(11).text()):BigDecimal.ZERO);

        return competitionStatistic;
    }

    private void scrapeGameStatistics(Document doc, Player player){
        Elements elements = ScrapeHelper.getElements(doc, "table#player-fixture tbody tr");
        for (Element e : elements) {
            GameStatistic gameStatistic = new GameStatistic();
            gameStatistic.setCompetition(ScrapeHelper.getAttributeValue(e, "td.tournament span a", "title"));
            gameStatistic.setDateOfGame(LocalDate.parse(ScrapeHelper.getElementData(e, "td.date"), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            gameStatistic.setTeam1(ScrapeHelper.getElementData(e, "td.home a"));
            gameStatistic.setTeam2(ScrapeHelper.getElementData(e, "td.away a"));
            String result = ScrapeHelper.getElementData(e, "td.result a");
            gameStatistic.setResult(result.replaceAll("[^\\d:]", ""));
            String minutesPlayed = ScrapeHelper.getElementData(e, "td.info").replaceAll("[^\\d]","");
            gameStatistic.setMinutesPlayed(NumberUtils.isCreatable(minutesPlayed)?Integer.parseInt(minutesPlayed):0);
            String rating = ScrapeHelper.getElementData(e, "td.rating");
            gameStatistic.setRating(NumberUtils.isCreatable(rating)?new BigDecimal(rating):BigDecimal.ZERO);
            gameStatistic.setManOfTheMatch(isManOfTheMatch(e));

            Element el = ScrapeHelper.getElement(e, "td:nth-of-type(7)");
            gameStatistic.setGoals(extractAchievements(el, "Goal"));
            gameStatistic.setAssists(extractAchievements(el, "Assist"));
            gameStatistic.setYellowCard(extractYellowCard(el));
            gameStatistic.setRedCard(extractRedCard(el));
            gameStatistic.setPlayer(player);
            player.getGameStatistics().add(gameStatistic);
        }
    }

    private int extractAchievements(Element e, String achievement){
        Elements achievements = ScrapeHelper.getElements(e, "span span[title="+ achievement +"]");
        return achievements.size();
    }

    private boolean isManOfTheMatch(Element e){
        return ScrapeHelper.getElement(e, "td:nth-of-type(6) span") != null;
    }

    private boolean extractYellowCard(Element e){
        return ScrapeHelper.getElement(e, "span span[title=Yellow Card]") != null;
    }

    private boolean extractRedCard(Element e){
        return ScrapeHelper.getElement(e, "span span[title=Red Card]") != null;
    }

    @Override
    public Document getPage(String url) {//#qcCmpUi .qc-cmp-button
        WebDriver driver = null;
        String html = null;
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        try {
            driver = new ChromeDriver(chromeOptions);
            driver.get(url);
            Thread.sleep(5000);
            driver.findElement(By.cssSelector("#qcCmpUi .qc-cmp-button")).click();
            Thread.sleep(5000);
            html = driver.getPageSource();
        } catch (InterruptedException e){

        } finally {
            if(driver != null)
                driver.quit();
        }
        return Jsoup.parse(html);
    }
}
