package xyz.riocode.scoutpro.converter;

import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.dto.ScrapeSiteDTO;
import xyz.riocode.scoutpro.scrape.model.ScrapeSite;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class ScrapeSiteConverter {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").withLocale(Locale.US);

    public List<ScrapeSiteDTO> scrapeSitesToScrapeSiteDTOs(List<ScrapeSite> scrapeSites) {
        return scrapeSites.stream()
                .map(this::scrapeSiteToScrapeSiteDTO)
                .collect(Collectors.toList());
    }

    public ScrapeSiteDTO scrapeSiteToScrapeSiteDTO(ScrapeSite scrapeSite) {
            return ScrapeSiteDTO.builder()
                    .id(scrapeSite.getId().toString())
                    .name(scrapeSite.getName())
                    .status(scrapeSite.getStatus().name())
                    .lastChecked(scrapeSite.getLastChecked().format(dateTimeFormatter))
                    .jobId(scrapeSite.getScrapeCheckJob().getId().toString())
                    .build();
    }

}
