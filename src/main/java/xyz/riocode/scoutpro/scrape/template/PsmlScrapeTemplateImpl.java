package xyz.riocode.scoutpro.scrape.template;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.scrape.helper.ScrapeHelper;
import xyz.riocode.scoutpro.scrape.model.ScrapeField;
import xyz.riocode.scoutpro.scrape.repository.ScrapeFieldRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Component("PsmlTemplate")
public class PsmlScrapeTemplateImpl implements ScrapeTemplate {

    private final ScrapeFieldRepository scrapeFieldRepository;

    public PsmlScrapeTemplateImpl(ScrapeFieldRepository scrapeFieldRepository){
        this.scrapeFieldRepository = scrapeFieldRepository;
    }

    @Override
    public void scrape(String pageContent, Player player) {
//        Player player = new Player();
        //todo - implement caching
        Map<String, String> scrapeFields = scrapeFieldRepository.findByScrapeSite_Name("Psml").stream()
                .collect(Collectors.toMap(ScrapeField::getName, ScrapeField::getSelector));
        scrapeCoreData(ScrapeHelper.createDocument(pageContent), player, scrapeFields);
//        return player;
    }

    protected void scrapeCoreData(Document doc, Player player, Map<String, String> scrapeFields){
        String teamName = ScrapeHelper.getElementData(doc, scrapeFields.get("teamName"));
        player.setPsmlTeam(teamName != null?teamName:"Free");
        String teamValue = ScrapeHelper.getElementDataOwn(doc, scrapeFields.get("teamValue"));
        if(teamValue != null){
            teamValue = teamValue.replaceAll("[^0-9,]", "").replace(",", "");
        }
        player.setPsmlValue(NumberUtils.isCreatable(teamValue)?new BigDecimal(teamValue):BigDecimal.ZERO);
        player.setPsmlLastCheck(LocalDateTime.now());
    }
}