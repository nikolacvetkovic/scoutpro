package xyz.riocode.scoutpro.scrape.model;

import lombok.*;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.scheduler.model.JobInfo;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "scrape_error_history")
public class ScrapeErrorHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "scrape_time")
    private LocalDateTime scrapeTime;
    @ManyToOne
    @JoinColumn(name = "job_id", referencedColumnName = "id")
    private JobInfo jobInfo;
    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private Player player;
    @Column(name = "stack_trace", columnDefinition = "text")
    private String stackTrace;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScrapeErrorHistory that = (ScrapeErrorHistory) o;

        if (!Objects.equals(scrapeTime, that.scrapeTime)) return false;
        if (!Objects.equals(jobInfo, that.jobInfo)) return false;
        if (!Objects.equals(player, that.player)) return false;
        return Objects.equals(stackTrace, that.stackTrace);
    }

    @Override
    public int hashCode() {
        int result = scrapeTime != null ? scrapeTime.hashCode() : 0;
        result = 31 * result + (jobInfo != null ? jobInfo.hashCode() : 0);
        result = 31 * result + (player != null ? player.hashCode() : 0);
        result = 31 * result + (stackTrace != null ? stackTrace.hashCode() : 0);
        return result;
    }
}
