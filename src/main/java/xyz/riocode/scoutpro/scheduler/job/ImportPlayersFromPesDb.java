package xyz.riocode.scoutpro.scheduler.job;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.repository.PlayerRepository;
import xyz.riocode.scoutpro.scrape.helper.ScrapeHelper;
import xyz.riocode.scoutpro.scrape.page.PsmlPageSupplierImpl;
import xyz.riocode.scoutpro.service.PlayerService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ImportPlayersFromPesDb extends QuartzJobBean {

    private static final String PSML_BASE_URL = "https://psml.rs/index.php";
    private static final String PSML_SEARCH_BASE_URL = PSML_BASE_URL + "?action=aps&q=&pesdblink=";

    private static final int overallLimit = 80;

    @Autowired
    private PlayerService playerService;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PsmlPageSupplierImpl psmlPageSupplier;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String pesDbBaseUrl = jobExecutionContext.getMergedJobDataMap().getString("pesDbBaseUrl");
        int sleepTimeBetweenPlayers = jobExecutionContext.getMergedJobDataMap().getInt("sleepTimeBetweenPlayers");
        log.info("ImportPlayers job start -> [PesDbBaseUrl:{}, sleepTimeBetweenPlayers:{}]", pesDbBaseUrl, sleepTimeBetweenPlayers);
        String pesDbSearchPageUrl = pesDbBaseUrl + "/?page=";

        Document document;
        Map<String, String> playersData;
        List<String[]> playersWithError = new ArrayList<>();
        int playersFound = 0;
        int playersImported = 0;
        int playersExist = 0;
        int page = 0;
        while(true) {
            try {
                Thread.sleep(7000);
                page++;
                document = ScrapeHelper.getPage(pesDbSearchPageUrl + page);
                playersData = scrapeTableByOverall(document, overallLimit);
                playersFound += playersData.size();
                log.info("Players to scrape: {}", playersData);
                if (!playersData.isEmpty()) {
                    for (Map.Entry<String, String> e : playersData.entrySet()) {
                        log.info("page: {}, next: {}, found: {}, imported: {}, exist: {}, errors: {}",
                                page, e.getKey(), playersFound, playersImported, playersExist, playersWithError);
                        if (playerRepository.findByPesDbName(e.getKey()) != null) {
                            log.info("Player {} exists", e.getKey());
                            playersExist++;
                            continue;
                        }
                        try {
                            Thread.sleep(sleepTimeBetweenPlayers);
                            Document psmlSearchResult = psmlPageSupplier.getPage(PSML_SEARCH_BASE_URL + pesDbBaseUrl + e.getValue());
                            Element psmlPlayer = ScrapeHelper.getElement(psmlSearchResult, "table.style2 tr:nth-of-type(2)");
                            if (ScrapeHelper.getElement(psmlPlayer, "td:nth-of-type(1) a") == null) {
                                log.warn("Search result is empty for player: {}", e.getKey());
                                playersWithError.add(new String[]{e.getKey(), "Search result empty"});
                                continue;
                            }
                            String psmlQueryUrl = ScrapeHelper.getAttributeValue(psmlPlayer, "td:nth-of-type(1) a", "href");
                            String transfermarktUrl = ScrapeHelper.getAttributeValue(psmlPlayer, "td:nth-of-type(3) a", "href");
                            Thread.sleep(15000);
                            Player player = new Player();
                            player.setTransfermarktUrl(transfermarktUrl);
                            player.setPesDbUrl(pesDbBaseUrl + e.getValue());
                            player.setPsmlUrl(PSML_BASE_URL + psmlQueryUrl);
                            playerService.create(player);
                            log.info("{} ({}) - {} is imported", player.getName(), player.getPrimaryPosition(), player.getOverallRating());
                            playersImported++;
                        } catch (Exception ex) {
                            log.error(ex.getMessage(), ex);
                            playersWithError.add(new String[]{e.getKey(), ex.getClass().getName()});
                        }
                    }
                } else {
                    log.info("ImportPlayers job end -> found: {}, imported: {}, exist: {}", playersFound, playersImported, playersExist);
                    log.info("Unsuccessful scrape for players:");
                    for(String[] s : playersWithError){
                        log.info(Arrays.toString(s));
                    }
                    // insert job result into database
                    break;
                }
            } catch(Exception ex){
                log.error(ex.getMessage(), ex);
            }
        }
    }

    private static Map<String, String> scrapeTableByOverall(Document doc, int overallLimit){
        Elements players = ScrapeHelper.getElements(doc, "table.players tr:not(:first-child)");
        return players.stream()
            .filter(e -> Integer.parseInt(ScrapeHelper.getElementDataOwn(e, "td:nth-of-type(9)")) >= overallLimit)
            .collect(Collectors.toMap(
                    e -> ScrapeHelper.getElementData(e, "td:nth-of-type(2) a"),
                    e -> ScrapeHelper.getAttributeValue(e, "td:nth-of-type(2) a", "href")
                            .replaceAll("\\.", "")));

    }
}
