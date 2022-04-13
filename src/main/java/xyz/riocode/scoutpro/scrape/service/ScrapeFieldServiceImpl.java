package xyz.riocode.scoutpro.scrape.service;

import org.springframework.stereotype.Service;
import xyz.riocode.scoutpro.converter.ScrapeFieldConverter;
import xyz.riocode.scoutpro.exception.ScrapeFieldNotFound;
import xyz.riocode.scoutpro.scrape.model.ScrapeField;
import xyz.riocode.scoutpro.scrape.repository.ScrapeFieldRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ScrapeFieldServiceImpl implements ScrapeFieldService {

    private final ScrapeFieldRepository scrapeFieldRepository;

    public ScrapeFieldServiceImpl(ScrapeFieldRepository scrapeFieldRepository, ScrapeFieldConverter scrapeFieldConverter) {
        this.scrapeFieldRepository = scrapeFieldRepository;
    }

    @Override
    public ScrapeField getByName(String name) {
        return scrapeFieldRepository.findByName(name);
    }

    @Override
    public List<ScrapeField> getByScrapeSite(Long scrapeSiteId) {
        return scrapeFieldRepository.findByScrapeSite_Id(scrapeSiteId);
    }

    @Override
    public Set<ScrapeField> getAll() {
        return new HashSet<>(scrapeFieldRepository.findAll());
    }

    @Override
    public ScrapeField update(ScrapeField scrapeField) {
        ScrapeField foundScrapeField = scrapeFieldRepository.findById(scrapeField.getId()).orElseThrow(() -> new ScrapeFieldNotFound(scrapeField));
        foundScrapeField.setSelector(scrapeField.getSelector());
        return scrapeFieldRepository.save(foundScrapeField);
    }
}
