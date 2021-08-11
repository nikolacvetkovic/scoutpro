package xyz.riocode.scoutpro.scrape.template;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.model.Transfer;
import xyz.riocode.scoutpro.scrape.helper.ScrapeHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

public class TMTransferScrapeTemplateImpl extends SimpleAbstractScrapeTemplate {

    public TMTransferScrapeTemplateImpl(Map<String, String> scrapeFields) {
        super(scrapeFields);
    }

    @Override
    public Player scrape(Player player) {
        Document page = getPage(player.getTransfermarktUrl());
        return scrape(player, page);
    }

    @Override
    public Player scrape(Player player, Document page) {
        return scrapeTransfers(page, player);
    }


    protected Player scrapeTransfers(Document doc, Player player){
        Elements elements = ScrapeHelper.getElements(doc, scrapeFields.get("transferTable"));
        for(Element e : elements){
            Transfer transfer = new Transfer();
            String dateString = ScrapeHelper.getElementData(e, scrapeFields.get("dateOfTransfer"));
            LocalDate dateOfTransfer = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("MMM d, yyyy").withLocale(Locale.US));
            transfer.setDateOfTransfer(dateOfTransfer);
            String fromTeam = ScrapeHelper.getElementData(e, scrapeFields.get("fromTeam"));
            transfer.setFromTeam(fromTeam);
            String toTeam = ScrapeHelper.getElementData(e, scrapeFields.get("toTeam"));
            transfer.setToTeam(toTeam);
            String marketValue = ScrapeHelper.getElementData(e, scrapeFields.get("marketValue"));
            transfer.setMarketValue(marketValue);
            String transferFee = ScrapeHelper.getElementData(e, scrapeFields.get("transferFee"));
            transfer.setTransferFee(transferFee);
            transfer.setPlayer(player);
            player.getTransfers().add(transfer);
        }
        player.setTransferLastCheck(LocalDateTime.now());
        return player;
    }

}
