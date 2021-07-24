package xyz.riocode.scoutpro.scrape.template;

import org.jsoup.nodes.Document;
import xyz.riocode.scoutpro.scrape.helper.ScrapeHelper;
import xyz.riocode.scoutpro.scrape.page.PageSupplier;

public abstract class SimpleAbstractScrapeTemplate implements ScrapeTemplate, PageSupplier {

    public Document getPage(String url){
        return ScrapeHelper.getPage(url);
    }
}
