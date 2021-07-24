package xyz.riocode.scoutpro.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CompetitionStatisticDTO {

    private String competition;
    private int startedApps;
    private int subApps;
    private int mins;
    private int goals;
    private int assists;
    private int yellowCards;
    private int redCards;
    private String shotsPerGame;
    private String passSuccess;
    private String aerialsWon;
    private int manOfTheMatch;
    private String rating;

}
