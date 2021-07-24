package xyz.riocode.scoutpro.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.time.LocalDate;

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
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "competition")
    private String competition;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @NotNull
    @Column(name = "date_of_game")
    private LocalDate dateOfGame;
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "team1")
    private String team1;
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "team2")
    private String team2;
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "result")
    private String result;
    @NotNull
    @Column(name = "man_of_the_match")
    private boolean manOfTheMatch;
    @NotNull
    @Column(name = "goals")
    private int goals;
    @NotNull
    @Column(name = "assists")
    private int assists;
    @NotNull
    @Column(name = "yellow_card")
    private boolean yellowCard;
    @NotNull
    @Column(name = "red_card")
    private boolean redCard;
    @NotNull
    @Column(name = "minutes_played")
    private int minutesPlayed;
    @NotNull
    @DecimalMin("0.00")
    @DecimalMax("10.00")
    @Column(name = "rating")
    private BigDecimal rating;
    @ManyToOne(optional = false)
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private Player player;

}
