package xyz.riocode.scoutpro.scrape.service;

import xyz.riocode.scoutpro.scrape.model.ScrapeSite;

import java.time.LocalDateTime;
import java.util.List;

public interface ScrapeSiteService {
    List<ScrapeSite> getAll();
    ScrapeSite getById(Long id);
    List<ScrapeSite> getByLastCheckedBefore(LocalDateTime lastChecked);
    ScrapeSite checkScrape(Long id);
}
