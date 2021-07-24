package xyz.riocode.scoutpro.scrape.helper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import xyz.riocode.scoutpro.exception.GetDocumentConnectionException;

import java.io.IOException;

public class ScrapeHelper {

    public static String getElementDataOwn(Element doc, String selector) {
        Element e = getElement(doc, selector);
        if (e == null) return null;
        return e.ownText().trim();
    }

    public static String getElementData(Element doc, String selector){
        Element e = getElement(doc, selector);
        if (e == null) return null;
        return e.text().trim();
    }

    public static Element getElement(Element doc, String selector) {
        return getElements(doc, selector).first();
    }

    public static Elements getElements(Element doc, String selector) {
        return doc.select(selector);
    }

    public static String getAttributeValue(Element doc, String selector, String attr) {
        return doc.select(selector).first().attr(attr);
    }

    public static Document getPage(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch(IOException ex) {
            throw new GetDocumentConnectionException(ex);
        }
    }

    public static Document getPageWithWebDriver(String url){
        WebDriver driver = null;
        String html = null;
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        try {
            driver = new ChromeDriver(chromeOptions);
            driver.get(url);
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
