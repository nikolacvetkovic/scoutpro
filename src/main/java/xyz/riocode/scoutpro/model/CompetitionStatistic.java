package xyz.riocode.scoutpro.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "competition_statistic")
public class CompetitionStatistic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "competition_name")
    private String competitionName;
    @NotNull
    @Column(name = "appearances")
    private String appearances;
    @NotNull
    @Column(name = "goals")
    private String goals;
    @NotNull
    @Column(name = "assists")
    private String assists;
    @NotNull
    @Column(name = "yellow_cards")
    private String yellowCards;
    @NotNull
    @Column(name = "red_cards")
    private String redCards;
    @NotNull
    @Column(name = "minutes_played")
    private String minutesPlayed;
    @ManyToOne(optional = false)
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private Player player;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompetitionStatistic that = (CompetitionStatistic) o;

        if (!Objects.equals(competitionName, that.competitionName))
            return false;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        int result = competitionName != null ? competitionName.hashCode() : 0;
        result = 31 * result + (player != null ? player.hashCode() : 0);
        return result;
    }
}
