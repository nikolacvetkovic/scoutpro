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

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class PesDbUpdatePlayers extends QuartzJobBean {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ScrapeEngine scrapeEngine;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        int pageSize = jobExecutionContext.getMergedJobDataMap().getIntValue("pageSize");
        log.info("PesDbUpdatePlayers job start -> pageSize: {}", pageSize);

        long count = playerRepository.count();
        if (count > 0) {
            long totalPages = count / pageSize;
            if (count % pageSize > 0) totalPages++;
            Page<Player> page;
            for (int i = 0; i < totalPages; i++) {
                page = playerRepository.findAll(PageRequest.of(i, pageSize));
                for (Player player : page.getContent()) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage(), e);
                        throw new RuntimeException(e);
                    }
                    try {
                        scrapeEngine.work(new URL(player.getPesDbUrl()), player);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                    playerRepository.save(player);
                    log.info("PesDb fields are updated for player: {} - {}", player.getId(), player.getName());
                }
            }
        }
        log.info("PesDbUpdatePlayers job finish.");
    }
}
