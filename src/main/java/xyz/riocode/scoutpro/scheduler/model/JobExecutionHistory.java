package xyz.riocode.scoutpro.scheduler.model;

import lombok.*;
import xyz.riocode.scoutpro.scheduler.enums.JobExecutionStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "job_execution_history")
public class JobExecutionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private JobExecutionStatus status;
    @ManyToOne
    @JoinColumn(name = "job_id", referencedColumnName = "id")
    private JobInfo jobInfo;
    @Column(name = "players_processed")
    private Long playersProcessed;
    @Column(name = "players_with_error")
    private Long playersWithError;
    @Column(name = "error_stack_trace", columnDefinition = "text")
    private String errorStackTrace;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobExecutionHistory that = (JobExecutionHistory) o;

        if (!Objects.equals(startTime, that.startTime)) return false;
        if (!Objects.equals(endTime, that.endTime)) return false;
        if (status != that.status) return false;
        if (!Objects.equals(jobInfo, that.jobInfo)) return false;
        if (!Objects.equals(playersProcessed, that.playersProcessed))
            return false;
        return Objects.equals(playersWithError, that.playersWithError);
    }

    @Override
    public int hashCode() {
        int result = startTime != null ? startTime.hashCode() : 0;
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (jobInfo != null ? jobInfo.hashCode() : 0);
        result = 31 * result + (playersProcessed != null ? playersProcessed.hashCode() : 0);
        result = 31 * result + (playersWithError != null ? playersWithError.hashCode() : 0);
        return result;
    }
}
