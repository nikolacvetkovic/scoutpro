package xyz.riocode.scoutpro.scrape.job;


import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.CommandLineRunner;
import xyz.riocode.scoutpro.model.AppUserPlayer;
import xyz.riocode.scoutpro.model.AppUserPlayerId;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.repository.PlayerRepository;
import xyz.riocode.scoutpro.scrape.helper.ScrapeHelper;
import xyz.riocode.scoutpro.service.PlayerService;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
//@Component
public class ScrapeFreePlayers implements CommandLineRunner {

    private static final String PESDB_SEARCH_BASE_URL = "http://pesdb.net/pes2021/?pos=3&page=";
    private static final String PESDB_BASE_URL = "http://pesdb.net/pes2021";
    private static final String PSML_SEARCH_BASE_URL = "http://psml.rs/index.php?action=aps&q=&pesdblink=";
    private static final String PSML_BASE_URL = "http://psml.rs/index.php";
    private static final String WS_SEARCH_BASE_URL = "https://www.whoscored.com/Search/?t=";
    private static final String WS_BASE_URL = "https://www.whoscored.com";
    private static final int overallimit = 80;

    private final PlayerRepository playerRepository;
    private final PlayerService playerService;

    public ScrapeFreePlayers(PlayerRepository playerRepository, PlayerService playerService) {
        this.playerRepository = playerRepository;
        this.playerService = playerService;
    }

    public void start() throws IOException {
        int page = 0;
        while(true) {
            Document document = Jsoup.connect(PESDB_SEARCH_BASE_URL + (page++)).get();
            Map<String, String> playersData = scrapeTableByOverall(document);
            if (playersData.isEmpty())
                System.exit(0);
            for (Map.Entry<String, String> e : playersData.entrySet()) {
                log.info("Player Name: {}, PesDbId: {}", e.getKey(), e.getValue());
                if(playerRepository.findByPesDbName(e.getKey(), "cvele") != null) continue;
                try {
                Thread.sleep(25000);
                Document psmlSearchResult = getDocumentByWebDriver(PSML_SEARCH_BASE_URL + PESDB_BASE_URL + e.getValue());
                Element psmlPlayer = ScrapeHelper.getElement(psmlSearchResult, "table.style2 tr:nth-of-type(2)");
                if (ScrapeHelper.getElement(psmlPlayer, "td:nth-of-type(1) a") == null){ log.warn("Search result is empty for player: {}", e.getKey()); continue;}
                //if (ScrapeHelper.getElement(psmlPlayer, "td:nth-of-type(8) a") != null){ log.warn("Player is in team already"); continue;}
                String psmlQueryUrl = ScrapeHelper.getAttributeValue(psmlPlayer, "td:nth-of-type(1) a", "href");
                String transfermarktUrl = ScrapeHelper.getAttributeValue(psmlPlayer, "td:nth-of-type(3) a", "href");
//                Document tmPlayerPage = Jsoup.connect(transfermarktUrl).get();
//                String playerName = ScrapeHelper.getElementData(tmPlayerPage, "h1[itemprop=name]");
//                Document wsSearchResult = Jsoup.connect(WS_SEARCH_BASE_URL + playerName.replaceAll(" ", "+")).get();
//                String wsQueryUrl = ScrapeHelper.getAttributeValue(wsSearchResult, "div.search-result tr:nth-of-type(2) td:nth-of-type(1) a", "href");
//                if (wsQueryUrl == null){ log.warn("WhoScored search result is empty for player: {}", e.getKey()); continue;}
//                if (!(isWSNameContainsPesDbName(ScrapeHelper.getElementDataOwn(wsSearchResult, "div.search-result tr:nth-of-type(2) td:nth-of-type(1) a"), e.getKey()))) {
//                    log.warn("WhoScored player name does not contain PesDb name");
//                    continue;
//                }
                Thread.sleep(15000);
                Player player = new Player();
                player.setTransfermarktUrl(transfermarktUrl);
//                player.setWhoScoredUrl(WS_BASE_URL + wsQueryUrl);
                player.setPesDbUrl(PESDB_BASE_URL + e.getValue());
                player.setPsmlUrl(PSML_BASE_URL + psmlQueryUrl);
                AppUserPlayerId appUserPlayerId = new AppUserPlayerId();
                AppUserPlayer appUserPlayer = new AppUserPlayer();
                appUserPlayer.setAppUserPlayerId(appUserPlayerId);
                appUserPlayer.setMyPlayer(false);
                appUserPlayer.setPlayer(player);
                player.getUsers().add(appUserPlayer);

                    playerService.createOrUpdate(player, "cvele");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    continue;
                }
            }
        }

    }

    private static Map<String, String> scrapeTableByOverall(Document doc){
        Elements players = ScrapeHelper.getElements(doc, "table.players tr:not(:first-child)");
        return players.stream()
                .filter(e -> Integer.parseInt(ScrapeHelper.getElementDataOwn(e, "td:nth-of-type(9)")) >= overallimit)
                .collect(Collectors.toMap(e -> ScrapeHelper.getElementData(e, "td:nth-of-type(2) a"), e -> ScrapeHelper.getAttributeValue(e, "td:nth-of-type(2) a", "href").replaceAll("\\.", "")));

    }

    @Override
    public void run(String... args) throws Exception {
        start();
    }


    private static Document getDocumentByWebDriver(String url){
        WebDriver driver = null;
        String html = null;
        ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.addArguments("--headless");
        try {
            driver = new ChromeDriver(chromeOptions);
            driver.get(url);
            driver.manage().deleteAllCookies();
            driver.manage().addCookie(new Cookie.Builder("PHPSESSID", "dd30cdbecced0f98b3483e64f89a7d27").path("/").domain("psml.rs").build());
            driver.get(url);
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

    private static boolean isWSNameContainsPesDbName(String wsName, String pesDbName){
        String pesDbNameShort = extractPesDbName(pesDbName);
        return wsName.toUpperCase().contains(pesDbNameShort);
    }

    private static String extractPesDbName(String name){
        String pesDbNameShort = name.toUpperCase();
        if(pesDbNameShort.contains(".") && (pesDbNameShort.indexOf(".") != (pesDbNameShort.length()-1))) {
            pesDbNameShort = pesDbNameShort.split("\\.")[1].trim();
        } else if(pesDbNameShort.contains(".") && (pesDbNameShort.indexOf(".") == (pesDbNameShort.length()-1))){
            pesDbNameShort = pesDbNameShort.split("\\.")[0].trim();
        }
            return pesDbNameShort;
    }
}
