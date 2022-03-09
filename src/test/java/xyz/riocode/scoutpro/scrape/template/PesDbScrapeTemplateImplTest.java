package xyz.riocode.scoutpro.scrape.template;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xyz.riocode.scoutpro.enums.Foot;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.scrape.model.ScrapeField;
import xyz.riocode.scoutpro.scrape.repository.ScrapeFieldRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class PesDbScrapeTemplateImplTest {

    Player player;
    Document document;
    PesDbScrapeTemplateImpl pesDbScrapeTemplate;
    List<ScrapeField> scrapeFields;

    @Mock
    ScrapeFieldRepository scrapeFieldRepository;

    private Map<String, String> getScrapeFieldsMap() {
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

    private List<ScrapeField> getScrapeFieldsList() {
        List<ScrapeField> scrapeFields = new ArrayList<>();

        scrapeFields.add(ScrapeField.builder().name("playerName").selector("table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(1) td").build());
        scrapeFields.add(ScrapeField.builder().name("teamName").selector("table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(3) td a").build());
        scrapeFields.add(ScrapeField.builder().name("teamNameFreePlayer").selector("table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(2) td a").build());
        scrapeFields.add(ScrapeField.builder().name("foot").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(10) td").build());
        scrapeFields.add(ScrapeField.builder().name("footFreePlayer").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(9) td").build());
        scrapeFields.add(ScrapeField.builder().name("weekCondition").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(11) td").build());
        scrapeFields.add(ScrapeField.builder().name("weekConditionFreePlayer").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(10) td").build());
        scrapeFields.add(ScrapeField.builder().name("primaryPosition").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(12) td div").build());
        scrapeFields.add(ScrapeField.builder().name("primaryPositionFreePlayer").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(11) td div").build());
        scrapeFields.add(ScrapeField.builder().name("weakPositions").selector( "table.player tbody table tr td.positions div span.pos1").build());
        scrapeFields.add(ScrapeField.builder().name("strongPositions").selector( "table.player tbody table tr td.positions div span.pos2").build());
        scrapeFields.add(ScrapeField.builder().name("additionalData").selector( "table.playing_styles tr").build());
        scrapeFields.add(ScrapeField.builder().name("offensiveAwareness").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(1) td").build());
        scrapeFields.add(ScrapeField.builder().name("ballControl").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(2) td").build());
        scrapeFields.add(ScrapeField.builder().name("dribbling").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(3) td").build());
        scrapeFields.add(ScrapeField.builder().name("tightPossession").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(4) td").build());
        scrapeFields.add(ScrapeField.builder().name("lowPass").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(5) td").build());
        scrapeFields.add(ScrapeField.builder().name("loftedPass").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(6) td").build());
        scrapeFields.add(ScrapeField.builder().name("finishing").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(7) td").build());
        scrapeFields.add(ScrapeField.builder().name("heading").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(8) td").build());
        scrapeFields.add(ScrapeField.builder().name("placeKicking").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(9) td").build());
        scrapeFields.add(ScrapeField.builder().name("curl").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(10) td").build());
        scrapeFields.add(ScrapeField.builder().name("speed").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(11) td").build());
        scrapeFields.add(ScrapeField.builder().name("acceleration").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(12) td").build());
        scrapeFields.add(ScrapeField.builder().name("kickingPower").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(13) td").build());
        scrapeFields.add(ScrapeField.builder().name("jump").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(14) td").build());
        scrapeFields.add(ScrapeField.builder().name("physicalContact").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(15) td").build());
        scrapeFields.add(ScrapeField.builder().name("balance").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(16) td").build());
        scrapeFields.add(ScrapeField.builder().name("stamina").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(1) td").build());
        scrapeFields.add(ScrapeField.builder().name("defensiveAwareness").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(2) td").build());
        scrapeFields.add(ScrapeField.builder().name("ballWinning").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(3) td").build());
        scrapeFields.add(ScrapeField.builder().name("aggression").selector("table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(4) td").build());
        scrapeFields.add(ScrapeField.builder().name("gkAwareness").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(5) td").build());
        scrapeFields.add(ScrapeField.builder().name("gkCatching").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(6) td").build());
        scrapeFields.add(ScrapeField.builder().name("gkClearing").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(7) td").build());
        scrapeFields.add(ScrapeField.builder().name("gkReflexes").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(8) td").build());
        scrapeFields.add(ScrapeField.builder().name("gkReach").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(9) td").build());
        scrapeFields.add(ScrapeField.builder().name("weakFootUsage").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(10) td").build());
        scrapeFields.add(ScrapeField.builder().name("weakFootAccuracy").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(11) td").build());
        scrapeFields.add(ScrapeField.builder().name("form").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(12) td").build());
        scrapeFields.add(ScrapeField.builder().name("injuryResistance").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(13) td").build());
        scrapeFields.add(ScrapeField.builder().name("overallRating").selector( "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(15) td").build());

        return scrapeFields;
    }

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        File file = new File("src/test/resources/pesdb.html");
        scrapeFields = getScrapeFieldsList();
        player = new Player();
        document = Jsoup.parse(file, "UTF-8", "https://pesdb.net");
        pesDbScrapeTemplate = new PesDbScrapeTemplateImpl(scrapeFieldRepository);
    }

    @Test
    void scrape() {
        when(scrapeFieldRepository.findByScrapeSite_Name(anyString())).thenReturn(scrapeFields);

        pesDbScrapeTemplate.scrape(document.toString(), player);

        assertEquals("F. CHIESA", player.getPesDbPlayerName());
        assertEquals("JUVENTUS", player.getPesDbTeamName());
        assertEquals(4, player.getWeakFootUsage());
        assertEquals(83, player.getOverallRating());
        assertEquals(3, player.getComPlayingStyles().size());
        assertThat(player.getComPlayingStyles(), hasItem("Incisive Run"));
    }

    @Test
    void scrapeCoreData() {
        pesDbScrapeTemplate.scrapeCoreData(document, player, getScrapeFieldsMap());
        assertEquals("F. CHIESA", player.getPesDbPlayerName());
        assertEquals("JUVENTUS", player.getPesDbTeamName());
        assertEquals(Foot.RIGHT, player.getFoot());
        assertEquals("RWF", player.getPrimaryPosition());
        assertEquals(3, player.getOtherStrongPositions().size());
        assertEquals(3, player.getOtherWeakPositions().size());
    }

    @Test
    void scrapeRatings() {
        pesDbScrapeTemplate.scrapeRatings(document, player, getScrapeFieldsMap());
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
        pesDbScrapeTemplate.scrapeAdditionalData(document, player, getScrapeFieldsMap());
        assertEquals("Prolific Winger", player.getPlayingStyle());

        assertEquals(5, player.getPlayerSkills().size());
        assertThat(player.getPlayerSkills(), hasItem("Long Range Shooting"));

        assertEquals(3, player.getComPlayingStyles().size());
        assertThat(player.getComPlayingStyles(), hasItem("Incisive Run"));

    }
}