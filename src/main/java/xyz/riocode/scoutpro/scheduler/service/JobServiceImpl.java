package xyz.riocode.scoutpro.scheduler.service;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;
import xyz.riocode.scoutpro.exception.JobNotFoundException;
import xyz.riocode.scoutpro.scheduler.enums.JobStatus;
import xyz.riocode.scoutpro.scheduler.helper.JobHelper;
import xyz.riocode.scoutpro.scheduler.model.JobInfo;
import xyz.riocode.scoutpro.scheduler.repository.JobInfoRepository;

import javax.transaction.Transactional;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Slf4j
@Transactional
@Service
public class JobServiceImpl implements JobService{

    private final JobInfoRepository jobInfoRepository;
    private final Scheduler scheduler;
    private final JobHelper jobHelper;
    private final ApplicationContext applicationContext;

    public JobServiceImpl(JobInfoRepository jobInfoRepository, Scheduler scheduler, JobHelper jobHelper, ApplicationContext applicationContext) {
        this.jobInfoRepository = jobInfoRepository;
        this.scheduler = scheduler;
        this.jobHelper = jobHelper;
        this.applicationContext = applicationContext;
    }

    @Override
    public List<JobInfo> getAll() {
        return jobInfoRepository.findAll();
    }

    @Override
    public JobInfo createJob(JobInfo job) {
        JobInfo foundedJob = jobInfoRepository.findByJobNameAndJobGroup(job.getJobName(), job.getJobGroup());
        if (foundedJob != null) throw new RuntimeException("Job already exist");
        try {
            JobDetail jobDetail = jobHelper.createJob(
                    (Class<? extends QuartzJobBean>) Class.forName(job.getJobClass()),
                    job.getJobName(),
                    job.getJobGroup(),
                    job.getCustomConfigData());
            scheduler.addJob(jobDetail, true);
            job.setJobStatus(JobStatus.CREATED);
        } catch (ClassNotFoundException | SchedulerException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return jobInfoRepository.save(job);
    }

    @Override
    public JobInfo updateJob(Long jobId, JobInfo job) {
        JobInfo foundedJob = jobInfoRepository.findById(jobId).orElseThrow(() -> new JobNotFoundException(jobId));
        JobKey jobKey = new JobKey(foundedJob.getJobName(), foundedJob.getJobGroup());
        TriggerKey triggerKey = new TriggerKey(foundedJob.getJobName(), foundedJob.getJobGroup());
        try {
            JobDetail jobDetail = jobHelper.createJob(
                    (Class<? extends QuartzJobBean>) Class.forName(foundedJob.getJobClass()),
                    foundedJob.getJobName(),
                    foundedJob.getJobGroup(),
                    job.getCustomConfigData());
            Trigger trigger = scheduler.getTrigger(triggerKey);
            scheduler.deleteJob(jobKey);
            if (trigger != null) {
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                scheduler.addJob(jobDetail, true);
            }
            foundedJob.setCustomConfigData(job.getCustomConfigData());
        } catch (SchedulerException | ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return jobInfoRepository.save(foundedJob);
    }

    @Override
    public boolean runOnceJob(Long jobId) {
        JobInfo foundedJob = jobInfoRepository.findById(jobId).orElseThrow(() -> new JobNotFoundException(jobId));
        try {
            scheduler.triggerJob(new JobKey(foundedJob.getJobName(), foundedJob.getJobGroup()));
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    public JobInfo scheduleJob(Long jobId, JobInfo job) {
        JobInfo foundedJob = jobInfoRepository.findById(jobId).orElseThrow(() -> new JobNotFoundException(jobId));
        try {
            if (scheduler.checkExists(
                    new TriggerKey(
                            foundedJob.getJobName(),
                            foundedJob.getJobGroup()))) throw new RuntimeException("The job is already scheduled.");
            if (job.getStartTime() == null) throw new RuntimeException("The job must have start date.");
            Trigger trigger = null;
            if (job.isCronJob() && job.getCronExpression() != null) {
                trigger = jobHelper.createCronTrigger(
                        new JobKey(foundedJob.getJobName(), foundedJob.getJobGroup()),
                        Date.from(job.getStartTime().atZone(ZoneId.systemDefault()).toInstant()),
                        Date.from(job.getEndTime().atZone(ZoneId.systemDefault()).toInstant()),
                        job.getCronExpression());
            } else if (job.getRepeatIntervalInSeconds() != null && job.getRepeatCount() != null) {
                trigger = jobHelper.createSimpleTrigger(
                        new JobKey(foundedJob.getJobName(), foundedJob.getJobGroup()),
                        Date.from(job.getStartTime().atZone(ZoneId.systemDefault()).toInstant()),
                        Date.from(job.getEndTime().atZone(ZoneId.systemDefault()).toInstant()),
                        job.getRepeatIntervalInSeconds(),
                        job.getRepeatCount());
            } else {
                throw new RuntimeException("The job must be cron or simple.");
            }
            scheduler.scheduleJob(trigger);
            foundedJob.setStartTime(job.getStartTime());
            foundedJob.setEndTime(job.getEndTime());
            foundedJob.setCronJob(job.isCronJob());
            foundedJob.setCronExpression(job.getCronExpression());
            foundedJob.setRepeatIntervalInSeconds(job.getRepeatIntervalInSeconds());
            foundedJob.setRepeatCount(job.getRepeatCount());
            foundedJob.setJobStatus(JobStatus.SCHEDULED);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return jobInfoRepository.save(foundedJob);
    }

    @Override
    public JobInfo unscheduleJob(Long jobId) {
        JobInfo foundedJob = jobInfoRepository.findById(jobId).orElseThrow(() -> new JobNotFoundException(jobId));
        try {
            if (!scheduler.checkExists(
                    new TriggerKey(
                            foundedJob.getJobName(),
                            foundedJob.getJobGroup()))) throw new RuntimeException("The job is not scheduled.");
            if (foundedJob.getJobStatus().equals(JobStatus.PAUSED))
                throw new RuntimeException("The job cannot be unscheduled because it is paused.");
            scheduler.unscheduleJob(new TriggerKey(foundedJob.getJobName(), foundedJob.getJobGroup()));
            foundedJob.setStartTime(null);
            foundedJob.setEndTime(null);
            foundedJob.setCronJob(false);
            foundedJob.setCronExpression(null);
            foundedJob.setRepeatIntervalInSeconds(null);
            foundedJob.setRepeatCount(null);
            foundedJob.setJobStatus(JobStatus.CREATED);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return jobInfoRepository.save(foundedJob);
    }

    @Override
    public JobInfo rescheduleJob(Long jobId, JobInfo job) {
        JobInfo foundedJob = jobInfoRepository.findById(jobId).orElseThrow(() -> new JobNotFoundException(jobId));
        try {
            if (!scheduler.checkExists(
                    new TriggerKey(
                            foundedJob.getJobName(),
                            foundedJob.getJobGroup()))) throw new RuntimeException("The job is not scheduled.");
            if (job.getStartTime() == null) throw new RuntimeException("The job must have start date.");
            if (foundedJob.getJobStatus().equals(JobStatus.PAUSED))
                throw new RuntimeException("The job cannot be unscheduled because it is paused.");
            Trigger trigger = null;
            if (job.isCronJob() && job.getCronExpression() != null) {
                trigger = jobHelper.createCronTrigger(
                        new JobKey(foundedJob.getJobName(), foundedJob.getJobGroup()),
                        Date.from(job.getStartTime().atZone(ZoneId.systemDefault()).toInstant()),
                        Date.from(job.getEndTime().atZone(ZoneId.systemDefault()).toInstant()),
                        job.getCronExpression());
            } else if (job.getRepeatIntervalInSeconds() != null && job.getRepeatCount() != null) {
                trigger = jobHelper.createSimpleTrigger(
                        new JobKey(foundedJob.getJobName(), foundedJob.getJobGroup()),
                        Date.from(job.getStartTime().atZone(ZoneId.systemDefault()).toInstant()),
                        Date.from(job.getEndTime().atZone(ZoneId.systemDefault()).toInstant()),
                        job.getRepeatIntervalInSeconds(),
                        job.getRepeatCount());
            } else {
                throw new RuntimeException("The job must be cron or simple.");
            }
            scheduler.rescheduleJob(new TriggerKey(foundedJob.getJobName(), foundedJob.getJobGroup()), trigger);
            foundedJob.setStartTime(job.getStartTime());
            foundedJob.setEndTime(job.getEndTime());
            foundedJob.setCronJob(job.isCronJob());
            foundedJob.setCronExpression(job.getCronExpression());
            foundedJob.setRepeatIntervalInSeconds(job.getRepeatIntervalInSeconds());
            foundedJob.setRepeatCount(job.getRepeatCount());
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return jobInfoRepository.save(foundedJob);
    }

    @Override
    public JobInfo pauseJob(Long jobId) {
        JobInfo foundedJob = jobInfoRepository.findById(jobId).orElseThrow(() -> new JobNotFoundException(jobId));
        try {
            if (!scheduler.checkExists(
                    new TriggerKey(
                            foundedJob.getJobName(),
                            foundedJob.getJobGroup()))) throw new RuntimeException("The job is not scheduled.");
            scheduler.pauseJob(new JobKey(foundedJob.getJobName(), foundedJob.getJobGroup()));
            foundedJob.setJobStatus(JobStatus.PAUSED);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return jobInfoRepository.save(foundedJob);
    }

    @Override
    public JobInfo resumeJob(Long jobId) {
        JobInfo foundedJob = jobInfoRepository.findById(jobId).orElseThrow(() -> new JobNotFoundException(jobId));
        try {
            if (!scheduler.checkExists(
                    new TriggerKey(
                            foundedJob.getJobName(),
                            foundedJob.getJobGroup()))) throw new RuntimeException("The job is not scheduled.");
            scheduler.resumeJob(new JobKey(foundedJob.getJobName(), foundedJob.getJobGroup()));
            foundedJob.setJobStatus(JobStatus.SCHEDULED);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return jobInfoRepository.save(foundedJob);
    }

    @Override
    public void deleteJob(Long jobId) {
        JobInfo foundedJob = jobInfoRepository.findById(jobId).orElseThrow(() -> new JobNotFoundException(jobId));
        try {
            scheduler.deleteJob(new JobKey(foundedJob.getJobName(), foundedJob.getJobGroup()));
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        jobInfoRepository.delete(foundedJob);
    }
}
