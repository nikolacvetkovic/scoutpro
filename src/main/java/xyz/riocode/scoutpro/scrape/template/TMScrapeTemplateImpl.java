package xyz.riocode.scoutpro.scrape.template;

import com.google.gson.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.model.MarketValue;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.model.Transfer;
import xyz.riocode.scoutpro.scrape.helper.ScrapeHelper;
import xyz.riocode.scoutpro.scrape.model.ScrapeField;
import xyz.riocode.scoutpro.scrape.repository.ScrapeFieldRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Component("TMTemplate")
public class TMScrapeTemplateImpl implements ScrapeTemplate {

    private final ScrapeFieldRepository scrapeFieldRepository;

    public TMScrapeTemplateImpl(ScrapeFieldRepository scrapeFieldRepository) {
        this.scrapeFieldRepository = scrapeFieldRepository;
    }

    @Override
    public void scrape(String pageContent, Player player) {
        Map<String, String> scrapeFields = scrapeFieldRepository.findByScrapeSite_Name("TM Core").stream()
                .collect(Collectors.toMap(ScrapeField::getName, ScrapeField::getSelector));
        Document page = ScrapeHelper.createDocument(pageContent);
        scrapeCoreData(page, player, scrapeFields);
        scrapeMarketValues(page, player, scrapeFields);
        scrapeTransfers(page, player, scrapeFields);
    }

    protected void scrapeCoreData(Document doc, Player player, Map<String, String> scrapeFields) {
        String playerName = ScrapeHelper.getAttributeValue(doc, scrapeFields.get("playerName"), "alt").trim();
        player.setName(playerName);
        String clubTeam = ScrapeHelper.getElementData(doc, scrapeFields.get("clubTeam"));
        player.setClubTeam(clubTeam);
        String contractUntil = ScrapeHelper.getElementData(doc, scrapeFields.get("contractUntil"));
        player.setContractUntil(contractUntil);
        String nationality = ScrapeHelper.getElementData(doc, scrapeFields.get("nationality"));
        player.setNationality(nationality);
        String position = ScrapeHelper.getElementData(doc, scrapeFields.get("position"));
        player.setTransfermarktPosition(position);
        String birthDate = ScrapeHelper.getElementData(doc, scrapeFields.get("birthDate"));
        birthDate = birthDate.substring(0, birthDate.indexOf("("));
        player.setDateOfBirth(birthDate);
        int age = Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("age")));
        player.setAge(age);
        String nationalTeam = ScrapeHelper.getAttributeValue(doc, scrapeFields.get("nationalTeam"), "title");
        player.setNationalTeam(nationalTeam);
    }

    protected Player scrapeMarketValues(Document doc, Player player, Map<String, String> scrapeFields){
        Elements scripts = doc.select("script[type=text/javascript]");
        String script =  null;
        for (Element s : scripts) {
            script = s.toString();
            if (script.contains("Highcharts.setOptions([])")) {
                script = script.substring(script.indexOf("new Highcharts.Chart(") + 21);
                script = script.substring(0, script.indexOf(",'tooltip':{")) + "}";
                script = script.substring(script.indexOf("'series':")+9, script.indexOf(",'legend':"));
                script = script.replaceAll("(?i)\\\\x([0-9a-f]{2})", "\\\\u00$1");
                script = script.replaceAll("\\\\x20", " ");
                script = script.replaceAll("\\\\x23", "#");
                script = script.replaceAll("\\\\x27", "'");
                script = script.replaceAll("\\\\x28", "(");
                script = script.replaceAll("\\\\x3A", ":");
                script = script.replaceAll("\\\\x2F", "/");
                script = script.replaceAll("\\\\x3F", "?");
                script = script.replaceAll("\\\\x3D", "=");
                script = script.replaceAll("\\\\x29", ")");
                script = script.replaceAll("\\\\x2D", "-");
                break;
            }
        }
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        JsonArray series = gson.fromJson(script, JsonArray.class);
        JsonArray data = series.get(0).getAsJsonObject().getAsJsonArray("data");

        for(JsonElement e : data){
            MarketValue mv = new MarketValue();
            JsonObject o = e.getAsJsonObject();
            String worth = o.get("y").getAsString().trim();
            mv.setWorth(new BigDecimal(worth));
            String date = o.get("datum_mw").getAsString().trim();
            mv.setDatePoint(LocalDate.parse(date, DateTimeFormatter.ofPattern("MMM d, yyyy").withLocale(Locale.US)));
            String clubTeam = o.get("verein").getAsString().trim();
            mv.setClubTeam(clubTeam);
            mv.setPlayer(player);
            player.getMarketValues().add(mv);
        }

        player.setMarketValueLastCheck(LocalDateTime.now());

        return player;
    }
    protected Player scrapeTransfers(Document doc, Player player, Map<String, String> scrapeFields){
        Elements elements = ScrapeHelper.getElements(doc, scrapeFields.get("transferTable"));
        for(Element e : elements){
            Transfer transfer = new Transfer();
            String dateString = ScrapeHelper.getElementData(e, scrapeFields.get("dateOfTransfer"));
            LocalDate dateOfTransfer = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("MMM d, yyyy").withLocale(Locale.US));
            transfer.setDateOfTransfer(dateOfTransfer);
            String fromTeam = ScrapeHelper.getElementData(e, scrapeFields.get("fromTeam_1st"));
            if (fromTeam == null) {
                fromTeam = ScrapeHelper.getElementData(e, scrapeFields.get("fromTeam_2nd"));
            }
            transfer.setFromTeam(fromTeam);
            String toTeam = ScrapeHelper.getElementData(e, scrapeFields.get("toTeam_1st"));
            if (toTeam == null) {
                toTeam = ScrapeHelper.getElementData(e, scrapeFields.get("toTeam_2nd"));
            }
            transfer.setToTeam(toTeam);
            String marketValue = ScrapeHelper.getElementData(e, scrapeFields.get("marketValue"));
            transfer.setMarketValue(marketValue);
            String transferFee = ScrapeHelper.getElementData(e, scrapeFields.get("transferFee"));
            transfer.setTransferFee(transferFee);
            transfer.setPlayer(player);
            player.getTransfers().add(transfer);
        }
        player.setTransferLastCheck(LocalDateTime.now());
        return player;
    }
}
