package xyz.riocode.scoutpro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.riocode.scoutpro.scheduler.model.JobInfo;
import xyz.riocode.scoutpro.scheduler.service.JobService;
import xyz.riocode.scoutpro.security.privilege.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
    @JobReadPrivilege
    @GetMapping
    public List<JobInfo> getAllJobs(){
        return jobService.getAll();
    }
    @JobUpdatePrivilege
    @PutMapping("/{jobId}/update")
    public JobInfo updateJob(@PathVariable Long jobId, @Valid @RequestBody JobInfo jobInfo){
        return jobService.updateJob(jobId, jobInfo);
    }
    @JobStartPrivilege
    @GetMapping("/{jobId}/run")
    public ResponseEntity runOnceJob(@PathVariable Long jobId){
        jobService.runOnceJob(jobId);
        return ResponseEntity.ok().build();
    }
    @JobSchedulePrivilege
    @PutMapping("/{jobId}/schedule")
    public JobInfo scheduleJob(@PathVariable Long jobId, @RequestBody JobInfo jobInfo){
        return jobService.scheduleJob(jobId, jobInfo);
    }
    @JobSchedulePrivilege
    @PutMapping("/{jobId}/reschedule")
    public JobInfo rescheduleJob(@PathVariable Long jobId, @RequestBody JobInfo jobInfo){
        return jobService.rescheduleJob(jobId, jobInfo);
    }
    @JobUnschedulePrivilege
    @PutMapping("/{jobId}/unschedule")
    public JobInfo unscheduleJob(@PathVariable Long jobId){
        return jobService.unscheduleJob(jobId);
    }
    @JobPausePrivilege
    @PutMapping("/{jobId}/pause")
    public JobInfo pauseJob(@PathVariable Long jobId){
        return jobService.pauseJob(jobId);
    }
    @JobResumePrivilege
    @PutMapping("/{jobId}/resume")
    public JobInfo resumeJob(@PathVariable Long jobId){
        return jobService.resumeJob(jobId);
    }
}
