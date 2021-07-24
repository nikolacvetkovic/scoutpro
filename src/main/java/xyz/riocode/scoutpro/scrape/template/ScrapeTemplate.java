package xyz.riocode.scoutpro.scrape.template;

import org.jsoup.nodes.Document;
import xyz.riocode.scoutpro.model.Player;

public interface ScrapeTemplate {
    Player scrape(Player player);
    Player scrape(Player player, Document page);
}
