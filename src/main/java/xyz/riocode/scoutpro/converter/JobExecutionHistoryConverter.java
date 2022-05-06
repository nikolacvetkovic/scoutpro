package xyz.riocode.scoutpro.converter;

import xyz.riocode.scoutpro.dto.JobExecutionHistoryDTO;
import xyz.riocode.scoutpro.scheduler.model.JobExecutionHistory;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class JobExecutionHistoryConverter {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").withLocale(Locale.US);

    public static List<JobExecutionHistoryDTO> jobExecutionToJobExecutionDTO(List<JobExecutionHistory> jobExecutionHistories) {
        return jobExecutionHistories.stream()
                .sorted(Comparator.comparing(JobExecutionHistory::getEndTime).reversed())
                .map(jobExecutionHistory -> {
                    return JobExecutionHistoryDTO.builder()
                            .jobName(jobExecutionHistory.getJobInfo().getJobName())
                            .jobStatus(jobExecutionHistory.getJobInfo().getJobStatus().name())
                            .startTime(jobExecutionHistory.getStartTime().format(dateTimeFormatter))
                            .endTime(jobExecutionHistory.getEndTime().format(dateTimeFormatter))
                            .status(jobExecutionHistory.getStatus().name())
                            .playersProcessed(jobExecutionHistory.getPlayersProcessed().toString())
                            .playersWithError(jobExecutionHistory.getPlayersWithError().toString())
                            .errorStackTrace(jobExecutionHistory.getErrorStackTrace())
                            .build();
        }).collect(Collectors.toList());
    }

}
