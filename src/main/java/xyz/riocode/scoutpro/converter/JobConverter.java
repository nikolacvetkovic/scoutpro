package xyz.riocode.scoutpro.converter;

import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.dto.JobDTO;
import xyz.riocode.scoutpro.scheduler.model.JobInfo;

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
                .cronExp(job.getCronExpression())
                .repeatCount(job.getRepeatCount())
                .repeatInterval(job.getRepeatIntervalInSeconds())
                .customConfig(job.getCustomConfigData())
                .build();
    }

    public List<JobDTO> jobToJobDTOs(List<JobInfo> jobs) {
        return jobs.stream()
                .map(this::jobToJobDTO)
                .collect(Collectors.toList());
    }
}
