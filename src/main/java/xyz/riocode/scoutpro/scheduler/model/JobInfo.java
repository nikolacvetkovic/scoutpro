package xyz.riocode.scoutpro.scheduler.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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

}
