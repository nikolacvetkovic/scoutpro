package xyz.riocode.scoutpro.scrape.template;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.scrape.model.ScrapeField;
import xyz.riocode.scoutpro.scrape.repository.ScrapeFieldRepository;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

class PsmlScrapeTemplateImplTest {

    Player player;
    Document document;
    PsmlScrapeTemplateImpl psmlScrapeTemplate;
    List<ScrapeField> scrapeFields;

    @Mock
    ScrapeFieldRepository scrapeFieldRepository;

    private Map<String, String> getScrapeFieldsMap() {
        Map<String, String> scrapeFields = new HashMap<>();
        scrapeFields.put("teamName", "table.innerTable tbody tr:nth-of-type(2) td:nth-of-type(2) p:nth-of-type(2) a");
        scrapeFields.put("teamValue", "table.innerTable tbody tr:nth-of-type(2) td:nth-of-type(3) p:nth-of-type(1)");
        return scrapeFields;
    }

    private List<ScrapeField> getScrapeFieldsList() {
        List<ScrapeField> scrapeFields = new ArrayList<>();
        scrapeFields.add(ScrapeField.builder().name("teamName").selector("table.innerTable tbody tr:nth-of-type(2) td:nth-of-type(2) p:nth-of-type(2) a").build());
        scrapeFields.add(ScrapeField.builder().name("teamValue").selector("table.innerTable tbody tr:nth-of-type(2) td:nth-of-type(3) p:nth-of-type(1)").build());
        return scrapeFields;
    }

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        File file = new File("src/test/resources/psml.html");
        scrapeFields = getScrapeFieldsList();
        player = new Player();
        document = Jsoup.parse(file, "UTF-8", "https://psml.rs");
        psmlScrapeTemplate = new PsmlScrapeTemplateImpl(scrapeFieldRepository);
    }

    @Test
    void scrape() {
        Mockito.when(scrapeFieldRepository.findByScrapeSite_Name(anyString())).thenReturn(scrapeFields);

        psmlScrapeTemplate.scrape(document.toString(), player);

        assertEquals("Hull City", player.getPsmlTeam());
        assertEquals(new BigDecimal(30000000), player.getPsmlValue());
    }

    @Test
    void scrapeCoreData() {
        psmlScrapeTemplate.scrapeCoreData(document, player, getScrapeFieldsMap());
        assertEquals("Hull City", player.getPsmlTeam());
        assertEquals(new BigDecimal(30000000), player.getPsmlValue());
    }
}