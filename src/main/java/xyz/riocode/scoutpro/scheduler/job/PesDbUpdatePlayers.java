package xyz.riocode.scoutpro.scheduler.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.quartz.QuartzJobBean;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.repository.PlayerRepository;
import xyz.riocode.scoutpro.scheduler.enums.JobExecutionStatus;
import xyz.riocode.scoutpro.scheduler.model.JobExecutionHistory;
import xyz.riocode.scoutpro.scheduler.model.JobInfo;
import xyz.riocode.scoutpro.scheduler.repository.JobExecutionHistoryRepository;
import xyz.riocode.scoutpro.scheduler.repository.JobInfoRepository;
import xyz.riocode.scoutpro.scrape.engine.ScrapeEngine;
import xyz.riocode.scoutpro.scrape.model.ScrapeErrorHistory;
import xyz.riocode.scoutpro.scrape.repository.ScrapeErrorHistoryRepository;

import java.net.URL;
import java.time.LocalDateTime;

@Slf4j
public class PesDbUpdatePlayers extends QuartzJobBean {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ScrapeEngine scrapeEngine;
    @Autowired
    private JobInfoRepository jobInfoRepository;
    @Autowired
    private JobExecutionHistoryRepository jobExecutionHistoryRepository;
    @Autowired
    private ScrapeErrorHistoryRepository scrapeErrorHistoryRepository;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("PesDbUpdatePlayers job start");
        JobInfo jobInfo = jobInfoRepository.findByJobNameAndJobGroup(jobExecutionContext.getJobDetail().getKey().getName(),
                jobExecutionContext.getJobDetail().getKey().getGroup());
        JobExecutionHistory jobExecutionHistory = JobExecutionHistory.builder()
                .startTime(LocalDateTime.now())
                .status(JobExecutionStatus.SUCCESS)
                .jobInfo(jobInfo)
                .build();
        long playersProcessed = 0;
        long playersWithError = 0;

        int pageSize = jobExecutionContext.getMergedJobDataMap().getIntValue("pageSize");

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
                        scrapeEngine.work(new URL(player.getPesDbUrl()), player);
                        playerRepository.save(player);
                        playersProcessed++;
                        log.debug("PesDb fields are updated for player: {} - {}", player.getId(), player.getName());
                    } catch (Exception ex) {
                        playersWithError++;
                        scrapeErrorHistoryRepository.save(ScrapeErrorHistory.builder()
                                .scrapeTime(LocalDateTime.now())
                                .jobInfo(jobInfo)
                                .stackTrace(ExceptionUtils.getStackTrace(ex))
                                .build());
                    }
                    log.debug("PesDb fields are updated for player: {} - {}", player.getId(), player.getName());
                }
            }
        }
        log.info("PesDbUpdatePlayers job finish.");
        jobExecutionHistory.setEndTime(LocalDateTime.now());
        jobExecutionHistory.setPlayersProcessed(playersProcessed);
        jobExecutionHistory.setPlayersWithError(playersWithError);
        jobExecutionHistoryRepository.save(jobExecutionHistory);
    }
}
