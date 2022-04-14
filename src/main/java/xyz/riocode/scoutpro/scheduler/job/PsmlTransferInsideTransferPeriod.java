package xyz.riocode.scoutpro.scheduler.job;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.model.PsmlTransfer;
import xyz.riocode.scoutpro.repository.PlayerRepository;
import xyz.riocode.scoutpro.scrape.engine.ScrapeLoader;
import xyz.riocode.scoutpro.scrape.helper.ScrapeHelper;
import xyz.riocode.scoutpro.scrape.loader.PsmlPageLoaderImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class PsmlTransferInsideTransferPeriod extends QuartzJobBean {

    private static final String PSML_BASE_URL = "https://pc.psml.rs/index.php";
    private static final String TRANSFERS_SELECTOR = "tr.reverseGradient table.innerTable:nth-of-type(2) tr:nth-of-type(2) td:has(h3:contains(Najnoviji Transferi))>div";

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ScrapeLoader scrapeLoader;
    @Autowired
    private PsmlPageLoaderImpl psmlPageLoader;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("PsmlScrapeTransfersInsideTransferPeriod job start");
        try {
            String mainPageContent = scrapeLoader.loadAndGetPageContent(new URL(PSML_BASE_URL), psmlPageLoader);
            Elements transfers = ScrapeHelper.getElements(ScrapeHelper.createDocument(mainPageContent), TRANSFERS_SELECTOR);
            Player player;
            for (Element transfer : transfers) {
                String playerPsmlUrlPart = ScrapeHelper.getAttributeValue(transfer, "a", "href");
                player = playerRepository.findByPsmlUrl(PSML_BASE_URL + playerPsmlUrlPart);
                if (player == null) continue;
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
                playerRepository.save(player);
                log.info("Psml transfer for : {} - {} inserted", player.getId(), player.getName());
            }
        } catch (MalformedURLException e) {
            log.error(e.getMessage(), e);
        }

        log.info("PsmlScrapeTransfersInsideTransferPeriod job end");
    }
}
