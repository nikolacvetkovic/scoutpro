package xyz.riocode.scoutpro.converter;

import xyz.riocode.scoutpro.dto.ScrapeErrorDTO;
import xyz.riocode.scoutpro.scrape.model.ScrapeError;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ScrapeErrorConverter {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").withLocale(Locale.US);

    public static List<ScrapeErrorDTO> scrapeErrorToScrapeErrorDTO(List<ScrapeError> scrapeErrors) {
        return scrapeErrors.stream()
                .sorted(Comparator.comparing(ScrapeError::getScrapeTime).reversed())
                .map(scrapeError -> {
                    return ScrapeErrorDTO.builder()
                            .time(scrapeError.getScrapeTime().format(dateTimeFormatter))
                            .jobName(scrapeError.getJobInfo()!=null?scrapeError.getJobInfo().getJobName():"")
                            .playerName(scrapeError.getPlayer()!=null?scrapeError.getPlayer().getName():"")
                            .stackTrace(scrapeError.getStackTrace())
                            .build();
        }).collect(Collectors.toList());
    }

}
