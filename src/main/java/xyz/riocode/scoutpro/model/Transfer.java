package xyz.riocode.scoutpro.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transfer")
//@NamedQueries({
//    @NamedQuery(name = "Transfer.findAll", query = "SELECT t FROM Transfer t")
//    , @NamedQuery(name = "Transfer.findById", query = "SELECT t FROM Transfer t WHERE t.id = :id")})
public class Transfer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "from_team")
    private String fromTeam;
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "to_team")
    private String toTeam;

    @NotNull
    @Column(name = "date_of_transfer")
    private LocalDate dateOfTransfer;
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "market_value")
    private String marketValue;
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "transfer_fee")
    private String transferFee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private Player player;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transfer)) return false;

        Transfer transfer = (Transfer) o;

        if (getFromTeam() != null ? !getFromTeam().equals(transfer.getFromTeam()) : transfer.getFromTeam() != null)
            return false;
        if (getToTeam() != null ? !getToTeam().equals(transfer.getToTeam()) : transfer.getToTeam() != null)
            return false;
        if (getDateOfTransfer() != null ? !getDateOfTransfer().equals(transfer.getDateOfTransfer()) : transfer.getDateOfTransfer() != null)
            return false;
        if (getMarketValue() != null ? !getMarketValue().equals(transfer.getMarketValue()) : transfer.getMarketValue() != null)
            return false;
        return getTransferFee() != null ? getTransferFee().equals(transfer.getTransferFee()) : transfer.getTransferFee() == null;
    }

    @Override
    public int hashCode() {
        int result = getFromTeam() != null ? getFromTeam().hashCode() : 0;
        result = 31 * result + (getToTeam() != null ? getToTeam().hashCode() : 0);
        result = 31 * result + (getDateOfTransfer() != null ? getDateOfTransfer().hashCode() : 0);
        result = 31 * result + (getMarketValue() != null ? getMarketValue().hashCode() : 0);
        result = 31 * result + (getTransferFee() != null ? getTransferFee().hashCode() : 0);
        return result;
    }
}
