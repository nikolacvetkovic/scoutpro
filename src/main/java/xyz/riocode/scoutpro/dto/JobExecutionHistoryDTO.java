package xyz.riocode.scoutpro.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobExecutionHistoryDTO {
    private String jobName;
    private String jobStatus;
    private String startTime;
    private String endTime;
    private String status;
    private String playersProcessed;
    private String playersWithError;
    private String errorStackTrace;
}
