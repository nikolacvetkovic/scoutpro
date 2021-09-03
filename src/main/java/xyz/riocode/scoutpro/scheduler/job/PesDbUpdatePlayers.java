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
import xyz.riocode.scoutpro.scrape.model.ScrapeField;
import xyz.riocode.scoutpro.scrape.repository.ScrapeFieldRepository;
import xyz.riocode.scoutpro.scrape.template.PesDbScrapeTemplateImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class PesDbUpdatePlayers extends QuartzJobBean {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ScrapeFieldRepository scrapeFieldRepository;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        int pageSize = jobExecutionContext.getMergedJobDataMap().getIntValue("pageSize");
        log.info("PesDbUpdatePlayers job start -> pageSize: {}", pageSize);

        long count = playerRepository.count();
        if (count > 0) {
            List<ScrapeField> scrapeFields = scrapeFieldRepository.findAll();
            Map<String, String> pesdbScrapeFields = scrapeFields.stream()
                    .filter(scrapeField -> scrapeField.getScrapeSite().getName().equals("pesdb"))
                    .collect(Collectors.toMap(ScrapeField::getName, ScrapeField::getSelector));
            long totalPages = count / pageSize;
            if (count % pageSize > 0) totalPages++;
            Page<Player> page;
            PesDbScrapeTemplateImpl pesDbScrapeTemplate = new PesDbScrapeTemplateImpl(pesdbScrapeFields);
            for (int i = 0; i < totalPages; i++) {
                page = playerRepository.findAll(PageRequest.of(i, pageSize));
                for (Player player : page.getContent()) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage(), e);
                        throw new RuntimeException(e);
                    }
                    pesDbScrapeTemplate.scrape(player);
                    playerRepository.save(player);
                    log.info("PesDb fields are updated for player: {} - {}", player.getId(), player.getName());
                }
            }
        }
        log.info("PesDbUpdatePlayers job finish.");
    }
}
