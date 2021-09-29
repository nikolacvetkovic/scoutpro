package xyz.riocode.scoutpro.scrape.template;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.riocode.scoutpro.model.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TMCoreScrapeTemplateImplTest {

    Player player;
    Document document;
    TMCoreScrapeTemplateImpl tmCoreScrapeTemplate;

    private Map<String, String> getScrapeFields() {
        Map<String, String> scrapeFields = new HashMap<>();
        scrapeFields.put("playerName", "h1[itemprop=name]");
        scrapeFields.put("clubTeam", "div.info-table span:contains(Current club)+span a:nth-of-type(2)");
        scrapeFields.put("contractUntil", "div.info-table span:contains(Contract expires)+span");
        scrapeFields.put("nationality", "span[itemprop=nationality]");
        scrapeFields.put("position", "div.info-table span:contains(Position)+span");
        scrapeFields.put("birthDate", "span[itemprop=birthDate]");
        scrapeFields.put("age", "div.info-table span:matches(Age:)+span");
        scrapeFields.put("nationalTeam", "div.info-table span:contains(Citizenship)+span img");
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
        tmCoreScrapeTemplate = new TMCoreScrapeTemplateImpl(getScrapeFields());
        document = Jsoup.parse(file, "UTF-8", "https://pesdb.net");
    }

    @Test
    void scrapeCoreData() {
        tmCoreScrapeTemplate.scrapeCoreData(document, player);
        assertEquals("Federico Chiesa", player.getName());
        assertEquals("Juventus FC", player.getClubTeam());
        assertEquals("Jun 30, 2022", player.getContractUntil());
        assertEquals("Italy", player.getNationality());
        assertEquals("attack - Right Winger", player.getTransfermarktPosition());
        assertEquals("Oct 25, 1997 ", player.getDateOfBirth());
        assertEquals(23, player.getAge());
        assertEquals("Italy", player.getNationalTeam());
    }
}