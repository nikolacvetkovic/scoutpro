package xyz.riocode.scoutpro.scrape.engine;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.config.ScrapeConfig;
import xyz.riocode.scoutpro.scrape.loader.PageLoader;

import javax.annotation.PreDestroy;
import java.net.URL;

@Component
public class ScrapeLoader {

    private final WebDriver webDriver;
    private final String firstTab;
    private final ScrapeConfig scrapeConfig;

    public ScrapeLoader(ScrapeConfig scrapeConfig) {
        this.scrapeConfig = scrapeConfig;
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("disable-infobars");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        this.webDriver = new ChromeDriver(chromeOptions);
        this.firstTab = this.webDriver.getWindowHandle();
    }

    public String loadAndGetPageContent(URL url, PageLoader pageLoader) {
        //open new tab
        this.webDriver.switchTo().newWindow(WindowType.TAB);
        //load page
        String pageContent = pageLoader.loadPage(url, this.webDriver);
        //close tab
        this.webDriver.close();
        //return to first tab
        this.webDriver.switchTo().window(firstTab);

        return pageContent;
    }

    @PreDestroy
    public void destroy() {
        this.webDriver.quit();
    }
}
