package xyz.riocode.scoutpro.scrape.template.async;

import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.scrape.helper.ScrapeHelper;
import xyz.riocode.scoutpro.scrape.page.PageSupplier;
import xyz.riocode.scoutpro.scrape.template.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Async
@Component
public class ScrapeAsyncWrapper {

    public CompletableFuture<Player> tmAllScrape(Player player, Map<String, String> scrapeFields){

        Document page = ScrapeHelper.getPage(player.getTransfermarktUrl());

        CompletableFuture<Player> tmCore = tmCoreScrape(player, page, scrapeFields);
        CompletableFuture<Player> tmMarketValues = tmMarketValueScrape(player, page, scrapeFields);
        CompletableFuture<Player> tmTransfers = tmTransferScrape(player, page, scrapeFields);

        CompletableFuture.allOf(tmCore, tmMarketValues, tmTransfers).join();
        Player p = null;

        try {
            p = tmCore.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return CompletableFuture.completedFuture(p);
    }

    public CompletableFuture<Player> tmCoreScrape(Player player, Map<String, String> scrapeFields){
        TMCoreScrapeTemplateImpl tmCoreScrapeTemplate = new TMCoreScrapeTemplateImpl(scrapeFields);
        tmCoreScrapeTemplate.scrape(player);
        return CompletableFuture.completedFuture(player);
    }

    private CompletableFuture<Player> tmCoreScrape(Player player, Document page, Map<String, String> scrapeFields){
        TMCoreScrapeTemplateImpl tmCoreScrapeTemplate = new TMCoreScrapeTemplateImpl(scrapeFields);
        tmCoreScrapeTemplate.scrape(player, page);
        return CompletableFuture.completedFuture(player);
    }

    public CompletableFuture<Player> tmMarketValueScrape(Player player, Map<String, String> scrapeFields){
        TMMarketValueScrapeTemplateImpl tmMarketValueScrapeTemplate = new TMMarketValueScrapeTemplateImpl(scrapeFields);
        tmMarketValueScrapeTemplate.scrape(player);
        return CompletableFuture.completedFuture(player);
    }

    private CompletableFuture<Player> tmMarketValueScrape(Player player, Document page, Map<String, String> scrapeFields){
        TMMarketValueScrapeTemplateImpl tmMarketValueScrapeTemplate = new TMMarketValueScrapeTemplateImpl(scrapeFields);
        tmMarketValueScrapeTemplate.scrape(player, page);
        return CompletableFuture.completedFuture(player);
    }

    public CompletableFuture<Player> tmTransferScrape(Player player, Map<String, String> scrapeFields){
        TMTransferScrapeTemplateImpl tmTransferScrapeTemplate = new TMTransferScrapeTemplateImpl(scrapeFields);
        tmTransferScrapeTemplate.scrape(player);
        return CompletableFuture.completedFuture(player);
    }

    public CompletableFuture<Player> tmTransferScrape(Player player, Document page, Map<String, String> scrapeFields){
        TMTransferScrapeTemplateImpl tmTransferScrapeTemplate = new TMTransferScrapeTemplateImpl(scrapeFields);
        tmTransferScrapeTemplate.scrape(player, page);
        return CompletableFuture.completedFuture(player);
    }

    public CompletableFuture<Player> pesDbScrape(Player player, Map<String, String> scrapeFields){
        PesDbScrapeTemplateImpl pesDbScrapeTemplate = new PesDbScrapeTemplateImpl(scrapeFields);
        pesDbScrapeTemplate.scrape(player);
        return CompletableFuture.completedFuture(player);
    }

    public CompletableFuture<Player> psmlScrape(Player player, Map<String, String> scrapeFields, PageSupplier pageSupplier){
        PsmlScrapeTemplateImpl psmlScrapeTemplate = new PsmlScrapeTemplateImpl(scrapeFields, pageSupplier);
        psmlScrapeTemplate.scrape(player);
        return CompletableFuture.completedFuture(player);
    }
}
