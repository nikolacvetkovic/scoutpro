package xyz.riocode.scoutpro.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlayerDTO {

    private String name;
    private String psmlValue;
    private String psmlTeam;

}
