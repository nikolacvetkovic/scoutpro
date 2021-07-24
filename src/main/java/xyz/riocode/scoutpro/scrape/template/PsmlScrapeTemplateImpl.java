package xyz.riocode.scoutpro.scrape.template;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.scrape.helper.ScrapeHelper;
import xyz.riocode.scoutpro.scrape.page.PageSupplier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PsmlScrapeTemplateImpl extends WebDriverAbstractScrapeTemplate {

    private final PageSupplier pageSupplier;

    public PsmlScrapeTemplateImpl(PageSupplier pageSupplier){
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
        String teamName = ScrapeHelper.getElementData(doc, "table.innerTable tbody tr:nth-of-type(2) td:nth-of-type(2) p:nth-of-type(2) a");
        player.setPsmlTeam(teamName != null?teamName:"Free");
        String teamValue = ScrapeHelper.getElementDataOwn(doc, "table.innerTable tbody tr:nth-of-type(2) td:nth-of-type(3) p:nth-of-type(1)");
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