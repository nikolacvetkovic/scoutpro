package xyz.riocode.scoutpro.scrape.template;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.riocode.scoutpro.model.MarketValue;
import xyz.riocode.scoutpro.model.Player;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

class TMMarketValueScrapeTemplateImplTest {

    Player player;
    Document document;
    TMMarketValueScrapeTemplateImpl tmMarketValueScrapeTemplate;

    @BeforeEach
    void setUp() throws IOException {
        File file = new File("src/test/resources/tm.html");
        player = new Player();
        tmMarketValueScrapeTemplate = new TMMarketValueScrapeTemplateImpl();
        document = Jsoup.parse(file, "UTF-8", "https://pesdb.net");
    }

    @Test
    void scrapeMarketValues() {
        MarketValue marketValue = new MarketValue();
        marketValue.setWorth(new BigDecimal(45000000));
        marketValue.setClubTeam("ACF Fiorentina");
        marketValue.setDatePoint(LocalDate.of(2018, 6, 7));
        tmMarketValueScrapeTemplate.scrapeMarketValues(document, player);
        assertThat(player.getMarketValues(), hasSize(21));
        assertThat(player.getMarketValues(), hasItem(marketValue));
    }
}