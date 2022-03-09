package xyz.riocode.scoutpro.scrape.template;

import xyz.riocode.scoutpro.model.Player;

public interface ScrapeTemplate {
    void scrape(String pageContent, Player player);
}
