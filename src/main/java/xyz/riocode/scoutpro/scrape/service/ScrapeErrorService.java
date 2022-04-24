package xyz.riocode.scoutpro.scrape.service;

import xyz.riocode.scoutpro.scrape.model.ScrapeError;

import java.util.List;

public interface ScrapeErrorService {
    List<ScrapeError> getAll();
}
