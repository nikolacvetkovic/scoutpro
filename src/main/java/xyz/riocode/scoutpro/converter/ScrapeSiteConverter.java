package xyz.riocode.scoutpro.converter;

import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.dto.ScrapeSiteDTO;
import xyz.riocode.scoutpro.scrape.model.ScrapeSite;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScrapeSiteConverter {

    public List<ScrapeSiteDTO> scrapeSiteToScrapeSiteDTO(List<ScrapeSite> scrapeSites) {
        return scrapeSites.stream().map(scrapeSite -> {
            return ScrapeSiteDTO.builder()
                    .id(scrapeSite.getId().toString())
                    .name(scrapeSite.getName())
                    .build();
        }).collect(Collectors.toList());
    }

}
