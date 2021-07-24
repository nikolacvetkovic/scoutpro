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
@Table(name = "competition_statistic")
public class CompetitionStatistic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "competition")
    private String competition;
    @NotNull
    @Column(name = "started_apps")
    private int startedApps;
    @NotNull
    @Column(name = "sub_apps")
    private int subApps;
    @NotNull
    @Column(name = "mins")
    private int mins;
    @NotNull
    @Column(name = "goals")
    private int goals;
    @NotNull
    @Column(name = "assists")
    private int assists;
    @NotNull
    @Column(name = "yellow_cards")
    private int yellowCards;
    @NotNull
    @Column(name = "red_cards")
    private int redCards;
    @NotNull
    @DecimalMin("0.0")
    @Column(name = "shots_per_game")
    private BigDecimal shotsPerGame;
    @NotNull
    @DecimalMin("0.00")
    @DecimalMax("100.00")
    @Column(name = "pass_success")
    private BigDecimal passSuccess;
    @NotNull
    @DecimalMin("0.00")
    @Column(name = "aerials_won")
    private BigDecimal aerialsWon;
    @NotNull
    @Column(name = "man_of_the_match")
    private int manOfTheMatch;
    @NotNull
    @DecimalMin("0.00")
    @DecimalMax("10.00")
    @Column(name = "rating")
    private BigDecimal rating;
    @ManyToOne(optional = false)
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private Player player;
}
