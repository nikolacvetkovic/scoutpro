package xyz.riocode.scoutpro.converter;

import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.dto.JobDTO;
import xyz.riocode.scoutpro.scheduler.enums.JobStatus;
import xyz.riocode.scoutpro.scheduler.model.JobInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class JobConverter {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").withLocale(Locale.US);

    public JobDTO jobToJobDTO(JobInfo job) {
        return JobDTO.builder()
                .id(job.getId().toString())
                .name(job.getJobName())
                .group(job.getJobGroup())
                .status(job.getJobStatus().name())
                .startTime(job.getStartTime()!=null? job.getStartTime().format(dateTimeFormatter):"")
                .endTime(job.getEndTime()!=null? job.getEndTime().format(dateTimeFormatter):"")
                .cronJob(job.isCronJob())
                .cronExp(job.getCronExpression())
                .repeatCount(job.getRepeatCount())
                .repeatInterval(job.getRepeatIntervalInSeconds())
                .customConfig(job.getCustomConfigData())
                .build();
    }

    public JobInfo jobDTOToJobInfo(JobDTO job) {
        return JobInfo.builder()
                .id(Long.valueOf(job.getId()))
                .jobName(job.getName())
                .jobGroup(job.getGroup())
                .jobStatus(JobStatus.valueOf(job.getStatus()))
                .startTime((job.getStartTime()!=null && !job.getStartTime().isEmpty()) ? LocalDateTime.parse(job.getStartTime(), dateTimeFormatter):null)
                .endTime((job.getEndTime()!=null && !job.getEndTime().isEmpty()) ? LocalDateTime.parse(job.getEndTime(), dateTimeFormatter):null)
                .cronJob(job.isCronJob())
                .cronExpression(job.getCronExp())
                .repeatCount(job.getRepeatCount())
                .repeatIntervalInSeconds(job.getRepeatInterval())
                .customConfigData(job.getCustomConfig())
                .build();
    }

    public List<JobDTO> jobsToJobDTOs(List<JobInfo> jobs) {
        return jobs.stream()
                .map(this::jobToJobDTO)
                .collect(Collectors.toList());
    }
}
