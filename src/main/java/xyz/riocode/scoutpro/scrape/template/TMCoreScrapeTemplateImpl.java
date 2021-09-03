package xyz.riocode.scoutpro.scrape.template;

import org.jsoup.nodes.Document;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.scrape.helper.ScrapeHelper;

import java.util.Map;

public class TMCoreScrapeTemplateImpl extends SimpleAbstractScrapeTemplate {

    public TMCoreScrapeTemplateImpl(Map<String, String> scrapeFields) {
        super(scrapeFields);
    }

    @Override
    public Player scrape(Player player) {
        Document page = getPage(player.getTransfermarktUrl());
        return scrape(player, page);
    }

    @Override
    public Player scrape(Player player, Document page) {
        scrapeCoreData(page, player);
        return player;
    }

    protected void scrapeCoreData(Document doc, Player player){
        String playerName = ScrapeHelper.getElementData(doc, scrapeFields.get("playerName"));
        player.setName(playerName);
        String clubTeam = ScrapeHelper.getElementData(doc, scrapeFields.get("clubTeam"));
        player.setClubTeam(clubTeam);
        String contractUntil = ScrapeHelper.getElementData(doc, scrapeFields.get("contractUntil"));
        player.setContractUntil(contractUntil);
        String nationality = ScrapeHelper.getElementData(doc, scrapeFields.get("nationality"));
        player.setNationality(nationality);
        String position = ScrapeHelper.getElementData(doc, scrapeFields.get("position"));
        player.setTransfermarktPosition(position);
        String birthDate = ScrapeHelper.getElementData(doc, scrapeFields.get("birthDate"));
        birthDate = birthDate.substring(0, birthDate.indexOf("("));
        player.setDateOfBirth(birthDate);
        int age = Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("age")));
        player.setAge(age);
        String nationalTeam = ScrapeHelper.getElementData(doc, scrapeFields.get("nationalTeam"));
        player.setNationalTeam(nationalTeam);
    }
}
