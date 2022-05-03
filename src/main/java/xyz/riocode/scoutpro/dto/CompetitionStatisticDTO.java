package xyz.riocode.scoutpro.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CompetitionStatisticDTO {

    private String competitionName;
    private String appearances;
    private String goals;
    private String assists;
    private String yellowCards;
    private String redCards;
    private String minutesPlayed;

}
