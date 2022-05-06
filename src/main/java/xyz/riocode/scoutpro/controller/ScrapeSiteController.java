package xyz.riocode.scoutpro.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.riocode.scoutpro.converter.ScrapeSiteConverter;
import xyz.riocode.scoutpro.dto.ScrapeSiteDTO;
import xyz.riocode.scoutpro.scrape.service.ScrapeSiteService;
import xyz.riocode.scoutpro.security.privilege.ScrapeSiteCheckPrivilege;

@RequestMapping("/scrape/site")
@Controller
public class ScrapeSiteController {

    private final ScrapeSiteService scrapeSiteService;

    public ScrapeSiteController(ScrapeSiteService scrapeSiteService) {
        this.scrapeSiteService = scrapeSiteService;
    }

    @ScrapeSiteCheckPrivilege
    @GetMapping(value = "/{scrapeSiteId}/check")
    public ResponseEntity<ScrapeSiteDTO> checkScrapeStatus(@PathVariable Long scrapeSiteId) {
        return new ResponseEntity<ScrapeSiteDTO>(ScrapeSiteConverter.scrapeSiteToScrapeSiteDTO(
                                                        scrapeSiteService.checkScrape(scrapeSiteId)), HttpStatus.OK);
    }
}
