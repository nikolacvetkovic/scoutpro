package xyz.riocode.scoutpro.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.model.*;
import xyz.riocode.scoutpro.model.security.AppUserPlayer;
import xyz.riocode.scoutpro.model.security.AppUserPlayerId;
import xyz.riocode.scoutpro.repository.AppUserRepository;
import xyz.riocode.scoutpro.repository.PlayerRepository;
import xyz.riocode.scoutpro.service.PlayerService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    private final PlayerRepository playerRepository;
    private final AppUserRepository appUserRepository;
    private final PlayerService playerService;

    public DataLoader(PlayerRepository playerRepository,
                      AppUserRepository appUserRepository,
                      PlayerService playerService) {
        this.playerRepository = playerRepository;
        this.appUserRepository = appUserRepository;
        this.playerService = playerService;
    }

    @Override
    public void run(String... args) throws Exception {
//        Player player = new Player();
//        player.setPlayerName("asdasdasd");
//        player.setTransfermarktUrl("asdasd");
//        player.setWhoScoredUrl("asdasd");
//        player.setPesDbUrl("asdasd");
//        player.setPsmlUrl("asdasd");
//
//        playerRepository.save(player);
    }



//    private AppUser saveUser(){
//        AppUser user = new AppUser();
//        user.setUsername("cvele");
//        user.setPassword("asdasd");
//
//        return appUserRepository.findByUsername("cvele").get();
//    }


    private Player createPlayer(){
        Player player = new Player();
        player.setTransfermarktCoreUrl("https://www.transfermarkt.com/frenkie-de-jong/profil/spieler/326330");
        player.setWhoScoredUrl("https://www.whoscored.com/Players/279423/Show/Frenkie-de-Jong");
        player.setPesDbUrl("http://pesdb.net/pes2019/?id=108662");
        player.setPsmlUrl("http://psml.rs/?action=shwply&playerID=108662");

        AppUserPlayerId appUserPlayerId = new AppUserPlayerId();
        AppUserPlayer appUserPlayer = new AppUserPlayer();
        appUserPlayer.setMyPlayer(true);
        appUserPlayer.setPlayer(player);
        appUserPlayer.setAppUserPlayerId(appUserPlayerId);
        //appUser.getPlayers().add(appUserPlayer);
        player.getUsers().add(appUserPlayer);

        log.info("Player created");

        return player;
    }

    private void saveMarketValues(Player player){
        Set<MarketValue> marketValues = new HashSet<>();
        MarketValue mv1 = new MarketValue();
        mv1.setClubTeam("Leicester City");
        mv1.setDatePoint(LocalDate.of(2018, 12, 19));
        mv1.setWorth(BigDecimal.valueOf(45000000.00));
        mv1.setPlayer(player);
        marketValues.add(mv1);

        MarketValue mv2 = new MarketValue();
        mv2.setClubTeam("Leicester City");
        mv2.setDatePoint(LocalDate.of(2019, 6, 13));
        mv2.setWorth(BigDecimal.valueOf(50000000.00));
        mv2.setPlayer(player);
        marketValues.add(mv2);

        player.setMarketValues(marketValues);
        player.setMarketValueLastCheck(LocalDateTime.now());
    }

    private void saveTransfers(Player player){
        Set<Transfer> transfers = new HashSet<>();
        Transfer tr1 = new Transfer();
        tr1.setFromTeam("Wigan");
        tr1.setToTeam("Hull City");
        tr1.setDateOfTransfer(LocalDate.of(2015, 5, 31));
        tr1.setMarketValue("2000000");
        tr1.setTransferFee("End of Loan");
        tr1.setPlayer(player);
        transfers.add(tr1);

        Transfer tr2 = new Transfer();
        tr2.setFromTeam("Hull City");
        tr2.setToTeam("Leicester City");
        tr2.setDateOfTransfer(LocalDate.of(2017, 7, 1));
        tr2.setMarketValue("8000000");
        tr2.setTransferFee("13700000");
        tr2.setPlayer(player);
        transfers.add(tr2);

        player.setTransfers(transfers);
        player.setTransferLastCheck(LocalDateTime.now());
    }

    private void saveAllplayer(Player player){
        Player foundPlayer = playerRepository.findById(player.getId()).get();
        player.setAge(26);
        player.setClubTeam("Leicester City");
        player.setContractUntil("30.06.2023");
        player.setDateOfBirth("Mar 5, 1993");
        player.setNationality("England");
        player.setNationalTeam("England");
        player.setTransfermarktPosition("Centre-Back");
    }

    private void saveplayer(Player player){
//        player.setPesDbPlayerName("H. MAGUIRE");
//        player.setPesDbTeamName("EAST MIDLANDS");
//        player.setFoot(Foot.RIGHT);
//        player.setWeekCondition('C');
//        player.setPrimaryPosition("CB");
//        Set<String> otherStrongPositions = new HashSet<>();
//        otherStrongPositions.add("CMF");
//        player.setOtherStrongPositions(otherStrongPositions);
//        Set<String> otherWeakPositions = new HashSet<>();
//        otherWeakPositions.add("RB");
//        player.setOtherWeakPositions(otherWeakPositions);
//        player.setOffensiveAwareness(64);
//        player.setBallControl(78);
//        player.setDribbling(73);
//        player.setLowPass(79);
//        player.setLoftedPass(74);
//        player.setFinishing(60);
//        player.setPlaceKicking(61);
//        player.setSwerve(62);
//        player.setHeader(89);
//        player.setDefensiveProwess(86);
//        player.setBallWinning(89);
//        player.setKickingPower(73);
//        player.setSpeed(66);
//        player.setExplosivePower(65);
//        player.setUnwaveringBalance(68);
//        player.setPhysicalContact(93);
//        player.setJump(79);
//        player.setGoalkeeping(40);
//        player.setGkCatch(40);
//        player.setClearing(40);
//        player.setReflexes(40);
//        player.setCoverage(40);
//        player.setStamina(82);
//        player.setWeakFootUsage(2);
//        player.setWeakFootAccuracy(2);
//        player.setForm(6);
//        player.setInjuryResistance(3);
//        player.setOverallRating(83);
//        player.setPlayingStyle("Build Up");
//        Set<String> playerSkills = new HashSet<>();
//        playerSkills.add("Heading");
//        playerSkills.add("Weighted Pass");
//        playerSkills.add("Man Marking");
//        playerSkills.add("Fighting Spirit");
//        player.setPlayerSkills(playerSkills);
//        player.setComPlayingStyles(Collections.emptySet());
//        player.setPesDbLastCheck(LocalDateTime.now());
    }

    private void saveAllStatistics(Player player){
        Set<CompetitionStatistic> competitionStatistics = new HashSet<>();
        CompetitionStatistic cs1 = new CompetitionStatistic();
        cs1.setCompetitionName("FIFA World Cup");
        cs1.setAppearances("6");
        cs1.setGoals("1");
        cs1.setAssists("1");
        cs1.setYellowCards("2");
        cs1.setRedCards("0");
        cs1.setMinutesPlayed("645");
        cs1.setPlayer(player);
        competitionStatistics.add(cs1);

        CompetitionStatistic cs2 = new CompetitionStatistic();
        cs2.setCompetitionName("Premier League");
        cs2.setAppearances("31");
        cs2.setGoals("3");
        cs2.setAssists("0");
        cs2.setYellowCards("6");
        cs2.setRedCards("1");
        cs2.setMinutesPlayed("2599");
        cs2.setPlayer(player);
        competitionStatistics.add(cs2);

        player.setCompetitionStatistics(competitionStatistics);
        player.setStatisticLastCheck(LocalDateTime.now());
    }

    private void saveGameStatistics(Player player){
        Set<GameStatistic> gameStatistics = new HashSet<>();
        GameStatistic gs1 = new GameStatistic();
        gs1.setDateOfGame(LocalDate.of(2019, 3, 16));
        gs1.setPlayerTeam("Burnley");
        gs1.setOpponentTeam("Leicester");
        gs1.setHomeAwayFlag("H");
        gs1.setResult("1:2");
        gs1.setPosition("AMF");
        gs1.setGoals("0");
        gs1.setAssists("0");
        gs1.setYellowCard(false);
        gs1.setRedCard(true);
        gs1.setMinutesPlayed("4");
        gs1.setNotPlayedReason(null);
        gs1.setPlayer(player);
        gameStatistics.add(gs1);

        GameStatistic gs2 = new GameStatistic();
        gs2.setDateOfGame(LocalDate.of(2019, 3, 22));
        gs2.setPlayerTeam("England");
        gs2.setOpponentTeam("Czech Republic");
        gs2.setHomeAwayFlag("A");
        gs2.setResult("5:0");
        gs2.setPosition("AMF");
        gs2.setGoals("0");
        gs2.setAssists("0");
        gs2.setYellowCard(false);
        gs2.setRedCard(false);
        gs2.setMinutesPlayed("90");
        gs2.setNotPlayedReason(null);
        gs2.setPlayer(player);
        gameStatistics.add(gs2);

        player.setGameStatistics(gameStatistics);
        player.setStatisticLastCheck(LocalDateTime.now());
    }

    private void saveCharacteristic(Player player){
        Set<String> strengths = new HashSet<>();
        strengths.add("Concentration");
        player.setStrengths(strengths);
        Set<String> stylesOfPlay = new HashSet<>();
        stylesOfPlay.add("Indirect set-piece threat");
        stylesOfPlay.add("Likes to dribble");
        stylesOfPlay.add("Likes to play long balls");
        stylesOfPlay.add("Plays the ball off the ground often");
        player.setStylesOfPlay(stylesOfPlay);
        player.setWeaknesses(Collections.EMPTY_SET);
    }

    private void savePsmlInfo(Player player){
        player.setPsmlTeam("Atomic Ants");
        player.setPsmlValue(BigDecimal.valueOf(15000000));
        PsmlTransfer psmlTransfer = new PsmlTransfer();
        psmlTransfer.setDateOfTransfer(LocalDate.now());
        psmlTransfer.setFromTeam("Free Agent");
        psmlTransfer.setToTeam("Atomic Ants");
        psmlTransfer.setPlayer(player);
        player.setPsmlTransfers((Set.of(psmlTransfer)));
        player.setPsmlLastCheck(LocalDateTime.now());
    }
}
