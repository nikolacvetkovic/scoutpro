package xyz.riocode.scoutpro.scrape.loader;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.config.ScrapeConfig;

import java.net.URL;
import java.util.List;

@Component("PsmlLoader")
public class PsmlPageLoaderImpl implements PageLoader {

    private static final String usernameCssSelector = "input[name=username]";
    private static final String passwordCssSelector = "input[name=password]";
    private static final String buttonCssSelector = "input[name=doLogIn]";

    private final ScrapeConfig scrapeConfig;

    public PsmlPageLoaderImpl(ScrapeConfig scrapeConfig) {
        this.scrapeConfig = scrapeConfig;
    }

    @Override
    public String loadPage(URL url, WebDriver webDriver){
        try {
            webDriver.get(url.toString());
            if(this.scrapeConfig.getPsmlSessionCookie() != null) {
                webDriver.manage().deleteAllCookies();
                webDriver.manage().addCookie(new Cookie(this.scrapeConfig.getPsmlSessionCookieName(),
                                                            this.scrapeConfig.getPsmlSessionCookie()));
                webDriver.get(url.toString());
            }
            List<WebElement> changePasswordForm = webDriver.findElements(By.id("ChangePassword"));
            if(changePasswordForm.size() == 0){
                webDriver.findElement(By.cssSelector(usernameCssSelector)).sendKeys(this.scrapeConfig.getPsmlUsername());
                webDriver.findElement(By.cssSelector(passwordCssSelector)).sendKeys(this.scrapeConfig.getPsmlPassword());
                webDriver.findElement(By.cssSelector(buttonCssSelector)).click();
                this.scrapeConfig.setPsmlSessionCookie(webDriver.manage()
                        .getCookieNamed(this.scrapeConfig.getPsmlSessionCookieName()).getValue());
            }
            Thread.sleep(5000);
            webDriver.switchTo().frame("content");
            return webDriver.getPageSource();
        } catch (InterruptedException e){

        }
        return null;
    }
}
