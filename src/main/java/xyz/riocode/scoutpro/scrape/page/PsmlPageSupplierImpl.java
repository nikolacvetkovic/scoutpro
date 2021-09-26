package xyz.riocode.scoutpro.scrape.page;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PsmlPageSupplierImpl implements PageSupplier{

    private static final String usernameCssSelector = "input[name=username]";
    private static final String passwordCssSelector = "input[name=password]";
    private static final String buttonCssSelector = "input[name=doLogIn]";


    private final String psmlUsername;
    private final String psmlPassword;
    private final String psmlSessionCookieName;

    private String psmlSessionCookie;

    public PsmlPageSupplierImpl(@Value("${psml.username}") String psmlUsername, @Value("${psml.password}") String psmlPassword, @Value("${psml.session.cookie.name}") String psmlSessionCookieName) {
        this.psmlUsername = psmlUsername;
        this.psmlPassword = psmlPassword;
        this.psmlSessionCookieName = psmlSessionCookieName;
    }

    @Override
    public Document getPage(String url){
        WebDriver driver = null;
        String html = null;
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("disable-infobars");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--headless");
        try {
            driver = new ChromeDriver(chromeOptions);
            driver.get(url);
            if(this.psmlSessionCookie != null) {
                driver.manage().deleteAllCookies();
                driver.manage().addCookie(new Cookie(psmlSessionCookieName, psmlSessionCookie));
                driver.get(url);
            }
            List<WebElement> changePasswordForm = driver.findElements(By.id("ChangePassword"));
            if(changePasswordForm.size() == 0){
                driver.findElement(By.cssSelector(usernameCssSelector)).sendKeys(this.psmlUsername);
                driver.findElement(By.cssSelector(passwordCssSelector)).sendKeys(this.psmlPassword);
                driver.findElement(By.cssSelector(buttonCssSelector)).click();
                this.psmlSessionCookie = driver.manage().getCookieNamed(psmlSessionCookieName).getValue();
            }
            Thread.sleep(5000);
            driver.switchTo().frame("content");
            html = driver.getPageSource();
        } catch (InterruptedException e){

        } finally {
            if(driver != null)
                driver.quit();
        }
        return Jsoup.parse(html);
    }
}
