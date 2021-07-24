package xyz.riocode.scoutpro.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GameStatisticDTO {

    private String competition;
    private String dateOfGame;
    private String team1;
    private String team2;
    private String result;
    private boolean manOfTheMatch;
    private int goals;
    private int assists;
    private boolean yellowCard;
    private boolean redCard;
    private int minutesPlayed;
    private String rating;

}
