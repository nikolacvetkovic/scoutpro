package xyz.riocode.scoutpro.converter;

import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.dto.ScrapeErrorDTO;
import xyz.riocode.scoutpro.scrape.model.ScrapeError;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class ScrapeErrorConverter {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").withLocale(Locale.US);

    public List<ScrapeErrorDTO> scrapeErrorToScrapeErrorDTO(List<ScrapeError> scrapeErrors) {
        return scrapeErrors.stream().map(scrapeError -> {
            return ScrapeErrorDTO.builder()
                    .time(scrapeError.getScrapeTime().format(dateTimeFormatter))
                    .jobName(scrapeError.getJobInfo()!=null?scrapeError.getJobInfo().getJobName():"")
                    .playerName(scrapeError.getPlayer()!=null?scrapeError.getPlayer().getName():"")
                    .build();
        }).collect(Collectors.toList());
    }

}
