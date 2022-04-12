package xyz.riocode.scoutpro.scheduler.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.quartz.QuartzJobBean;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.repository.PlayerRepository;
import xyz.riocode.scoutpro.scrape.engine.ScrapeEngine;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class TransfermarktUpdatePlayers extends QuartzJobBean {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ScrapeEngine scrapeEngine;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        int pageSize = jobExecutionContext.getMergedJobDataMap().getIntValue("pageSize");
        log.info("TransfermarktUpdatePlayers job start -> pageSize: {}", pageSize);

        List<String[]> playersWithError = new ArrayList<>();
        LocalDateTime dateTimeLimit = LocalDateTime.now().minusMonths(1);

        long count = playerRepository.countByTransferLastCheckBefore(dateTimeLimit);
        if (count > 0) {
            int totalPages = (int) (count / pageSize);
            if (count % pageSize > 0) totalPages++;
            Page<Player> page;
            for (int i = (totalPages-1); i >= 0; i--) {
                page = playerRepository.findByTransferLastCheckBefore(dateTimeLimit, PageRequest.of(i, pageSize));
                for (Player player : page.getContent()) {
                    try {
                        Thread.sleep(10000);
                        scrapeEngine.work(new URL(player.getTransfermarktUrl()), player);
                        playerRepository.save(player);
                        log.info("Transfermarkt fields are updated for player: {} - {}", player.getId(), player.getName());
                    } catch (Exception ex) {
                        log.error(ex.getMessage(), ex);
                        playersWithError.add(new String[]{player.getId() + " - " + player.getName(), ex.getMessage()});
                    }
                }
            }
        }
        log.info("TransfermarktUpdatePlayers job finish.");
        log.info("Unsuccessful scrape for players:");
        for(String[] s : playersWithError){
            log.info(Arrays.toString(s));
        }
    }
}
