package xyz.riocode.scoutpro.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScrapeErrorDTO {

    private String time;
    private String jobName;
    private String playerName;
    private String stackTrace;

}
