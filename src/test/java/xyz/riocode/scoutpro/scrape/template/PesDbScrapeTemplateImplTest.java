package xyz.riocode.scoutpro.scrape.template;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.riocode.scoutpro.enums.Foot;
import xyz.riocode.scoutpro.model.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PesDbScrapeTemplateImplTest {


    Player player;
    Document document;
    PesDbScrapeTemplateImpl pesDbScrapeTemplate;

    private Map<String, String> getScrapeFields() {
        Map<String, String> scrapeFields = new HashMap<>();
        scrapeFields.put("playerName", "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(1) td");
        scrapeFields.put("teamName", "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(3) td a");
        scrapeFields.put("teamNameFreePlayer", "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(2) td a");
        scrapeFields.put("foot", "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(10) td");
        scrapeFields.put("footFreePlayer", "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(9) td");
        scrapeFields.put("weekCondition", "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(11) td");
        scrapeFields.put("weekConditionFreePlayer", "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(10) td");
        scrapeFields.put("primaryPosition", "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(12) td div");
        scrapeFields.put("primaryPositionFreePlayer", "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(11) td div");
        scrapeFields.put("weakPositions", "table.player tbody table tr td.positions div span.pos1");
        scrapeFields.put("strongPositions", "table.player tbody table tr td.positions div span.pos2");
        scrapeFields.put("additionalData", "table.playing_styles tr");
        scrapeFields.put("offensiveAwareness", "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(1) td");
        scrapeFields.put("ballControl", "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(2) td");
        scrapeFields.put("dribbling", "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(3) td");
        scrapeFields.put("tightPossession", "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(4) td");
        scrapeFields.put("lowPass", "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(5) td");
        scrapeFields.put("loftedPass", "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(6) td");
        scrapeFields.put("finishing", "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(7) td");
        scrapeFields.put("heading", "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(8) td");
        scrapeFields.put("placeKicking", "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(9) td");
        scrapeFields.put("curl", "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(10) td");
        scrapeFields.put("speed", "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(11) td");
        scrapeFields.put("acceleration", "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(12) td");
        scrapeFields.put("kickingPower", "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(13) td");
        scrapeFields.put("jump", "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(14) td");
        scrapeFields.put("physicalContact", "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(15) td");
        scrapeFields.put("balance", "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(16) td");
        scrapeFields.put("stamina", "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(1) td");
        scrapeFields.put("defensiveAwareness", "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(2) td");
        scrapeFields.put("ballWinning", "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(3) td");
        scrapeFields.put("aggression", "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(4) td");
        scrapeFields.put("gkAwareness", "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(5) td");
        scrapeFields.put("gkCatching", "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(6) td");
        scrapeFields.put("gkClearing", "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(7) td");
        scrapeFields.put("gkReflexes", "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(8) td");
        scrapeFields.put("gkReach", "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(9) td");
        scrapeFields.put("weakFootUsage", "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(10) td");
        scrapeFields.put("weakFootAccuracy", "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(11) td");
        scrapeFields.put("form", "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(12) td");
        scrapeFields.put("injuryResistance", "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(13) td");
        scrapeFields.put("overallRating", "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(15) td");

        return scrapeFields;
    }

    @BeforeEach
    void setUp() throws IOException {
        File file = new File("src/test/resources/pesdb.html");
        player = new Player();
        pesDbScrapeTemplate = new PesDbScrapeTemplateImpl(getScrapeFields());
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