package xyz.riocode.scoutpro.scrape.service;

import xyz.riocode.scoutpro.scrape.model.ScrapeField;
import xyz.riocode.scoutpro.scrape.model.ScrapeSite;

import java.util.Map;
import java.util.Set;

public interface ScrapeFieldService {
    ScrapeField getByName(String name);
    Map<String, String> getByScrapeSite(ScrapeSite scrapeSite);
    Set<ScrapeField> getAll();
    void update(ScrapeField scrapeField);
}
