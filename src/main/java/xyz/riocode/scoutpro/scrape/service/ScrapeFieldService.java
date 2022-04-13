package xyz.riocode.scoutpro.scrape.service;

import xyz.riocode.scoutpro.scrape.model.ScrapeField;

import java.util.List;
import java.util.Set;

public interface ScrapeFieldService {
    ScrapeField getByName(String name);
    List<ScrapeField> getByScrapeSite(Long scrapeSiteId);
    Set<ScrapeField> getAll();
    ScrapeField update(ScrapeField scrapeField);
}
