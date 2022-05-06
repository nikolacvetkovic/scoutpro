package xyz.riocode.scoutpro.converter;

import xyz.riocode.scoutpro.dto.ScrapeFieldDTO;
import xyz.riocode.scoutpro.scrape.model.ScrapeField;

import java.util.List;
import java.util.stream.Collectors;

public class ScrapeFieldConverter {

    public static List<ScrapeFieldDTO> scrapeFieldsToScrapeFieldDTOs(List<ScrapeField> scrapeFields) {
        return scrapeFields.stream()
                .map(ScrapeFieldConverter::scrapeFieldToScrapeFieldDTO)
                .collect(Collectors.toList());
    }

    public static ScrapeField scrapeFieldDTOToScrapeField(ScrapeFieldDTO scrapeFieldDTO) {
        return ScrapeField.builder()
                .id(Long.valueOf(scrapeFieldDTO.getId()))
                .name(scrapeFieldDTO.getName())
                .selector(scrapeFieldDTO.getSelector())
                .build();
    }

    public static ScrapeFieldDTO scrapeFieldToScrapeFieldDTO(ScrapeField scrapeField) {
        return ScrapeFieldDTO.builder()
                .id(scrapeField.getId().toString())
                .name(scrapeField.getName())
                .selector(scrapeField.getSelector())
                .build();
    }

}
