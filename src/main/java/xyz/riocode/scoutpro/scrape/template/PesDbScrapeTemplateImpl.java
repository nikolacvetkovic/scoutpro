package xyz.riocode.scoutpro.scrape.template;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xyz.riocode.scoutpro.enums.Foot;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.scrape.helper.ScrapeHelper;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class PesDbScrapeTemplateImpl extends SimpleAbstractScrapeTemplate {

    @Override
    public Player scrape(Player player){
        Document page = getPage(player.getPesDbUrl());
        return scrape(player, page);
    }

    @Override
    public Player scrape(Player player, Document page) {
        // todo implement async
        scrapeCoreData(page, player);
        scrapeRatings(page, player);
        scrapeAdditionalData(page, player);
        return player;
    }

    protected void scrapeCoreData(Document doc, Player player){
        if(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(3) td a").equals("Free Agent")){
            extractCoreDataFreePlayer(doc, player);
        } else {
            extractCoreDataStandard(doc, player);
        }
    }

    protected void scrapeRatings(Document doc, Player player){
        player.setOffensiveAwareness(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(1) td")));
        player.setBallControl(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(2) td")));
        player.setDribbling(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(3) td")));
        player.setTightPossession(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(4) td")));
        player.setLowPass(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(5) td")));
        player.setLoftedPass(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(6) td")));
        player.setFinishing(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(7) td")));
        player.setHeading(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(8) td")));
        player.setPlaceKicking(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(9) td")));
        player.setCurl(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(10) td")));
        player.setSpeed(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(11) td")));
        player.setAcceleration(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(12) td")));
        player.setKickingPower(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(13) td")));
        player.setJump(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(14) td")));
        player.setPhysicalContact(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(15) td")));
        player.setBalance(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(2) table tr:nth-of-type(16) td")));
        player.setStamina(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(1) td")));
        player.setDefensiveAwareness(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(2) td")));
        player.setBallWinning(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(3) td")));
        player.setAggression(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(4) td")));
        player.setGkAwareness(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(5) td")));
        player.setGkCatching(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(6) td")));
        player.setGkClearing(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(7) td")));
        player.setGkReflexes(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(8) td")));
        player.setGkReach(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(9) td")));
        player.setWeakFootUsage(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(10) td")));
        player.setWeakFootAccuracy(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(11) td")));
        player.setForm(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(12) td")));
        player.setInjuryResistance(
                Integer.parseInt(ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(13) td")));
        player.setOverallRating(
                Integer.parseInt(ScrapeHelper.getElementDataOwn(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(3) table tr:nth-of-type(15) td")));
    }

    protected void scrapeAdditionalData(Document doc, Player player){
        String playingStyles = null;
        Set<String> playerSkills = new HashSet<>();
        Set<String> comPlayingStyles = new HashSet<>();
        Elements addInfo = ScrapeHelper.getElements(doc, "table.playing_styles tr");
        int counter = 0;
        String value;
        for (Element e : addInfo) {
            if(ScrapeHelper.getElement(e, "th") != null){
                counter++;
                continue;
            }
            value = ScrapeHelper.getElementData(e, "td");
            switch(counter){
                case 1:
                    playingStyles = value;
                break;
                case 2:
                    if(!value.equals("-")) playerSkills.add(value);
                break;
                case 3:
                    if(!value.equals("-")) comPlayingStyles.add(value);
                break;
            }
        }
        player.setPlayingStyle(playingStyles);
        player.setPlayerSkills(playerSkills);
        player.setComPlayingStyles(comPlayingStyles);
    }

    private void extractCoreDataStandard(Document doc, Player player){
        String pesDbName = ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(1) td");
        player.setPesDbPlayerName(pesDbName);
        String teamName = ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(3) td a");
        player.setPesDbTeamName(teamName);
        String foot = ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(10) td");
        foot = foot.split(" ")[0];
        // todo
        player.setFoot(Foot.valueOf(foot.toUpperCase()));
        String weekCondition = ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(11) td");
        player.setWeekCondition(weekCondition!=null?weekCondition.charAt(0):null);
        String primaryPosition = ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(12) td div");
        //todo
//        for (Positions p : Positions.values()) {
//            if(p.toString().equals(primaryPosition)){
//                player.setPositionNumberValue(p.getNumberValue());
//            }
//        }
        player.setPrimaryPosition(primaryPosition);
        player.setOtherStrongPositions(extractOtherStrongPositions(doc, player));
        player.setOtherWeakPositions(extractOtherWeakPositions(doc));
        player.setPesDbLastCheck(LocalDateTime.now());
    }

    private void extractCoreDataFreePlayer(Document doc, Player player){
        String pesDbName = ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(1) td");
        player.setPesDbPlayerName(pesDbName);
        String teamName = ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(2) td a");
        player.setPesDbTeamName(teamName);
        String foot = ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(9) td");
        foot = foot.split(" ")[0];
        //todo
        player.setFoot(Foot.valueOf(foot.toUpperCase()));
        String weekCondition = ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(10) td");
        player.setWeekCondition(weekCondition!=null?weekCondition.charAt(0):null);
        String primaryPosition = ScrapeHelper.getElementData(doc, "table.player tbody tr:nth-of-type(1) td:nth-of-type(1) table tr:nth-of-type(11) td div");
        //todo
//        for (Positions p : Positions.values()) {
//            if(p.toString().equals(primaryPosition)){
//                player.setPositionNumberValue(p.getNumberValue());
//            }
//        }
        player.setPrimaryPosition(primaryPosition);
        player.setOtherStrongPositions(extractOtherStrongPositions(doc, player));
        player.setOtherWeakPositions(extractOtherWeakPositions(doc));
        player.setPesDbLastCheck(LocalDateTime.now());
    }

    private Set<String> extractOtherWeakPositions(Element e){
        Set<String> positions = new HashSet<>();
        Elements weakerPositions = ScrapeHelper.getElements(e, "table.player tbody table tr td.positions div span.pos1");
        String s;
        if(weakerPositions.size() > 0){
            for(Element el : weakerPositions){
                s = el.text();
                positions.add(s);
            }
        }

        return positions;
    }
    private Set<String> extractOtherStrongPositions(Element e, Player player){
        Set<String> positions = new HashSet<>();
        Elements strongerPositions = ScrapeHelper.getElements(e, "table.player tbody table tr td.positions div span.pos2");
        String s;
        if(strongerPositions.size() > 0){
            for(Element el : strongerPositions){
                s = el.text();
                if(!s.equals(player.getPrimaryPosition()))
                    positions.add(s);
            }
        }

        return positions;
    }

    private enum Positions {
        GK(1), CB(2), LB(3), RB(4), DMF(5), CMF(6), LMF(7), RMF(8), AMF(9), LWF(10), RWF(11), SS(12), CF(13);

        private int numberValue;

        Positions(int numberValue) {
            this.numberValue = numberValue;
        }

        public int getNumberValue(){
            return this.numberValue;
        }

    }
    
}
