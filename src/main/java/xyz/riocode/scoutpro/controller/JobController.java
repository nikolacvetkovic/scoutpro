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

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
    @JobReadPrivilege
    @GetMapping
    public List<JobDTO> getAllJobs(){
        return JobConverter.jobsToJobDTOs(jobService.getAll());
    }

    @JobUpdatePrivilege
    @PutMapping("/{jobId}/update")
    public JobDTO updateJob(@PathVariable Long jobId, @Valid @RequestBody JobDTO job){
        return JobConverter.jobToJobDTO(
                    jobService.updateJob(jobId, JobConverter.jobDTOToJobInfo(job)));
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
        return JobConverter.jobToJobDTO(jobService.scheduleJob(jobId, JobConverter.jobDTOToJobInfo(job)));
    }

    @JobSchedulePrivilege
    @PutMapping("/{jobId}/reschedule")
    public JobDTO rescheduleJob(@PathVariable Long jobId, @RequestBody JobDTO job){
        return JobConverter.jobToJobDTO(jobService.rescheduleJob(jobId, JobConverter.jobDTOToJobInfo(job)));
    }

    @JobUnschedulePrivilege
    @PutMapping("/{jobId}/unschedule")
    public JobDTO unscheduleJob(@PathVariable Long jobId){
        return JobConverter.jobToJobDTO(jobService.unscheduleJob(jobId));
    }

    @JobPausePrivilege
    @PutMapping("/{jobId}/pause")
    public JobDTO pauseJob(@PathVariable Long jobId){
        return JobConverter.jobToJobDTO(jobService.pauseJob(jobId));
    }

    @JobResumePrivilege
    @PutMapping("/{jobId}/resume")
    public JobDTO resumeJob(@PathVariable Long jobId){
        return JobConverter.jobToJobDTO(jobService.resumeJob(jobId));
    }
}
