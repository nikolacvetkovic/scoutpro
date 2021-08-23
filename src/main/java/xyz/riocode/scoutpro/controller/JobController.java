package xyz.riocode.scoutpro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.riocode.scoutpro.scheduler.model.JobInfo;
import xyz.riocode.scoutpro.scheduler.service.JobService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public List<JobInfo> getAllJobs(){
        return jobService.getAll();
    }

    @PutMapping("/{jobId}/update")
    public JobInfo updateJob(@PathVariable Long jobId, @Valid @RequestBody JobInfo jobInfo){
        return jobService.updateJob(jobId, jobInfo);
    }

    @GetMapping("/{jobId}/run")
    public ResponseEntity runOnceJob(@PathVariable Long jobId){
        jobService.runOnceJob(jobId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{jobId}/schedule")
    public JobInfo scheduleJob(@PathVariable Long jobId, @RequestBody JobInfo jobInfo){
        return jobService.scheduleJob(jobId, jobInfo);
    }

    @PutMapping("/{jobId}/reschedule")
    public JobInfo rescheduleJob(@PathVariable Long jobId, @RequestBody JobInfo jobInfo){
        return jobService.rescheduleJob(jobId, jobInfo);
    }

    @PutMapping("/{jobId}/unschedule")
    public JobInfo unscheduleJob(@PathVariable Long jobId){
        return jobService.unscheduleJob(jobId);
    }

    @PutMapping("/{jobId}/pause")
    public JobInfo pauseJob(@PathVariable Long jobId){
        return jobService.pauseJob(jobId);
    }

    @PutMapping("/{jobId}/resume")
    public JobInfo resumeJob(@PathVariable Long jobId){
        return jobService.resumeJob(jobId);
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity deleteJob(@PathVariable Long jobId){
        jobService.deleteJob(jobId);
        return ResponseEntity.ok().build();
    }

}
