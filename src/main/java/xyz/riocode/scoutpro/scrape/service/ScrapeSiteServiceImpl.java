package xyz.riocode.scoutpro.scrape.service;

import org.springframework.stereotype.Service;
import xyz.riocode.scoutpro.scrape.model.ScrapeSite;
import xyz.riocode.scoutpro.scrape.repository.ScrapeSiteRepository;

import java.util.List;

@Service
public class ScrapeSiteServiceImpl implements ScrapeSiteService{

    private final ScrapeSiteRepository scrapeSiteRepository;

    public ScrapeSiteServiceImpl(ScrapeSiteRepository scrapeSiteRepository) {
        this.scrapeSiteRepository = scrapeSiteRepository;
    }

    @Override
    public List<ScrapeSite> getAll() {
        return scrapeSiteRepository.findAll();
    }
}
