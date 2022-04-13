package xyz.riocode.scoutpro.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScrapeFieldDTO {

    private String id;
    private String name;
    private String selector;

}
