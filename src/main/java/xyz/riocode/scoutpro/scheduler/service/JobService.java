package xyz.riocode.scoutpro.scheduler.service;

import xyz.riocode.scoutpro.scheduler.model.JobInfo;

import java.util.List;

public interface JobService {

    List<JobInfo> getAll();
    JobInfo createJob(JobInfo job);
    JobInfo updateJob(Long jobId, JobInfo job);
    boolean runOnceJob(Long jobId);
    JobInfo scheduleJob(Long jobId, JobInfo job);
    JobInfo unscheduleJob(Long jobId);
    JobInfo rescheduleJob(Long jobId, JobInfo job);
    JobInfo pauseJob(Long jobId);
    JobInfo resumeJob(Long jobId);
    void deleteJob(Long jobId);

}
