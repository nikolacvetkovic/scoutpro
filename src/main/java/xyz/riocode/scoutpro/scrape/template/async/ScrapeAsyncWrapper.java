package xyz.riocode.scoutpro.scrape.template.async;

import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.scrape.helper.ScrapeHelper;
import xyz.riocode.scoutpro.scrape.page.PsmlPageSupplierImpl;
import xyz.riocode.scoutpro.scrape.template.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Async
@Component
public class ScrapeAsyncWrapper {
    
    private final PsmlPageSupplierImpl pageSupplier;

    public ScrapeAsyncWrapper(PsmlPageSupplierImpl pageSupplier) {
        this.pageSupplier = pageSupplier;
    }

    public CompletableFuture<Player> tmAllScrape(Player player){

        Document page = ScrapeHelper.getPage(player.getTransfermarktUrl());

        CompletableFuture<Player> tmCore = tmCoreScrape(player, page);
        CompletableFuture<Player> tmMarketValues = tmMarketValueScrape(player, page);
        CompletableFuture<Player> tmTransfers = tmTransferScrape(player, page);

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

    public CompletableFuture<Player> tmCoreScrape(Player player){
        TMCoreScrapeTemplateImpl tmCoreScrapeTemplate = new TMCoreScrapeTemplateImpl();
        tmCoreScrapeTemplate.scrape(player);
        return CompletableFuture.completedFuture(player);
    }

    private CompletableFuture<Player> tmCoreScrape(Player player, Document page){
        TMCoreScrapeTemplateImpl tmCoreScrapeTemplate = new TMCoreScrapeTemplateImpl();
        tmCoreScrapeTemplate.scrape(player, page);
        return CompletableFuture.completedFuture(player);
    }

    public CompletableFuture<Player> tmMarketValueScrape(Player player){
        TMMarketValueScrapeTemplateImpl tmMarketValueScrapeTemplate = new TMMarketValueScrapeTemplateImpl();
        tmMarketValueScrapeTemplate.scrape(player);
        return CompletableFuture.completedFuture(player);
    }

    private CompletableFuture<Player> tmMarketValueScrape(Player player, Document page){
        TMMarketValueScrapeTemplateImpl tmMarketValueScrapeTemplate = new TMMarketValueScrapeTemplateImpl();
        tmMarketValueScrapeTemplate.scrape(player, page);
        return CompletableFuture.completedFuture(player);
    }

    public CompletableFuture<Player> tmTransferScrape(Player player){
        TMTransferScrapeTemplateImpl tmTransferScrapeTemplate = new TMTransferScrapeTemplateImpl();
        tmTransferScrapeTemplate.scrape(player);
        return CompletableFuture.completedFuture(player);
    }

    public CompletableFuture<Player> tmTransferScrape(Player player, Document page){
        TMTransferScrapeTemplateImpl tmTransferScrapeTemplate = new TMTransferScrapeTemplateImpl();
        tmTransferScrapeTemplate.scrape(player, page);
        return CompletableFuture.completedFuture(player);
    }

    public CompletableFuture<Player> wsAllScrape(Player player){
        Document page = ScrapeHelper.getPageWithWebDriver(player.getWhoScoredUrl());

        CompletableFuture<Player> statistics = statisticsScrape(player, page);
        CompletableFuture<Player> characteristics = characteristicsScrape(player, page);

        CompletableFuture.allOf(statistics, characteristics).join();
        Player p = null;

        try {
            p = statistics.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(p);
    }

    public CompletableFuture<Player> statisticsScrape(Player player){
        StatisticsScrapeTemplateImpl statisticsScrapeTemplate = new StatisticsScrapeTemplateImpl();
        statisticsScrapeTemplate.scrape(player);
        return CompletableFuture.completedFuture(player);
    }

    private CompletableFuture<Player> statisticsScrape(Player player, Document page){
        StatisticsScrapeTemplateImpl statisticsScrapeTemplate = new StatisticsScrapeTemplateImpl();
        statisticsScrapeTemplate.scrape(player, page);
        return CompletableFuture.completedFuture(player);
    }

    public CompletableFuture<Player> characteristicsScrape(Player player){
        CharacteristicsScrapeTemplateImpl characteristicsScrapeTemplate = new CharacteristicsScrapeTemplateImpl();
        characteristicsScrapeTemplate.scrape(player);
        return CompletableFuture.completedFuture(player);
    }

    private CompletableFuture<Player> characteristicsScrape(Player player, Document page){
        CharacteristicsScrapeTemplateImpl characteristicsScrapeTemplate = new CharacteristicsScrapeTemplateImpl();
        characteristicsScrapeTemplate.scrape(player, page);
        return CompletableFuture.completedFuture(player);
    }

    public CompletableFuture<Player> pesDbScrape(Player player){
        PesDbScrapeTemplateImpl pesDbScrapeTemplate = new PesDbScrapeTemplateImpl();
        pesDbScrapeTemplate.scrape(player);
        return CompletableFuture.completedFuture(player);
    }

    public CompletableFuture<Player> psmlScrape(Player player){
        PsmlScrapeTemplateImpl psmlScrapeTemplate = new PsmlScrapeTemplateImpl(pageSupplier);
        psmlScrapeTemplate.scrape(player);
        return CompletableFuture.completedFuture(player);
    }
}
