package xyz.riocode.scoutpro.scrape.loader;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.scrape.helper.ScrapeHelper;

import java.net.URL;

@Component("PesDbLoader")
public class PesDbPageLoaderImpl implements PageLoader {

    @Override
    public String loadPage(URL url, WebDriver webDriver) {
        return ScrapeHelper.getPage(url.toString()).toString();
    }
}
