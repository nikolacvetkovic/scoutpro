package xyz.riocode.scoutpro.scrape.template;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.scrape.helper.ScrapeHelper;
import xyz.riocode.scoutpro.scrape.page.PageSupplier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public class PsmlScrapeTemplateImpl extends WebDriverAbstractScrapeTemplate {

    private final PageSupplier pageSupplier;

    public PsmlScrapeTemplateImpl(Map<String, String> scrapeFields, PageSupplier pageSupplier){
        super(scrapeFields);
        this.pageSupplier = pageSupplier;
    }

    @Override
    public Player scrape(Player player){
        Document page = getPage(player.getPsmlUrl());
        return scrape(player, page);
    }

    @Override
    public Player scrape(Player player, Document page) {
        scrapeCoreData(page, player);
        return player;
    }

    protected void scrapeCoreData(Document doc, Player player){
        String teamName = ScrapeHelper.getElementData(doc, scrapeFields.get("teamName"));
        player.setPsmlTeam(teamName != null?teamName:"Free");
        String teamValue = ScrapeHelper.getElementDataOwn(doc, scrapeFields.get("teamValue"));
        if(teamValue != null){
            teamValue = teamValue.replaceAll("[^0-9,]", "").replace(",", "");
        }
        player.setPsmlValue(NumberUtils.isCreatable(teamValue)?new BigDecimal(teamValue):BigDecimal.ZERO);
        player.setPsmlLastCheck(LocalDateTime.now());
    }

    @Override
    public Document getPage(String url) {
        return pageSupplier.getPage(url);
    }
}