package xyz.riocode.scoutpro.scrape.service;

import org.springframework.stereotype.Service;
import xyz.riocode.scoutpro.exception.ScrapeFieldNotFound;
import xyz.riocode.scoutpro.scrape.model.ScrapeField;
import xyz.riocode.scoutpro.scrape.model.ScrapeSite;
import xyz.riocode.scoutpro.scrape.repository.ScrapeFieldRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScrapeFieldServiceImpl implements ScrapeFieldService {

    private final ScrapeFieldRepository scrapeFieldRepository;

    public ScrapeFieldServiceImpl(ScrapeFieldRepository scrapeFieldRepository) {
        this.scrapeFieldRepository = scrapeFieldRepository;
    }

    @Override
    public ScrapeField getByName(String name) {
        return scrapeFieldRepository.findByName(name);
    }

    @Override
    public Map<String, String> getByScrapeSite(ScrapeSite scrapeSite) {
        List<ScrapeField> scrapeFields = scrapeFieldRepository.findByScrapeSite_Id(scrapeSite.getId());
        if (scrapeFields.size() == 0) throw new ScrapeFieldNotFound();
        return scrapeFields.stream().collect(Collectors.toMap(ScrapeField::getName, ScrapeField::getSelector));
    }

    @Override
    public Set<ScrapeField> getAll() {
        return new HashSet<>(scrapeFieldRepository.findAll());
    }

    @Override
    public void update(ScrapeField scrapeField) {
        ScrapeField foundScrapeField = scrapeFieldRepository.findByName(scrapeField.getName());
        if (foundScrapeField == null) throw new ScrapeFieldNotFound(scrapeField);
        foundScrapeField.setSelector(scrapeField.getSelector());
        scrapeFieldRepository.save(foundScrapeField);
    }
}
