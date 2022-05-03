package xyz.riocode.scoutpro.scrape.template;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xyz.riocode.scoutpro.model.MarketValue;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.model.Transfer;
import xyz.riocode.scoutpro.scrape.model.ScrapeField;
import xyz.riocode.scoutpro.scrape.repository.ScrapeFieldRepository;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class TMCoreScrapeTemplateImplTest {

    Player player;
    Document document;
    TMCoreScrapeTemplateImpl tmScrapeTemplate;
    List<ScrapeField> scrapeFields;
    MarketValue marketValue;
    Transfer transfer;

    @Mock
    ScrapeFieldRepository scrapeFieldRepository;

    private Map<String, String> getScrapeFieldsMap() {
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
        scrapeFields.put("fromTeam_1st", "td:nth-of-type(5) a");
        scrapeFields.put("fromTeam_2nd", "td:nth-of-type(5)");
        scrapeFields.put("toTeam_1st", "td:nth-of-type(8) a");
        scrapeFields.put("toTeam_2nd", "td:nth-of-type(8)");
        scrapeFields.put("marketValue", "td.zelle-mw");
        scrapeFields.put("transferFee", "td.zelle-abloese");
        return scrapeFields;
    }

    private List<ScrapeField> getScrapeFieldsList() {
        List<ScrapeField> scrapeFields = new ArrayList<>();
        scrapeFields.add(ScrapeField.builder().name("playerName").selector("h1[itemprop=name]").build());
        scrapeFields.add(ScrapeField.builder().name("clubTeam").selector("div.info-table span:contains(Current club)+span a:nth-of-type(2)").build());
        scrapeFields.add(ScrapeField.builder().name("contractUntil").selector("div.info-table span:contains(Contract expires)+span").build());
        scrapeFields.add(ScrapeField.builder().name("nationality").selector("span[itemprop=nationality]").build());
        scrapeFields.add(ScrapeField.builder().name("position").selector("div.info-table span:contains(Position)+span").build());
        scrapeFields.add(ScrapeField.builder().name("birthDate").selector("span[itemprop=birthDate]").build());
        scrapeFields.add(ScrapeField.builder().name("age").selector("div.info-table span:matches(Age:)+span").build());
        scrapeFields.add(ScrapeField.builder().name("nationalTeam").selector("div.info-table span:contains(Citizenship)+span img").build());
        scrapeFields.add(ScrapeField.builder().name("transferTable").selector("div.responsive-table tr.zeile-transfer").build());
        scrapeFields.add(ScrapeField.builder().name("dateOfTransfer").selector("td:nth-of-type(2)").build());
        scrapeFields.add(ScrapeField.builder().name("fromTeam_1st").selector("td:nth-of-type(5) a").build());
        scrapeFields.add(ScrapeField.builder().name("fromTeam_2nd").selector("td:nth-of-type(5)").build());
        scrapeFields.add(ScrapeField.builder().name("toTeam_1st").selector("td:nth-of-type(8) a").build());
        scrapeFields.add(ScrapeField.builder().name("toTeam_2nd").selector("td:nth-of-type(8)").build());
        scrapeFields.add(ScrapeField.builder().name("marketValue").selector("td.zelle-mw").build());
        scrapeFields.add(ScrapeField.builder().name("transferFee").selector("td.zelle-abloese").build());
        return scrapeFields;
    }

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        File file = new File("src/test/resources/tm.html");
        scrapeFields = getScrapeFieldsList();
        player = new Player();

        marketValue = new MarketValue();
        marketValue.setWorth(new BigDecimal(45000000));
        marketValue.setClubTeam("ACF Fiorentina");
        marketValue.setDatePoint(LocalDate.of(2018, 6, 7));

        transfer = new Transfer();
        transfer.setFromTeam("Fiorentina");
        transfer.setToTeam("Juventus");
        transfer.setTransferFee("Loan fee: €10.00m");
        transfer.setDateOfTransfer(LocalDate.of(2020, 10, 5));
        transfer.setMarketValue("€48.00m");

        document = Jsoup.parse(file, "UTF-8", "https://pesdb.net");
        tmScrapeTemplate = new TMCoreScrapeTemplateImpl(scrapeFieldRepository);
    }

    @Test
    void scrape() {
        when(scrapeFieldRepository.findByScrapeSite_Name(anyString())).thenReturn(scrapeFields);

        tmScrapeTemplate.scrape(document.toString(), player);

        assertEquals("Federico Chiesa", player.getName());
        assertEquals("Juventus FC", player.getClubTeam());
        assertEquals("Jun 30, 2022", player.getContractUntil());
        assertEquals("Italy", player.getNationality());
        assertEquals("attack - Right Winger", player.getTransfermarktPosition());
        assertEquals("Oct 25, 1997 ", player.getDateOfBirth());
        assertEquals(23, player.getAge());
        assertEquals("Italy", player.getNationalTeam());
        assertThat(player.getMarketValues(), hasSize(21));
        assertThat(player.getMarketValues(), hasItem(marketValue));
        assertThat(player.getTransfers(), hasSize(4));
        assertThat(player.getTransfers(), hasItem(transfer));
    }

    @Test
    void scrapeCoreData() {
        tmScrapeTemplate.scrapeCoreData(document, player, getScrapeFieldsMap());

        assertEquals("Federico Chiesa", player.getName());
        assertEquals("Juventus FC", player.getClubTeam());
        assertEquals("Jun 30, 2022", player.getContractUntil());
        assertEquals("Italy", player.getNationality());
        assertEquals("attack - Right Winger", player.getTransfermarktPosition());
        assertEquals("Oct 25, 1997 ", player.getDateOfBirth());
        assertEquals(23, player.getAge());
        assertEquals("Italy", player.getNationalTeam());
    }

    @Test
    void scrapeMarketValues() {
        tmScrapeTemplate.scrapeMarketValues(document, player, getScrapeFieldsMap());

        assertThat(player.getMarketValues(), hasSize(21));
        assertThat(player.getMarketValues(), hasItem(marketValue));
    }

    @Test
    void scrapeTransfers() {
        tmScrapeTemplate.scrapeTransfers(document, player, getScrapeFieldsMap());

        assertThat(player.getTransfers(), hasSize(4));
        assertThat(player.getTransfers(), hasItem(transfer));
    }

    @Test
    void scrapeTransfers_2() throws IOException {
        File file = new File("src/test/resources/tm_2.html");
        document = Jsoup.parse(file, "UTF-8", "https://pesdb.net");

        tmScrapeTemplate.scrapeTransfers(document, player, getScrapeFieldsMap());

        Transfer transfer = new Transfer();
        transfer.setFromTeam("Man City");
        transfer.setToTeam("PSV Eindhoven");
        transfer.setTransferFee("loan transfer");
        transfer.setDateOfTransfer(LocalDate.of(2016, 8, 26));
        transfer.setMarketValue("€4.00m");

        assertThat(player.getTransfers(), hasSize(6));
        assertThat(player.getTransfers(), hasItem(transfer));
    }
}