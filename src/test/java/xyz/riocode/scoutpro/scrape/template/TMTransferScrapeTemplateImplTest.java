package xyz.riocode.scoutpro.scrape.template;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.model.Transfer;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

class TMTransferScrapeTemplateImplTest {

    Player player;
    Document document;
    TMTransferScrapeTemplateImpl tmTransferScrapeTemplate;

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
        tmTransferScrapeTemplate = new TMTransferScrapeTemplateImpl(getScrapeFields());
        document = Jsoup.parse(file, "UTF-8", "https://pesdb.net");
    }

    @Test
    void scrapeTransfers() {
        Transfer transfer = new Transfer();
        transfer.setFromTeam("Fiorentina");
        transfer.setToTeam("Juventus");
        transfer.setTransferFee("Loan fee: €10.00m");
        transfer.setDateOfTransfer(LocalDate.of(2020, 10, 5));
        transfer.setMarketValue("€48.00m");
        tmTransferScrapeTemplate.scrapeTransfers(document, player);
        assertThat(player.getTransfers(), hasSize(4));
        assertThat(player.getTransfers(), hasItem(transfer));
    }
}