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

import static org.junit.jupiter.api.Assertions.assertEquals;

class PsmlScrapeTemplateImplTest {

    Player player;
    Document document;
    PsmlScrapeTemplateImpl psmlScrapeTemplate;
    PsmlPageSupplierImpl pageSupplier;

    @BeforeEach
    void setUp() throws IOException {
        File file = new File("src/test/resources/psml.html");
        player = new Player();
        pageSupplier = new PsmlPageSupplierImpl("cvelenidza", "deoreshimano", "PHPSESSID");
        psmlScrapeTemplate = new PsmlScrapeTemplateImpl(pageSupplier);
        document = Jsoup.parse(file, "UTF-8", "https://psml.rs");
    }

    @Test
    void scrapeCoreData() {
        psmlScrapeTemplate.scrapeCoreData(document, player);
        assertEquals("Hull City", player.getPsmlTeam());
        assertEquals(new BigDecimal(30000000), player.getPsmlValue());
    }
}