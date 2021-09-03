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

@Slf4j
public class PesDbChangeSeasonYear extends QuartzJobBean {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String oldSeason = "pes" + jobExecutionContext.getMergedJobDataMap().getString("oldSeasonYear");
        String newSeason = "pes" + jobExecutionContext.getMergedJobDataMap().getString("newSeasonYear");
        int pageSize = jobExecutionContext.getMergedJobDataMap().getIntValue("pageSize");

        log.info("PesDbChangeSeasonYear job start -> [oldSeasonYear:{}, newSeasonYear:{}, pageSize:{}]", oldSeason, newSeason, pageSize);

        long count = playerRepository.count();
        if (count > 0) {
            long totalPages = count / pageSize;
            if (count % pageSize > 0) totalPages++;
            String pesDbUrl;
            Page<Player> page;
            for (int i = 0; i < totalPages; i++) {
                page = playerRepository.findAll(PageRequest.of(i, pageSize));
                for (Player player : page.getContent()) {
                    pesDbUrl = player.getPesDbUrl();
                    pesDbUrl = pesDbUrl.replaceAll(oldSeason, newSeason);
                    player.setPesDbUrl(pesDbUrl);
                    playerRepository.save(player);
                    log.info("PesDbUrl update finished for player: {} - {}", player.getId(), player.getName());
                }
            }
        }

        log.info("PesDbChangeSeasonYear finish.");
    }
}
