package xyz.riocode.scoutpro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.riocode.scoutpro.converter.JobConverter;
import xyz.riocode.scoutpro.dto.JobDTO;
import xyz.riocode.scoutpro.scheduler.service.JobService;
import xyz.riocode.scoutpro.security.privilege.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {

    private final JobService jobService;
    private final JobConverter jobConverter;

    public JobController(JobService jobService, JobConverter jobConverter) {
        this.jobService = jobService;
        this.jobConverter = jobConverter;
    }
    @JobReadPrivilege
    @GetMapping
    public List<JobDTO> getAllJobs(){
        return jobConverter.jobsToJobDTOs(jobService.getAll());
    }

    @JobUpdatePrivilege
    @PutMapping("/{jobId}/update")
    public JobDTO updateJob(@PathVariable Long jobId, @Valid @RequestBody JobDTO job){
        return jobConverter.jobToJobDTO(
                    jobService.updateJob(jobId, jobConverter.jobDTOToJobInfo(job)));
    }

    @JobStartPrivilege
    @GetMapping("/{jobId}/run")
    public ResponseEntity runOnceJob(@PathVariable Long jobId){
        jobService.runOnceJob(jobId);
        return ResponseEntity.ok().build();
    }

    @JobSchedulePrivilege
    @PutMapping("/{jobId}/schedule")
    public JobDTO scheduleJob(@PathVariable Long jobId, @RequestBody JobDTO job){
        return jobConverter.jobToJobDTO(jobService.scheduleJob(jobId, jobConverter.jobDTOToJobInfo(job)));
    }

    @JobSchedulePrivilege
    @PutMapping("/{jobId}/reschedule")
    public JobDTO rescheduleJob(@PathVariable Long jobId, @RequestBody JobDTO job){
        return jobConverter.jobToJobDTO(jobService.rescheduleJob(jobId, jobConverter.jobDTOToJobInfo(job)));
    }

    @JobUnschedulePrivilege
    @PutMapping("/{jobId}/unschedule")
    public JobDTO unscheduleJob(@PathVariable Long jobId){
        return jobConverter.jobToJobDTO(jobService.unscheduleJob(jobId));
    }

    @JobPausePrivilege
    @PutMapping("/{jobId}/pause")
    public JobDTO pauseJob(@PathVariable Long jobId){
        return jobConverter.jobToJobDTO(jobService.pauseJob(jobId));
    }

    @JobResumePrivilege
    @PutMapping("/{jobId}/resume")
    public JobDTO resumeJob(@PathVariable Long jobId){
        return jobConverter.jobToJobDTO(jobService.resumeJob(jobId));
    }
}
