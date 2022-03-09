package xyz.riocode.scoutpro.scheduler.job;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.repository.PlayerRepository;
import xyz.riocode.scoutpro.scrape.engine.ScrapeLoader;
import xyz.riocode.scoutpro.scrape.helper.ScrapeHelper;
import xyz.riocode.scoutpro.scrape.loader.PsmlPageLoaderImpl;
import xyz.riocode.scoutpro.service.PlayerService;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ImportPlayersFromPsml extends QuartzJobBean {

    private static final String PSML_BASE_URL = "https://psml.rs/index.php";
    private static final String PSML_DIVISION_URL = "https://psml.rs/?action=atls&division=";

    @Autowired
    private PlayerService playerService;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PsmlPageLoaderImpl psmlPageLoader;
    @Autowired
    private ScrapeLoader scrapeLoader;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("ImportPlayersFromPsml job start");

        int playersFound = 0;
        int playersImported = 0;
        int playersExist = 0;
        int teamCount = 0;

        Document divisionPage;
        Map<String, String> teamsData;
        Map<String, String> playersData;
        List<String[]> playersWithError = new ArrayList<>();

        for (int i = 1; i < 3; i++) {
            try {
                Thread.sleep(7000);
                divisionPage = ScrapeHelper.createDocument(
                        scrapeLoader.loadAndGetPageContent(new URL(PSML_DIVISION_URL + i), psmlPageLoader));
                teamsData = scrapeTeams(divisionPage);
                for (Map.Entry<String, String> team : teamsData.entrySet()) {
                    try {
                        teamCount++;
                        Thread.sleep(10000);
                        Document teamPage = ScrapeHelper.createDocument(scrapeLoader.loadAndGetPageContent(new URL(PSML_BASE_URL + team.getValue()), psmlPageLoader));
                        playersData = scrapePlayers(teamPage);
                        playersFound += playersData.size();
                        for (Map.Entry<String, String> playerData : playersData.entrySet()) {
                            if (playerRepository.findByPesDbName(playerData.getKey()) != null) {
                                log.info("Player {} exists", playerData.getKey());
                                playersExist++;
                                continue;
                            }
                            try {
                                log.info("division: {}, team: {}, teamCount: {}, player: {}", i, team.getKey(), teamCount, playerData.getKey());
                                log.info("found: {}, imported: {}, exist: {}, errors: {}", playersFound, playersImported, playersExist, playersWithError);
                                String psmlUrl = PSML_BASE_URL + playerData.getValue();
                                Document playerPage = ScrapeHelper.createDocument(scrapeLoader.loadAndGetPageContent(new URL(psmlUrl), psmlPageLoader));
                                String pesDbUrl = ScrapeHelper.getAttributeValue(playerPage, "table.innerTable tr:nth-of-type(2) td:nth-of-type(1) p a", "href");
                                String tmUrl = ScrapeHelper.getAttributeValue(playerPage, "table.innerTable tr:nth-of-type(2) td:nth-of-type(3) p a", "href");
                                if (playerRepository.findByTransfermarktUrl(tmUrl) != null) {
                                    log.info("Player {} exists", playerData.getKey());
                                    playersExist++;
                                    continue;
                                }
                                Thread.sleep(15000);
                                Player player = new Player();
                                player.setTransfermarktUrl(tmUrl);
                                player.setPesDbUrl(pesDbUrl);
                                player.setPsmlUrl(psmlUrl);
                                playerService.create(player);
                                playersImported++;
                                log.info("{} ({}) - {} is imported", player.getName(), player.getPrimaryPosition(), player.getOverallRating());
                            } catch (Exception ex) {
                                log.error(ex.getMessage(), ex);
                                playersWithError.add(new String[]{playerData.getKey(), ex.getClass().getName()});
                            }
                        }
                    } catch (Exception ex) {
                        log.error(ex.getMessage(), ex);
                    }
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        log.info("ImportPlayersFromPsml job end");
        log.info("found: {}, imported: {}, exist: {}", playersFound, playersImported, playersExist);
        log.info("Unsuccessful scrape for players:");
        for (String[] s : playersWithError) log.info(Arrays.toString(s));
    }

    private Map<String, String> scrapeTeams(Document divisionPage) {
        Elements teams = ScrapeHelper.getElements(divisionPage, "table.style3 tbody tr");
        return teams.stream()
                .collect(Collectors.toMap(e -> ScrapeHelper.getElementData(e, "a"),
                                            e -> ScrapeHelper.getAttributeValue(e, "a", "href")));
    }

    private Map<String, String> scrapePlayers(Document teamPage) {
        Elements players = ScrapeHelper.getElements(teamPage, "#squad tbody tr:has(td:has(a))");
        return players.stream()
                .collect(Collectors.toMap(e -> ScrapeHelper.getElementData(e, "td:nth-of-type(1) a"),
                                            e -> ScrapeHelper.getAttributeValue(e, "td:nth-of-type(1) a", "href")));
    }
}