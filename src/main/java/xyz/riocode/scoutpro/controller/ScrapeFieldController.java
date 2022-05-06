package xyz.riocode.scoutpro.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.riocode.scoutpro.converter.ScrapeFieldConverter;
import xyz.riocode.scoutpro.dto.ScrapeFieldDTO;
import xyz.riocode.scoutpro.scrape.service.ScrapeFieldService;
import xyz.riocode.scoutpro.security.privilege.ScrapeFieldReadPrivilege;
import xyz.riocode.scoutpro.security.privilege.ScrapeFieldUpdatePrivilege;

import java.util.List;

@RequestMapping("/scrape/field")
@Controller
public class ScrapeFieldController {

    private final ScrapeFieldService scrapeFieldService;

    public ScrapeFieldController(ScrapeFieldService scrapeFieldService) {
        this.scrapeFieldService = scrapeFieldService;
    }

    @ScrapeFieldReadPrivilege
    @GetMapping(value = "/{scrapeSiteId}/site",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ScrapeFieldDTO>> getScrapeFieldsByScrapeSite(@PathVariable Long scrapeSiteId) {
        return new ResponseEntity<>(ScrapeFieldConverter.scrapeFieldsToScrapeFieldDTOs(
                scrapeFieldService.getByScrapeSite(scrapeSiteId)), HttpStatus.OK);
    }

    @ScrapeFieldUpdatePrivilege
    @PutMapping(value = "/{scrapeFieldId}",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScrapeFieldDTO> updateScrapeField(@PathVariable Long scrapeFieldId,
                                                            @RequestBody ScrapeFieldDTO scrapeFieldDTO){

        return new ResponseEntity<>(ScrapeFieldConverter.scrapeFieldToScrapeFieldDTO(
                                    scrapeFieldService.update(
                                        ScrapeFieldConverter.scrapeFieldDTOToScrapeField(scrapeFieldDTO))), HttpStatus.OK);
    }
}
