package xyz.riocode.scoutpro.scrape.engine;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.scrape.loader.PageLoader;
import xyz.riocode.scoutpro.scrape.model.ScrapeSite;
import xyz.riocode.scoutpro.scrape.repository.ScrapeSiteRepository;
import xyz.riocode.scoutpro.scrape.template.ScrapeTemplate;

import java.net.URL;
import java.util.List;

@Component
public class ScrapeEngine {

    private final BeanFactory beanFactory;
    private final ScrapeLoader scrapeLoader;
    private final ScrapeSiteRepository scrapeSiteRepository;

    public ScrapeEngine(BeanFactory beanFactory, ScrapeLoader scrapeLoader, ScrapeSiteRepository scrapeSiteRepository) {
        this.beanFactory = beanFactory;
        this.scrapeLoader = scrapeLoader;
        this.scrapeSiteRepository = scrapeSiteRepository;
    }

    public Player work(URL scrapeUrl, Player player) {
        //resolve what loader and template to use
        List<ScrapeSite> scrapeSites = scrapeSiteRepository.findAll();
        ScrapeSite foundedScrapeSite = scrapeSites.stream()
                .filter(scrapeSite -> scrapeUrl.toString().matches(scrapeSite.getUrlRegex()))
                .findFirst()
                .orElseThrow();
        String loaderName = foundedScrapeSite.getLoaderName();
        String templateName = foundedScrapeSite.getTemplateName();
        PageLoader pageLoader = beanFactory.getBean(loaderName, PageLoader.class);
        ScrapeTemplate template = beanFactory.getBean(templateName, ScrapeTemplate.class);

        //load page and get content
        String pageContent = scrapeLoader.loadAndGetPageContent(scrapeUrl, pageLoader);

        //scrape
        template.scrape(pageContent, player);

        return player;
    }
}