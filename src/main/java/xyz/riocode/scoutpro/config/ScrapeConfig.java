package xyz.riocode.scoutpro.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Getter
@Setter
@Configuration
public class ScrapeConfig {

    @Value("${webdriver.path}")
    private String webDriverPath;

    @Value("${psml.username}")
    private String psmlUsername;
    @Value("${psml.password}")
    private String psmlPassword;
    @Value("${psml.session.cookie.name}")
    private String psmlSessionCookieName;

    private String psmlSessionCookie;

    @PostConstruct
    public void setProperty(){
        System.setProperty("webdriver.chrome.driver", webDriverPath);
        System.out.println(webDriverPath);
    }

}
