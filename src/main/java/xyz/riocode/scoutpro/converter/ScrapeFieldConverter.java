package xyz.riocode.scoutpro.converter;

import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.dto.ScrapeFieldDTO;
import xyz.riocode.scoutpro.scrape.model.ScrapeField;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScrapeFieldConverter {

    public List<ScrapeFieldDTO> scrapeFieldsToScrapeFieldDTOs(List<ScrapeField> scrapeFields) {
        return scrapeFields.stream()
                .map(this::scrapeFieldToScrapeFieldDTO)
                .collect(Collectors.toList());
    }

    public ScrapeField scrapeFieldDTOToScrapeField(ScrapeFieldDTO scrapeFieldDTO) {
        return ScrapeField.builder()
                .id(Long.valueOf(scrapeFieldDTO.getId()))
                .name(scrapeFieldDTO.getName())
                .selector(scrapeFieldDTO.getSelector())
                .build();
    }

    public ScrapeFieldDTO scrapeFieldToScrapeFieldDTO(ScrapeField scrapeField) {
        return ScrapeFieldDTO.builder()
                .id(scrapeField.getId().toString())
                .name(scrapeField.getName())
                .selector(scrapeField.getSelector())
                .build();
    }

}
