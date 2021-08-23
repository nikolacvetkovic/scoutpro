package xyz.riocode.scoutpro.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.scheduler.model.JobInfo;
import xyz.riocode.scoutpro.scheduler.service.JobService;

@Component
public class JobsLoader implements CommandLineRunner {

    private final JobService jobService;

    public JobsLoader(JobService jobService) {
        this.jobService = jobService;
    }

    @Override
    public void run(String... args) throws Exception {
        JobInfo jobInfo = JobInfo.builder()
                .cronJob(false)
                .jobClass("xyz.riocode.scoutpro.scheduler.job.PesDbChangeSeasonYear")
                .jobName("PesDbChangeSeasonYear")
                .jobGroup("PesDb")
                .jobStatus("CREATED")
                .customConfigData("{\"oldSeasonYear\":\"2021\",\n" +
                        "\"newSeasonYear\":\"2022\",\n" +
                        "\"pageSize\":\"1\"}")
                .build();

        jobService.createJob(jobInfo);
    }
}
