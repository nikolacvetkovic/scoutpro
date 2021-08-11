package xyz.riocode.scoutpro.scrape.template;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.scrape.page.PsmlPageSupplierImpl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PsmlScrapeTemplateImplTest {

    Player player;
    Document document;
    PsmlScrapeTemplateImpl psmlScrapeTemplate;
    PsmlPageSupplierImpl pageSupplier;

    private Map<String, String> getScrapeFields() {
        Map<String, String> scrapeFields = new HashMap<>();
        scrapeFields.put("teamName", "table.innerTable tbody tr:nth-of-type(2) td:nth-of-type(2) p:nth-of-type(2) a");
        scrapeFields.put("teamValue", "table.innerTable tbody tr:nth-of-type(2) td:nth-of-type(3) p:nth-of-type(1)");

        return scrapeFields;
    }

    @BeforeEach
    void setUp() throws IOException {
        File file = new File("src/test/resources/psml.html");
        player = new Player();
        pageSupplier = new PsmlPageSupplierImpl("cvelenidza", "deoreshimano", "PHPSESSID");
        psmlScrapeTemplate = new PsmlScrapeTemplateImpl(getScrapeFields(), pageSupplier);
        document = Jsoup.parse(file, "UTF-8", "https://psml.rs");
    }

    @Test
    void scrapeCoreData() {
        psmlScrapeTemplate.scrapeCoreData(document, player);
        assertEquals("Hull City", player.getPsmlTeam());
        assertEquals(new BigDecimal(30000000), player.getPsmlValue());
    }
}