package xyz.riocode.scoutpro.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "market_value")
//@NamedQueries({
//    @NamedQuery(name = "MarketValue.findAll", query = "SELECT m FROM MarketValue m")
//    , @NamedQuery(name = "MarketValue.findById", query = "SELECT m FROM MarketValue m WHERE m.id = :id")})
public class MarketValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotNull
    @Column(name = "worth")
    private BigDecimal worth;
    @NotNull
    @Column(name = "date_point")
    private LocalDate datePoint;
    @NotNull
    @Column(name = "club_team")
    private String clubTeam;

    @ManyToOne(optional = false)
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private Player player;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MarketValue)) return false;

        MarketValue that = (MarketValue) o;

        if (getWorth() != null ? !getWorth().equals(that.getWorth()) : that.getWorth() != null) return false;
        if (getDatePoint() != null ? !getDatePoint().equals(that.getDatePoint()) : that.getDatePoint() != null)
            return false;
        return getClubTeam() != null ? getClubTeam().equals(that.getClubTeam()) : that.getClubTeam() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getWorth() != null ? getWorth().hashCode() : 0);
        result = 31 * result + (getDatePoint() != null ? getDatePoint().hashCode() : 0);
        result = 31 * result + (getClubTeam() != null ? getClubTeam().hashCode() : 0);
        result = 31 * result + (getPlayer() != null ? getPlayer().hashCode() : 0);
        return result;
    }
}
