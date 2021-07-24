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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

class TMTransferScrapeTemplateImplTest {

    Player player;
    Document document;
    TMTransferScrapeTemplateImpl tmTransferScrapeTemplate;

    @BeforeEach
    void setUp() throws IOException {
        File file = new File("src/test/resources/tm.html");
        player = new Player();
        tmTransferScrapeTemplate = new TMTransferScrapeTemplateImpl();
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
        assertThat(player.getTransfers(), hasSize(3));
        assertThat(player.getTransfers(), hasItem(transfer));
    }
}