package xyz.riocode.scoutpro.scrape.template;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.riocode.scoutpro.enums.Foot;
import xyz.riocode.scoutpro.model.Player;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PesDbScrapeTemplateImplTest {


    Player player;
    Document document;
    PesDbScrapeTemplateImpl pesDbScrapeTemplate;

    @BeforeEach
    void setUp() throws IOException {
        File file = new File("src/test/resources/pesdb.html");
        player = new Player();
        pesDbScrapeTemplate = new PesDbScrapeTemplateImpl();
        document = Jsoup.parse(file, "UTF-8", "https://pesdb.net");
    }

    @Test
    void scrape() {
    }

    @Test
    void testScrape() {
    }

    @Test
    void scrapeCoreData() {
        pesDbScrapeTemplate.scrapeCoreData(document, player);
        assertEquals("F. CHIESA", player.getPesDbPlayerName());
        assertEquals("JUVENTUS", player.getPesDbTeamName());
        assertEquals(Foot.RIGHT, player.getFoot());
        assertEquals("RWF", player.getPrimaryPosition());
        assertEquals(3, player.getOtherStrongPositions().size());
        assertEquals(3, player.getOtherWeakPositions().size());
    }

    @Test
    void scrapeRatings() {
        pesDbScrapeTemplate.scrapeRatings(document, player);
        assertEquals(78, player.getOffensiveAwareness());
        assertEquals(83, player.getBallControl());
        assertEquals(62, player.getHeading());
        assertEquals(86, player.getAcceleration());
        assertEquals(76, player.getAggression());
        assertEquals(4, player.getWeakFootUsage());
        assertEquals(83, player.getOverallRating());
    }

    @Test
    void scrapeAdditionalData() {
        pesDbScrapeTemplate.scrapeAdditionalData(document, player);
        assertEquals("Prolific Winger", player.getPlayingStyle());

        assertEquals(5, player.getPlayerSkills().size());
        assertThat(player.getPlayerSkills(), hasItem("Long Range Shooting"));

        assertEquals(3, player.getComPlayingStyles().size());
        assertThat(player.getComPlayingStyles(), hasItem("Incisive Run"));

    }
}