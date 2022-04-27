package xyz.riocode.scoutpro.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {

    private String id;
    private String name;
    private String group;
    private String status;
    private String startTime;
    private String endTime;
    private boolean cronJob;
    private String cronExp;
    private Integer repeatCount;
    private Integer repeatInterval;
    private String customConfig;

}
