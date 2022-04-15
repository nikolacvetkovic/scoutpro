package xyz.riocode.scoutpro.scheduler.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "job_info")
public class JobInfo implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "job_name")
    private String jobName;
    @Column(name = "job_group")
    private String jobGroup;
    @Column(name = "job_status")
    private String jobStatus;
    @Column(name = "job_class")
    private String jobClass;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "cron_job")
    private Boolean cronJob;
    @Column(name = "cron_expression")
    private String cronExpression;
    @Column(name = "repeat_interval_in_seconds")
    private Integer repeatIntervalInSeconds;
    @Column(name = "repeat_count")
    private Integer repeatCount;

    @Lob
    @Column(name = "customConfigData")
    private String customConfigData;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobInfo jobInfo = (JobInfo) o;

        if (!Objects.equals(jobName, jobInfo.jobName)) return false;
        if (!Objects.equals(jobGroup, jobInfo.jobGroup)) return false;
        if (!Objects.equals(jobStatus, jobInfo.jobStatus)) return false;
        if (!Objects.equals(jobClass, jobInfo.jobClass)) return false;
        if (!Objects.equals(startTime, jobInfo.startTime)) return false;
        if (!Objects.equals(endTime, jobInfo.endTime)) return false;
        if (!Objects.equals(cronJob, jobInfo.cronJob)) return false;
        if (!Objects.equals(cronExpression, jobInfo.cronExpression))
            return false;
        if (!Objects.equals(repeatIntervalInSeconds, jobInfo.repeatIntervalInSeconds))
            return false;
        if (!Objects.equals(repeatCount, jobInfo.repeatCount)) return false;
        return Objects.equals(customConfigData, jobInfo.customConfigData);
    }

    @Override
    public int hashCode() {
        int result = jobName != null ? jobName.hashCode() : 0;
        result = 31 * result + (jobGroup != null ? jobGroup.hashCode() : 0);
        result = 31 * result + (jobStatus != null ? jobStatus.hashCode() : 0);
        result = 31 * result + (jobClass != null ? jobClass.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (cronJob != null ? cronJob.hashCode() : 0);
        result = 31 * result + (cronExpression != null ? cronExpression.hashCode() : 0);
        result = 31 * result + (repeatIntervalInSeconds != null ? repeatIntervalInSeconds.hashCode() : 0);
        result = 31 * result + (repeatCount != null ? repeatCount.hashCode() : 0);
        result = 31 * result + (customConfigData != null ? customConfigData.hashCode() : 0);
        return result;
    }
}
