package xyz.riocode.scoutpro.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "position_statistic")
public class PositionStatistic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "position")
    private String position;
    @NotNull
    @Column(name = "apps")
    private int apps;
    @NotNull
    @Column(name = "goals")
    private int goals;
    @NotNull
    @Column(name = "assists")
    private int assists;
    @NotNull
    @DecimalMin("0.00")
    @DecimalMax("10.00")
    @Column(name = "rating")
    private BigDecimal rating;
    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private Player player;
}
