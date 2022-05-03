package xyz.riocode.scoutpro.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "game_statistic")
public class GameStatistic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @NotNull
    @Column(name = "date_of_game")
    private LocalDate dateOfGame;
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "player_team")
    private String playerTeam;
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "opponent_team")
    private String opponentTeam;
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "home_away_flag")
    private String homeAwayFlag;
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "result")
    private String result;
    @Column(name = "position")
    private String position;
    @Column(name = "goals")
    private String goals;
    @Column(name = "assists")
    private String assists;
    @Column(name = "yellow_card")
    private boolean yellowCard;
    @Column(name = "red_card")
    private boolean redCard;
    @Column(name = "minutes_played")
    private String minutesPlayed;
    @Column(name = "not_played_reason")
    private String notPlayedReason;
    @ManyToOne(optional = false)
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private Player player;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameStatistic that = (GameStatistic) o;

        if (yellowCard != that.yellowCard) return false;
        if (redCard != that.redCard) return false;
        if (!Objects.equals(dateOfGame, that.dateOfGame)) return false;
        if (!Objects.equals(playerTeam, that.playerTeam)) return false;
        if (!Objects.equals(opponentTeam, that.opponentTeam)) return false;
        if (!Objects.equals(homeAwayFlag, that.homeAwayFlag)) return false;
        if (!Objects.equals(result, that.result)) return false;
        if (!Objects.equals(position, that.position)) return false;
        if (!Objects.equals(goals, that.goals)) return false;
        if (!Objects.equals(assists, that.assists)) return false;
        if (!Objects.equals(minutesPlayed, that.minutesPlayed))
            return false;
        if (!Objects.equals(notPlayedReason, that.notPlayedReason))
            return false;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        int result1 = dateOfGame != null ? dateOfGame.hashCode() : 0;
        result1 = 31 * result1 + (playerTeam != null ? playerTeam.hashCode() : 0);
        result1 = 31 * result1 + (opponentTeam != null ? opponentTeam.hashCode() : 0);
        result1 = 31 * result1 + (homeAwayFlag != null ? homeAwayFlag.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (position != null ? position.hashCode() : 0);
        result1 = 31 * result1 + (goals != null ? goals.hashCode() : 0);
        result1 = 31 * result1 + (assists != null ? assists.hashCode() : 0);
        result1 = 31 * result1 + (yellowCard ? 1 : 0);
        result1 = 31 * result1 + (redCard ? 1 : 0);
        result1 = 31 * result1 + (minutesPlayed != null ? minutesPlayed.hashCode() : 0);
        result1 = 31 * result1 + (notPlayedReason != null ? notPlayedReason.hashCode() : 0);
        result1 = 31 * result1 + (player != null ? player.hashCode() : 0);
        return result1;
    }
}
