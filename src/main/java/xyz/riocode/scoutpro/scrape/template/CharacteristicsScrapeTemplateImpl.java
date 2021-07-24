package xyz.riocode.scoutpro.scrape.template;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.scrape.helper.ScrapeHelper;

public class CharacteristicsScrapeTemplateImpl extends WebDriverAbstractScrapeTemplate {

    @Override
    public Player scrape(Player player){
        Document page = getPage(player.getWhoScoredUrl());
        return scrape(player, page);
    }

    @Override
    public Player scrape(Player player, Document page) {
//        Characteristic characteristic = player.getCharacteristic();
//        if(characteristic == null){
//            characteristic = new Characteristic();
//            characteristic.setPlayer(player);
//            player.setCharacteristic(characteristic);
//        }
        scrapeCharacteristic(page, player);

        return player;
    }

    private void scrapeCharacteristic(Document doc, Player player){
        Elements el1 = ScrapeHelper.getElements(doc, "div.character-card div.strengths tr");
        if(el1.size() > 0){
            for(Element e : el1){
                String s1 = ScrapeHelper.getElementDataOwn(e, "td:nth-of-type(1) div");
                String s2 = ScrapeHelper.getElementData(e, "td:nth-of-type(2) span");
                if(s1 != null && s2 != null) player.getStrengths().add(s1 + " - " + s2);
            }
        }

        Elements el2 = ScrapeHelper.getElements(doc, "div.character-card div.weaknesses tr");
        if(el2.size() > 0){
            for (Element e : el2) {
                String s1 = ScrapeHelper.getElementDataOwn(e, "td:nth-of-type(1) div");
                String s2 = ScrapeHelper.getElementData(e, "td:nth-of-type(2) span");
                if(s1 != null && s2 != null) player.getWeaknesses().add(s1 + " - " + s2);
            }
        }

        Elements el3 = ScrapeHelper.getElements(doc, "div.style li");
        if(el3.size() > 0){
            for (Element e : el3) {
                String s = e.ownText();
                if(s != null) player.getStylesOfPlay().add(s);
            }
        }

    }

    @Override
    public Document getPage(String url) {
        WebDriver driver = null;
        String html = null;
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        try {
            driver = new ChromeDriver(chromeOptions);
            driver.get(url);
            Thread.sleep(5000);
            driver.findElement(By.cssSelector("#qcCmpUi .qc-cmp-button")).click();
            Thread.sleep(5000);
            html = driver.getPageSource();
        } catch (InterruptedException e){

        } finally {
            if(driver != null)
                driver.quit();
        }
        return Jsoup.parse(html);
    }
}
