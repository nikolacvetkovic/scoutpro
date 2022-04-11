package xyz.riocode.scoutpro.scrape.loader;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component("TMLoader")
public class TMPageLoaderImpl implements PageLoader {

    @Override
    public String loadPage(URL url, WebDriver webDriver) {
        try {
            webDriver.get(url.toString());
            Thread.sleep(1500);
            return webDriver.getPageSource();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
