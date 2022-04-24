package xyz.riocode.scoutpro.scrape.service;

import org.springframework.stereotype.Service;
import xyz.riocode.scoutpro.scrape.model.ScrapeError;
import xyz.riocode.scoutpro.scrape.repository.ScrapeErrorRepository;

import java.util.List;

@Service
public class ScrapeErrorServiceImpl implements ScrapeErrorService{

    private final ScrapeErrorRepository scrapeErrorRepository;

    public ScrapeErrorServiceImpl(ScrapeErrorRepository scrapeErrorRepository) {
        this.scrapeErrorRepository = scrapeErrorRepository;
    }

    @Override
    public List<ScrapeError> getAll() {
        return scrapeErrorRepository.findAll();
    }
}
