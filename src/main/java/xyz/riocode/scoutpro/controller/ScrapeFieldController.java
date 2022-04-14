package xyz.riocode.scoutpro.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.riocode.scoutpro.converter.ScrapeFieldConverter;
import xyz.riocode.scoutpro.dto.ScrapeFieldDTO;
import xyz.riocode.scoutpro.scrape.service.ScrapeFieldService;

import java.util.List;

@RequestMapping("/scrapefield")
@Controller
public class ScrapeFieldController {

    private final ScrapeFieldService scrapeFieldService;
    private final ScrapeFieldConverter scrapeFieldConverter;

    public ScrapeFieldController(ScrapeFieldService scrapeFieldService, ScrapeFieldConverter scrapeFieldConverter) {
        this.scrapeFieldService = scrapeFieldService;
        this.scrapeFieldConverter = scrapeFieldConverter;
    }

    @GetMapping(value = "/{scrapeSiteId}/site",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ScrapeFieldDTO>> getScrapeFieldsByScrapeSite(@PathVariable Long scrapeSiteId) {
        return new ResponseEntity<>(scrapeFieldConverter.scrapeFieldsToScrapeFieldDTOs(
                                        scrapeFieldService.getByScrapeSite(scrapeSiteId)), HttpStatus.OK);
    }

    @PutMapping(value = "/{scrapeFieldId}",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScrapeFieldDTO> updateScrapeField(@PathVariable Long scrapeFieldId,
                                                            @RequestBody ScrapeFieldDTO scrapeFieldDTO){

        return new ResponseEntity<>(scrapeFieldConverter.scrapeFieldToScrapeFieldDTO(
                                    scrapeFieldService.update(
                                        scrapeFieldConverter.scrapeFieldDTOToScrapeField(scrapeFieldDTO))), HttpStatus.OK);
    }

}