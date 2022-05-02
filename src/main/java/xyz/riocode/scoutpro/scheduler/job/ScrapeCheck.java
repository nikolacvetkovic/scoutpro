package xyz.riocode.scoutpro.scheduler.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import xyz.riocode.scoutpro.scrape.model.ScrapeSite;
import xyz.riocode.scoutpro.scrape.service.ScrapeSiteService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class ScrapeCheck extends QuartzJobBean {

    @Autowired
    private ScrapeSiteService scrapeSiteService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("ScrapeCheck job start");
        int checkInterval = jobExecutionContext.getMergedJobDataMap().getInt("checkInterval");
        LocalDateTime dateTimeLimit = LocalDateTime.now().minusHours(checkInterval);
        List<ScrapeSite> scrapeSites = scrapeSiteService.getByLastCheckedBefore(dateTimeLimit);
        if (scrapeSites.size() > 0) {
            ScrapeSite foundedScrapeSite = scrapeSites.stream().findAny().get();
            log.debug("ScrapeCheck started for {} site", foundedScrapeSite.getName());
            scrapeSiteService.checkScrape(foundedScrapeSite.getId());
            log.debug("ScrapeCheck finished for {} site", foundedScrapeSite.getName());
        } else {
            log.debug("All ScrapeSites are checked");
        }
        log.info("ScrapeCheck job finish");
    }
}
