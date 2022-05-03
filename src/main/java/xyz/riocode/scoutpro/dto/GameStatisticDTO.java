package xyz.riocode.scoutpro.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GameStatisticDTO {

    private String dateOfGame;
    private String playerTeam;
    private String opponentTeam;
    private String homeAwayFlag;
    private String result;
    private String position;
    private String goals;
    private String assists;
    private boolean yellowCard;
    private boolean redCard;
    private String minutesPlayed;
    private String notPlayedReason;

}
