package xyz.riocode.scoutpro.scrape.service;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.repository.PlayerRepository;
import xyz.riocode.scoutpro.scrape.engine.ScrapeEngine;
import xyz.riocode.scoutpro.scrape.enums.ScrapeSiteStatus;
import xyz.riocode.scoutpro.scrape.model.ScrapeSite;
import xyz.riocode.scoutpro.scrape.repository.ScrapeSiteRepository;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScrapeSiteServiceImpl implements ScrapeSiteService{

    private final ScrapeSiteRepository scrapeSiteRepository;
    private final PlayerRepository playerRepository;
    private final ScrapeEngine scrapeEngine;

    public ScrapeSiteServiceImpl(ScrapeSiteRepository scrapeSiteRepository, PlayerRepository playerRepository, ScrapeEngine scrapeEngine) {
        this.scrapeSiteRepository = scrapeSiteRepository;
        this.playerRepository = playerRepository;
        this.scrapeEngine = scrapeEngine;
    }

    @Override
    public List<ScrapeSite> getAll() {
        return scrapeSiteRepository.findAll();
    }

    @Override
    public ScrapeSite getById(Long id) {
        return scrapeSiteRepository.findById(id).orElseThrow();
    }

    @Override
    public ScrapeSite checkScrape(Long id) {
        int pageSize = 100;
        ScrapeSite foundedScrapeSite = scrapeSiteRepository.findById(id).orElseThrow();
        //todo - very very bad code!
        foundedScrapeSite.setStatus(ScrapeSiteStatus.SUCCESS);
        long playerCount = playerRepository.count();
        int totalPages = (int) (playerCount/pageSize);
        if (playerCount % pageSize > 0) totalPages++;
        int randomPage = RandomUtils.nextInt(0, totalPages);
        List<Player> players = playerRepository.findAll(PageRequest.of(randomPage, 10)).getContent();
        Player player = players.get(RandomUtils.nextInt(0, players.size()));
        List<URL> urlsToCheck;
        try {
            urlsToCheck = List.of(new URL(player.getTransfermarktUrl()),
                                            new URL(player.getPesDbUrl()),
                                            new URL(player.getPsmlUrl())
            );
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        for (URL url : urlsToCheck) {
            if (url.toString().matches(foundedScrapeSite.getUrlRegex())) {
                try {
                    scrapeEngine.work(url, player);
                    playerRepository.save(player);
                } catch (Exception ex) {
                    foundedScrapeSite.setStatus(ScrapeSiteStatus.FAILED);
                }
            }
        }
        foundedScrapeSite.setLastChecked(LocalDateTime.now());

        return scrapeSiteRepository.save(foundedScrapeSite);
    }

    @Override
    public List<ScrapeSite> getByLastCheckedBefore(LocalDateTime lastChecked) {
        return scrapeSiteRepository.findByLastCheckedBefore(lastChecked);
    }
}
