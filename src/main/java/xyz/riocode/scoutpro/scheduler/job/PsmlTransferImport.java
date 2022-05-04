package xyz.riocode.scoutpro.scheduler.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.model.PsmlTransfer;
import xyz.riocode.scoutpro.repository.PlayerRepository;
import xyz.riocode.scoutpro.scheduler.enums.JobExecutionStatus;
import xyz.riocode.scoutpro.scheduler.model.JobExecutionHistory;
import xyz.riocode.scoutpro.scheduler.model.JobInfo;
import xyz.riocode.scoutpro.scheduler.repository.JobExecutionHistoryRepository;
import xyz.riocode.scoutpro.scheduler.repository.JobInfoRepository;
import xyz.riocode.scoutpro.scrape.engine.ScrapeLoader;
import xyz.riocode.scoutpro.scrape.helper.ScrapeHelper;
import xyz.riocode.scoutpro.scrape.loader.PsmlPageLoaderImpl;
import xyz.riocode.scoutpro.scrape.model.ScrapeError;
import xyz.riocode.scoutpro.scrape.repository.ScrapeErrorRepository;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class PsmlTransferImport extends QuartzJobBean {

    private static final String PSML_BASE_URL = "https://pc.psml.rs/index.php";
    private static final String TRANSFERS_SELECTOR = "tr.reverseGradient table.innerTable:nth-of-type(2) tr:nth-of-type(2) td:not(h3:contains(Rezultati))>div";

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ScrapeLoader scrapeLoader;
    @Autowired
    private PsmlPageLoaderImpl psmlPageLoader;
    @Autowired
    private JobInfoRepository jobInfoRepository;
    @Autowired
    private JobExecutionHistoryRepository jobExecutionHistoryRepository;
    @Autowired
    private ScrapeErrorRepository scrapeErrorRepository;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("PsmlTransferImport job start");
        JobInfo jobInfo = jobInfoRepository.findByJobNameAndJobGroup(jobExecutionContext.getJobDetail().getKey().getName(),
                jobExecutionContext.getJobDetail().getKey().getGroup());
        JobExecutionHistory jobExecutionHistory = JobExecutionHistory.builder()
                .startTime(LocalDateTime.now())
                .status(JobExecutionStatus.SUCCESS)
                .jobInfo(jobInfo)
                .build();
        long playersProcessed = 0;
        long playersWithError = 0;

        try {
            String mainPageContent = scrapeLoader.loadAndGetPageContent(new URL(PSML_BASE_URL), psmlPageLoader);
            Elements transfers = ScrapeHelper.getElements(ScrapeHelper.createDocument(mainPageContent), TRANSFERS_SELECTOR);
            Player player;
            for (Element transfer : transfers) {
                String playerPsmlUrlPart = ScrapeHelper.getAttributeValue(transfer, "a", "href");
                player = playerRepository.findByPsmlUrl(PSML_BASE_URL + playerPsmlUrlPart);
                if (player == null) continue;
                try {
                    String fromTeam = ScrapeHelper.getAttributeValue(transfer, "div div:nth-of-type(3) img", "title");
                    String toTeam = ScrapeHelper.getAttributeValue(transfer, "div div:nth-of-type(1) img", "title");
                    String divHtml = ScrapeHelper.getElementHtml(transfer, "div");
                    String[] transferDetails = divHtml.substring(divHtml.indexOf("<br>")).split("<br>");
                    String transferFee = transferDetails[1].trim();
                    LocalDate dateOfTransfer = LocalDate.parse(transferDetails[2].trim(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                    PsmlTransfer psmlTransfer = PsmlTransfer.builder()
                            .fromTeam(fromTeam)
                            .toTeam(toTeam)
                            .transferFee(transferFee)
                            .dateOfTransfer(dateOfTransfer)
                            .player(player)
                            .build();
                    player.getPsmlTransfers().add(psmlTransfer);
                    player.setPsmlTeam(toTeam);
                    player.setPsmlValue(formatTransferFee(transferFee));
                    playerRepository.save(player);
                    playersProcessed++;
                    log.debug("Psml transfer for : {} - {} inserted", player.getId(), player.getName());
                } catch (Exception ex) {
                    playersWithError++;
                    scrapeErrorRepository.save(ScrapeError.builder()
                            .scrapeTime(LocalDateTime.now())
                            .player(player)
                            .jobInfo(jobInfo)
                            .player(player)
                            .stackTrace(ExceptionUtils.getStackTrace(ex))
                            .build());
                    log.error(ex.getMessage(), ex);
                }
            }
        } catch (Exception ex) {
            jobExecutionHistory.setStatus(JobExecutionStatus.FAILED);
            jobExecutionHistory.setErrorStackTrace(ExceptionUtils.getStackTrace(ex));
            log.error(ex.getMessage(), ex);
        }
        log.info("PsmlTransferImport job end");
        jobExecutionHistory.setEndTime(LocalDateTime.now());
        jobExecutionHistory.setPlayersProcessed(playersProcessed);
        jobExecutionHistory.setPlayersWithError(playersWithError);
        jobExecutionHistoryRepository.save(jobExecutionHistory);
    }

    private BigDecimal formatTransferFee(String scrapedTransferFee) {
        String transferFeeString = scrapedTransferFee.replaceAll("[^0-9,]", "");
        BigDecimal transferFee = new BigDecimal(transferFeeString);
        return transferFee.multiply(BigDecimal.valueOf(1000000));
    }
}
