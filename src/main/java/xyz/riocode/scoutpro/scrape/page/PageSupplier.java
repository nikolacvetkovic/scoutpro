package xyz.riocode.scoutpro.scrape.page;

import org.jsoup.nodes.Document;

public interface PageSupplier {
    Document getPage(String url);
}
