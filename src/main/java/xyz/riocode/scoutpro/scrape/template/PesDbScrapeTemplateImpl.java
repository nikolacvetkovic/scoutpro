package xyz.riocode.scoutpro.scrape.template;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xyz.riocode.scoutpro.enums.Foot;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.scrape.helper.ScrapeHelper;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PesDbScrapeTemplateImpl extends SimpleAbstractScrapeTemplate {

    public PesDbScrapeTemplateImpl(Map<String, String> scrapeFields) {
        super(scrapeFields);
    }

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
        if(ScrapeHelper.getElementData(doc, scrapeFields.get("teamName")).equals("Free Agent")){
            extractCoreDataFreePlayer(doc, player);
        } else {
            extractCoreDataStandard(doc, player);
        }
    }

    protected void scrapeRatings(Document doc, Player player){
        player.setOffensiveAwareness(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("offensiveAwareness"))));
        player.setBallControl(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("ballControl"))));
        player.setDribbling(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("dribbling"))));
        player.setTightPossession(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("tightPossession"))));
        player.setLowPass(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("lowPass"))));
        player.setLoftedPass(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("loftedPass"))));
        player.setFinishing(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("finishing"))));
        player.setHeading(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("heading"))));
        player.setPlaceKicking(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("placeKicking"))));
        player.setCurl(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("curl"))));
        player.setSpeed(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("speed"))));
        player.setAcceleration(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("acceleration"))));
        player.setKickingPower(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("kickingPower"))));
        player.setJump(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("jump"))));
        player.setPhysicalContact(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("physicalContact"))));
        player.setBalance(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("balance"))));
        player.setStamina(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("stamina"))));
        player.setDefensiveAwareness(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("defensiveAwareness"))));
        player.setBallWinning(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("ballWinning"))));
        player.setAggression(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("aggression"))));
        player.setGkAwareness(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("gkAwareness"))));
        player.setGkCatching(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("gkCatching"))));
        player.setGkClearing(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("gkClearing"))));
        player.setGkReflexes(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("gkReflexes"))));
        player.setGkReach(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("gkReach"))));
        player.setWeakFootUsage(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("weakFootUsage"))));
        player.setWeakFootAccuracy(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("weakFootAccuracy"))));
        player.setForm(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("form"))));
        player.setInjuryResistance(
                Integer.parseInt(ScrapeHelper.getElementData(doc, scrapeFields.get("injuryResistance"))));
        player.setOverallRating(
                Integer.parseInt(ScrapeHelper.getElementDataOwn(doc, scrapeFields.get("overallRating"))));
    }

    protected void scrapeAdditionalData(Document doc, Player player){
        String playingStyles = null;
        Set<String> playerSkills = new HashSet<>();
        Set<String> comPlayingStyles = new HashSet<>();
        Elements addInfo = ScrapeHelper.getElements(doc, scrapeFields.get("additionalData"));
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
        String pesDbName = ScrapeHelper.getElementData(doc, scrapeFields.get("playerName"));
        player.setPesDbPlayerName(pesDbName);
        String teamName = ScrapeHelper.getElementData(doc, scrapeFields.get("teamName"));
        player.setPesDbTeamName(teamName);
        String foot = ScrapeHelper.getElementData(doc, scrapeFields.get("foot"));
        foot = foot.split(" ")[0];
        // todo
        player.setFoot(Foot.valueOf(foot.toUpperCase()));
        String weekCondition = ScrapeHelper.getElementData(doc, scrapeFields.get("weekCondition"));
        player.setWeekCondition(weekCondition!=null?weekCondition.charAt(0):null);
        String primaryPosition = ScrapeHelper.getElementData(doc, scrapeFields.get("primaryPosition"));
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
        String pesDbName = ScrapeHelper.getElementData(doc, scrapeFields.get("playerName"));
        player.setPesDbPlayerName(pesDbName);
        String teamName = ScrapeHelper.getElementData(doc, scrapeFields.get("teamNameFreePlayer"));
        player.setPesDbTeamName(teamName);
        String foot = ScrapeHelper.getElementData(doc, scrapeFields.get("footFreePlayer"));
        foot = foot.split(" ")[0];
        //todo
        player.setFoot(Foot.valueOf(foot.toUpperCase()));
        String weekCondition = ScrapeHelper.getElementData(doc, scrapeFields.get("weekConditionFreePlayer"));
        player.setWeekCondition(weekCondition!=null?weekCondition.charAt(0):null);
        String primaryPosition = ScrapeHelper.getElementData(doc, scrapeFields.get("primaryPositionFreePlayer"));
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
        Elements weakerPositions = ScrapeHelper.getElements(e, scrapeFields.get("weakPositions"));
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
        Elements strongerPositions = ScrapeHelper.getElements(e, scrapeFields.get("strongPositions"));
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
