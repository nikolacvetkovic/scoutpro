package xyz.riocode.scoutpro.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PositionStatisticDTO {

    private String position;
    private int apps;
    private int goals;
    private int assists;
    private String rating;
}
