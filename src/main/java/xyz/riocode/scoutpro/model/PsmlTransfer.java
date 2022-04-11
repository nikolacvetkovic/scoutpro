package xyz.riocode.scoutpro.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "psml_transfer")
public class PsmlTransfer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "from_team")
    private String fromTeam;
    @Column(name = "to_team")
    private String toTeam;
    @Column(name = "transfer_fee")
    private String transferFee;

    @Column(name = "date_of_transfer")
    private LocalDate dateOfTransfer;
    @ManyToOne(optional = false)
    @JoinColumn(name = "player_id",referencedColumnName = "id")
    private Player player;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PsmlTransfer that = (PsmlTransfer) o;

        if (!Objects.equals(fromTeam, that.fromTeam)) return false;
        if (!Objects.equals(toTeam, that.toTeam)) return false;
        if (!Objects.equals(transferFee, that.transferFee)) return false;
        if (!Objects.equals(dateOfTransfer, that.dateOfTransfer))
            return false;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        int result = fromTeam != null ? fromTeam.hashCode() : 0;
        result = 31 * result + (toTeam != null ? toTeam.hashCode() : 0);
        result = 31 * result + (transferFee != null ? transferFee.hashCode() : 0);
        result = 31 * result + (dateOfTransfer != null ? dateOfTransfer.hashCode() : 0);
        result = 31 * result + (player != null ? player.hashCode() : 0);
        return result;
    }
}
