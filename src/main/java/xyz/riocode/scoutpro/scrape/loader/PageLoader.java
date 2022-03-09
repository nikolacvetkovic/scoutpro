package xyz.riocode.scoutpro.scrape.loader;

import org.openqa.selenium.WebDriver;

import java.net.URL;

public interface PageLoader {
    String loadPage(URL url, WebDriver webDriver);
}
