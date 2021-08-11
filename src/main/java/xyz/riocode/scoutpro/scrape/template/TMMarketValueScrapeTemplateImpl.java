package xyz.riocode.scoutpro.scrape.template;

import com.google.gson.*;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xyz.riocode.scoutpro.model.MarketValue;
import xyz.riocode.scoutpro.model.Player;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

public class TMMarketValueScrapeTemplateImpl extends SimpleAbstractScrapeTemplate {

    public TMMarketValueScrapeTemplateImpl(Map<String, String> scrapeFields) {
        super(scrapeFields);
    }

    @Override
    public Player scrape(Player player) {
        Document page = getPage(player.getTransfermarktUrl());
        return scrape(player, page);
    }

    @Override
    public Player scrape(Player player, Document page) {
        return scrapeMarketValues(page, player);
    }

    protected Player scrapeMarketValues(Document doc, Player player){
        Elements scripts = doc.select("script[type=text/javascript]");
        String script =  null;
        for (Element s : scripts) {
            script = s.toString();
            if (script.contains("Highcharts.setOptions([])")) {
                script = script.substring(script.indexOf("new Highcharts.Chart(") + 21);
                script = script.substring(0, script.indexOf(",'tooltip':{"));
                script = script + "}";
                script = StringEscapeUtils.unescapeJava(script.replaceAll("(?i)\\\\x([0-9a-f]{2})", "\\\\u00$1"));
                script = script.replaceAll("\\\\x", "");
                script = script.replaceAll("\\\\x20", " ");
                script = script.replaceAll("\\\\x23", "#");
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
        JsonObject jsonObject = gson.fromJson(script, JsonObject.class);
        JsonArray jsonArray = jsonObject.getAsJsonArray("series").get(0).getAsJsonObject().getAsJsonArray("data");

        for(JsonElement e : jsonArray){
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
}
