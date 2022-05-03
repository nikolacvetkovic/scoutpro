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
import xyz.riocode.scoutpro.scrape.model.ScrapeError;
import xyz.riocode.scoutpro.scrape.repository.ScrapeErrorRepository;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class TMStatsUpdatePlayers extends QuartzJobBean {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ScrapeEngine scrapeEngine;
    @Autowired
    private JobInfoRepository jobInfoRepository;
    @Autowired
    private JobExecutionHistoryRepository jobExecutionHistoryRepository;
    @Autowired
    private ScrapeErrorRepository scrapeErrorRepository;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("TMStatsUpdatePlayers job start");
        JobInfo jobInfo = jobInfoRepository.findByJobNameAndJobGroup(jobExecutionContext.getJobDetail().getKey().getName(),
                jobExecutionContext.getJobDetail().getKey().getGroup());
        JobExecutionHistory jobExecutionHistory = JobExecutionHistory.builder()
                .startTime(LocalDateTime.now())
                .status(JobExecutionStatus.SUCCESS)
                .jobInfo(jobInfo)
                .build();
        long playersProcessed = 0;

        int pageSize = jobExecutionContext.getMergedJobDataMap().getIntValue("pageSize");
        String playerCheckInterval = jobExecutionContext.getMergedJobDataMap().getString("playerCheckInterval");
        long checkIntervalValue = Long.parseLong(playerCheckInterval.replaceAll("\\D+",""));
        String checkIntervalUnit = playerCheckInterval.replaceAll("[^a-zA-Z]+","");
        LocalDateTime dateTimeLimit = LocalDateTime.now().minus(checkIntervalValue, ChronoUnit.valueOf(checkIntervalUnit));

        List<String[]> playersWithError = new ArrayList<>();

        long count = playerRepository.countByStatisticsLastCheckBefore(dateTimeLimit);
        if (count > 0) {
            int totalPages = (int) (count / pageSize);
            if (count % pageSize > 0) totalPages++;
            Page<Player> page;
            for (int i = (totalPages-1); i >= 0; i--) {
                page = playerRepository.findByStatisticsLastCheckBefore(dateTimeLimit, PageRequest.of(i, pageSize));
                for (Player player : page.getContent()) {
                    try {
                        Thread.sleep(10000);
                        scrapeEngine.work(new URL(player.getTransfermarktStatsUrl()), player);
                        playerRepository.save(player);
                        playersProcessed++;
                        log.debug("Transfermarkt stats are updated for player: {} - {}", player.getId(), player.getName());
                    } catch (Exception ex) {
                        scrapeErrorRepository.save(ScrapeError.builder()
                                .scrapeTime(LocalDateTime.now())
                                .jobInfo(jobInfo)
                                .stackTrace(ExceptionUtils.getStackTrace(ex))
                                .build());
                        playersWithError.add(new String[]{player.getId() + " - " + player.getName(), ex.getMessage()});
                        log.error(ex.getMessage(), ex);
                    }
                }
            }
        }
        log.info("TMStatsUpdatePlayers job start");
        log.debug("Unsuccessful scrape for players:");
        for(String[] s : playersWithError){
            log.debug(Arrays.toString(s));
        }
        jobExecutionHistory.setEndTime(LocalDateTime.now());
        jobExecutionHistory.setPlayersProcessed(playersProcessed);
        jobExecutionHistory.setPlayersWithError((long) playersWithError.size());
        jobExecutionHistoryRepository.save(jobExecutionHistory);
    }
}
