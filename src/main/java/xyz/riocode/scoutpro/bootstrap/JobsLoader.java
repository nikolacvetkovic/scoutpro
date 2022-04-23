package xyz.riocode.scoutpro.bootstrap;

import org.quartz.Scheduler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.scheduler.service.JobService;

@Component
public class JobsLoader implements CommandLineRunner {

    private final JobService jobService;
    private final Scheduler scheduler;

    public JobsLoader(JobService jobService, Scheduler scheduler) {
        this.jobService = jobService;
        this.scheduler = scheduler;
    }

    @Override
    public void run(String... args) throws Exception {
//        JobInfo pesDbChangeSeasonYearJob = JobInfo.builder()
//                .jobClass("xyz.riocode.scoutpro.scheduler.job.PesDbChangeSeasonYear")
//                .jobName("PesDbChangeSeasonYear")
//                .jobGroup("PesDb")
//                .customConfigData("{\"oldSeasonYear\":\"2021\",\n" +
//                        "\"newSeasonYear\":\"2022\",\n" +
//                        "\"pageSize\":1}")
//                .build();
//
//        jobService.createJob(pesDbChangeSeasonYearJob);
//
//        JobInfo pesDbUpdatePlayers = JobInfo.builder()
//                .jobClass("xyz.riocode.scoutpro.scheduler.job.PesDbUpdatePlayers")
//                .jobName("PesDbUpdatePlayers")
//                .jobGroup("PesDb")
//                .customConfigData("{\"pageSize\":3}")
//                .build();
//
//        jobService.createJob(pesDbUpdatePlayers);
//
//        JobInfo importPlayersFromPesDb = JobInfo.builder()
//                .jobClass("xyz.riocode.scoutpro.scheduler.job.ImportPlayersFromPesDb")
//                .jobName("ImportPlayersFromPesDb")
//                .jobGroup("General")
//                .customConfigData("{\"pesDbBaseUrl\":\"https://pesdb.net/pes2021\"," +
//                                    "\"sleepTimeBetweenPlayers\":25000}")
//                .build();
//
//        jobService.createJob(importPlayersFromPesDb);
//
//        JobInfo importPlayersFromPsml = JobInfo.builder()
//                .jobClass("xyz.riocode.scoutpro.scheduler.job.ImportPlayersFromPsml")
//                .jobName("ImportPlayersFromPsml")
//                .jobGroup("General")
//                .build();
//
//        jobService.createJob(importPlayersFromPsml);
//
//        JobInfo psmlUpdatePlayers = JobInfo.builder()
//                .jobClass("xyz.riocode.scoutpro.scheduler.job.PsmlUpdatePlayers")
//                .jobName("PsmlUpdatePlayers")
//                .jobGroup("Psml")
//                .customConfigData("{\"pageSize\":20}")
//                .build();
//
//        jobService.createJob(psmlUpdatePlayers);
//
//        JobInfo transfermarktUpdatePlayers = JobInfo.builder()
//                .jobClass("xyz.riocode.scoutpro.scheduler.job.TransfermarktUpdatePlayers")
//                .jobName("TransfermarktUpdatePlayers")
//                .jobGroup("Transfermarkt")
//                .customConfigData("{\"pageSize\":20}")
//                .build();
//
//        jobService.createJob(transfermarktUpdatePlayers);

//        JobInfo psmlTransferImport = JobInfo.builder()
//                .jobClass("xyz.riocode.scoutpro.scheduler.job.PsmlTransferImport")
//                .jobName("PsmlTransferImport")
//                .jobGroup("Psml")
//                .customConfigData("")
//                .build();
//
//        jobService.createJob(psmlTransferImport);
    }
}
