package xyz.riocode.scoutpro.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScrapeSiteDTO {

    private String id;
    private String name;
    private String status;
    private String lastChecked;
    private String jobId;

}
