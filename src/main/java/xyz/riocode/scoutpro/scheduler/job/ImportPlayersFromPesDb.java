package xyz.riocode.scoutpro.scheduler.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.repository.PlayerRepository;
import xyz.riocode.scoutpro.scheduler.enums.JobExecutionStatus;
import xyz.riocode.scoutpro.scheduler.model.JobExecutionHistory;
import xyz.riocode.scoutpro.scheduler.model.JobInfo;
import xyz.riocode.scoutpro.scheduler.repository.JobExecutionHistoryRepository;
import xyz.riocode.scoutpro.scheduler.repository.JobInfoRepository;
import xyz.riocode.scoutpro.scrape.engine.ScrapeLoader;
import xyz.riocode.scoutpro.scrape.helper.ScrapeHelper;
import xyz.riocode.scoutpro.scrape.loader.PsmlPageLoaderImpl;
import xyz.riocode.scoutpro.scrape.model.ScrapeErrorHistory;
import xyz.riocode.scoutpro.scrape.repository.ScrapeErrorHistoryRepository;
import xyz.riocode.scoutpro.service.PlayerService;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ImportPlayersFromPesDb extends QuartzJobBean {

    private static final String PSML_BASE_URL = "https://pc.psml.rs/index.php";
    private static final String PSML_SEARCH_BASE_URL = PSML_BASE_URL + "?action=aps&q=&pesdblink=";

    private static final int overallLimit = 80;

    @Autowired
    private PlayerService playerService;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PsmlPageLoaderImpl psmlPageLoader;
    @Autowired
    private ScrapeLoader scrapeLoader;
    @Autowired
    private JobInfoRepository jobInfoRepository;
    @Autowired
    private JobExecutionHistoryRepository jobExecutionHistoryRepository;
    @Autowired
    private ScrapeErrorHistoryRepository scrapeErrorHistoryRepository;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("ImportPlayersFromPesDb job start");
        JobInfo jobInfo = jobInfoRepository.findByJobNameAndJobGroup(jobExecutionContext.getJobDetail().getKey().getName(),
                jobExecutionContext.getJobDetail().getKey().getGroup());
        JobExecutionHistory jobExecutionHistory = JobExecutionHistory.builder()
                .startTime(LocalDateTime.now())
                .status(JobExecutionStatus.SUCCESS)
                .jobInfo(jobInfo)
                .build();

        String pesDbBaseUrl = jobExecutionContext.getMergedJobDataMap().getString("pesDbBaseUrl");
        int sleepTimeBetweenPlayers = jobExecutionContext.getMergedJobDataMap().getInt("sleepTimeBetweenPlayers");
        String pesDbSearchPageUrl = pesDbBaseUrl + "/?page=";

        Document document;
        Map<String, String> playersData;
        List<String[]> playersWithError = new ArrayList<>();
        long playersFound = 0;
        long playersImported = 0;
        int playersExist = 0;
        int page = 0;
        while(true) {
            try {
                Thread.sleep(7000);
                page++;
                document = ScrapeHelper.getPage(pesDbSearchPageUrl + page);
                playersData = scrapeTableByOverall(document, overallLimit);
                playersFound += playersData.size();
                log.debug("Players to scrape: {}", playersData);
                if (!playersData.isEmpty()) {
                    for (Map.Entry<String, String> e : playersData.entrySet()) {
                        log.debug("page: {}, next: {}, found: {}, imported: {}, exist: {}, errors: {}",
                                page, e.getKey(), playersFound, playersImported, playersExist, playersWithError);
                        if (playerRepository.findByPesDbName(e.getKey()) != null) {
                            log.debug("Player {} exists", e.getKey());
                            playersExist++;
                            continue;
                        }
                        try {
                            Thread.sleep(sleepTimeBetweenPlayers);
                            Document psmlSearchResult = ScrapeHelper.createDocument(
                                    scrapeLoader.loadAndGetPageContent(
                                            new URL(PSML_SEARCH_BASE_URL + pesDbBaseUrl + e.getValue()),
                                            psmlPageLoader));
                            Element psmlPlayer = ScrapeHelper.getElement(psmlSearchResult, "table.style2 tr:nth-of-type(2)");
                            if (ScrapeHelper.getElement(psmlPlayer, "td:nth-of-type(1) a") == null) {
                                log.debug("Search result is empty for player: {}", e.getKey());
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
                            log.debug("{} ({}) - {} is imported", player.getName(), player.getPrimaryPosition(), player.getOverallRating());
                            playersImported++;
                        } catch (Exception ex) {
                            scrapeErrorHistoryRepository.save(ScrapeErrorHistory.builder()
                                    .scrapeTime(LocalDateTime.now())
                                    .jobInfo(jobInfo)
                                    .stackTrace(ExceptionUtils.getStackTrace(ex))
                                    .build());
                            playersWithError.add(new String[]{e.getKey(), ex.getClass().getName()});
                            log.error(ex.getMessage(), ex);
                        }
                    }
                } else {
                    break;
                }
            } catch(Exception ex){
                jobExecutionHistory.setStatus(JobExecutionStatus.FAILED);
                jobExecutionHistory.setErrorStackTrace(ExceptionUtils.getStackTrace(ex));
                log.error(ex.getMessage(), ex);
            }
        }
        log.info("ImportPlayersFromPesDb job end");
        log.debug("Unsuccessful scrape for players:");
        for(String[] s : playersWithError){
            log.debug(Arrays.toString(s));
        }
        jobExecutionHistory.setEndTime(LocalDateTime.now());
        jobExecutionHistory.setPlayersProcessed(playersImported);
        jobExecutionHistory.setPlayersWithError((long) playersWithError.size());
        jobExecutionHistoryRepository.save(jobExecutionHistory);
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
