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
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

class TMMarketValueScrapeTemplateImplTest {

    Player player;
    Document document;
    TMMarketValueScrapeTemplateImpl tmMarketValueScrapeTemplate;

    private Map<String, String> getScrapeFields() {
        Map<String, String> scrapeFields = new HashMap<>();
        scrapeFields.put("playerName", "h1[itemprop=name]");
        scrapeFields.put("clubTeam", "table.auflistung tr:has(th:contains(Current club)) td a:nth-of-type(2)");
        scrapeFields.put("contractUntil", "table.auflistung tr:has(th:contains(Contract expires)) td");
        scrapeFields.put("nationality", "span[itemprop=nationality]");
        scrapeFields.put("position", "table.auflistung tr:has(th:contains(Position)) td");
        scrapeFields.put("birthDate", "span[itemprop=birthDate]");
        scrapeFields.put("age", "table.auflistung tr:has(th:contains(Age)) td");
        scrapeFields.put("nationalTeam", "div.dataContent div.dataDaten:nth-of-type(3) p:nth-of-type(1) span.dataValue");
        scrapeFields.put("transferTable", "div.responsive-table tr.zeile-transfer");
        scrapeFields.put("dateOfTransfer", "td:nth-of-type(2)");
        scrapeFields.put("fromTeam", "td:nth-of-type(5) a");
        scrapeFields.put("toTeam", "td:nth-of-type(8) a");
        scrapeFields.put("marketValue", "td.zelle-mw");
        scrapeFields.put("transferFee", "td.zelle-abloese");

        return scrapeFields;
    }

    @BeforeEach
    void setUp() throws IOException {
        File file = new File("src/test/resources/tm.html");
        player = new Player();
        tmMarketValueScrapeTemplate = new TMMarketValueScrapeTemplateImpl(getScrapeFields());
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