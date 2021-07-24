package xyz.riocode.scoutpro.scrape.template;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.riocode.scoutpro.model.Player;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TMCoreScrapeTemplateImplTest {

    Player player;
    Document document;
    TMCoreScrapeTemplateImpl tmCoreScrapeTemplate;

    @BeforeEach
    void setUp() throws IOException {
        File file = new File("src/test/resources/tm.html");
        player = new Player();
        tmCoreScrapeTemplate = new TMCoreScrapeTemplateImpl();
        document = Jsoup.parse(file, "UTF-8", "https://pesdb.net");
    }

    @Test
    void scrapeCoreData() {
        tmCoreScrapeTemplate.scrapeCoreData(document, player);
        assertEquals("Federico Chiesa", player.getPlayerName());
        assertEquals("Juventus FC", player.getClubTeam());
        assertEquals("Jun 30, 2022", player.getContractUntil());
        assertEquals("Italy", player.getNationality());
        assertEquals("attack - Right Winger", player.getTransfermarktPosition());
        assertEquals("Oct 25, 1997 ", player.getDateOfBirth());
        assertEquals(23, player.getAge());
        assertEquals("Italy", player.getNationalTeam());
    }
}