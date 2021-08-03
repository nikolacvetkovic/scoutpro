package xyz.riocode.scoutpro.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlayerSearchDTO {

    private String id;
    private String playerName;
    private String position;
    private int overallRating;
    private String tmCurrentValue;
    private String psmlValue;
    private String psmlTeam;
}
