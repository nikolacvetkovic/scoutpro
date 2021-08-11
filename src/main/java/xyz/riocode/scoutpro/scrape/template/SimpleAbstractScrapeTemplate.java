package xyz.riocode.scoutpro.scrape.template;

import org.jsoup.nodes.Document;
import xyz.riocode.scoutpro.scrape.helper.ScrapeHelper;
import xyz.riocode.scoutpro.scrape.page.PageSupplier;

import java.util.Map;

public abstract class SimpleAbstractScrapeTemplate implements ScrapeTemplate, PageSupplier {

    protected final Map<String, String> scrapeFields;

    protected SimpleAbstractScrapeTemplate(Map<String, String> scrapeFields) {
        this.scrapeFields = scrapeFields;
    }

    public Document getPage(String url){
        return ScrapeHelper.getPage(url);
    }
}
