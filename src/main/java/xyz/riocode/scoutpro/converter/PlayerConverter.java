package xyz.riocode.scoutpro.converter;

import org.hibernate.LazyInitializationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.dto.DashboardDTO;
import xyz.riocode.scoutpro.dto.PlayerCompleteDTO;
import xyz.riocode.scoutpro.dto.PlayerDashboardDTO;
import xyz.riocode.scoutpro.dto.PlayerFormDTO;
import xyz.riocode.scoutpro.model.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PlayerConverter {

    private final TransferConverter transferConverter;
    private final MarketValueConverter marketValueConverter;
    private final CompetitionStatisticConverter competitionStatisticConverter;
    private final PositionStatisticConverter positionStatisticConverter;
    private final GameStatisticConverter gameStatisticConverter;
    private final PsmlTransferConverter psmlTransferConverter;
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").withLocale(Locale.US);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy").withLocale(Locale.US);

    public PlayerConverter(TransferConverter transferConverter, MarketValueConverter marketValueConverter,
                           CompetitionStatisticConverter competitionStatisticConverter, PositionStatisticConverter positionStatisticConverter,
                           GameStatisticConverter gameStatisticConverter, PsmlTransferConverter psmlTransferConverter) {
        this.transferConverter = transferConverter;
        this.marketValueConverter = marketValueConverter;
        this.competitionStatisticConverter = competitionStatisticConverter;
        this.positionStatisticConverter = positionStatisticConverter;
        this.gameStatisticConverter = gameStatisticConverter;
        this.psmlTransferConverter = psmlTransferConverter;
    }

    public PlayerFormDTO playerToPlayerFormDTO(Player player, String username) {
        PlayerFormDTO playerFormDTO = new PlayerFormDTO();
        playerFormDTO.setId(player.getId().toString());
        playerFormDTO.setPlayerName(player.getPlayerName());
        playerFormDTO.setMyPlayer(player.getUsers().stream()
                .filter(appUserPlayer -> appUserPlayer.getAppUser().getUsername().equals(username))
                .map(AppUserPlayer::isMyPlayer)
                .findFirst()
                .get());
        playerFormDTO.setTransfermarktUrl(player.getTransfermarktUrl());
        playerFormDTO.setWhoScoredUrl(player.getWhoScoredUrl());
        playerFormDTO.setPesDbUrl(player.getPesDbUrl());
        playerFormDTO.setPsmlUrl(player.getPsmlUrl());

        return playerFormDTO;
    }

    public Player playerFormDTOToPlayer(PlayerFormDTO playerDTO) {
        Player player = new Player();
        if (playerDTO.getId() != null && playerDTO.getId().length() != 0) player.setId(Long.valueOf(playerDTO.getId()));
        AppUserPlayerId appUserPlayerId = new AppUserPlayerId();
        AppUserPlayer appUserPlayer = new AppUserPlayer();
        appUserPlayer.setAppUserPlayerId(appUserPlayerId);
        appUserPlayer.setMyPlayer(playerDTO.isMyPlayer());
        appUserPlayer.setPlayer(player);
        player.getUsers().add(appUserPlayer);
        player.setTransfermarktUrl(playerDTO.getTransfermarktUrl());
        player.setWhoScoredUrl(playerDTO.getWhoScoredUrl());
        player.setPesDbUrl(playerDTO.getPesDbUrl());
        player.setPsmlUrl(playerDTO.getPsmlUrl());

        return player;
    }

    public DashboardDTO playersToDashboardDTO(Page<Player> playersPage, String username) {
        DashboardDTO dashboardDTO = new DashboardDTO();
        List<PlayerDashboardDTO> playerDashboardDTOS = new ArrayList<>();
        for (Player player : playersPage.getContent()) {
            PlayerDashboardDTO playerDashboardDTO = new PlayerDashboardDTO();
            playerDashboardDTO.setId(player.getId().toString());
            playerDashboardDTO.setPlayerName(player.getPlayerName());
            playerDashboardDTO.setMyPlayer(player.getUsers().stream()
                    .filter(appUserPlayer -> appUserPlayer.getAppUser().getUsername().equals(username))
                    .map(AppUserPlayer::isMyPlayer)
                    .findFirst()
                    .get());

            playerDashboardDTO.setTmCurrentValue(player.getMarketValues().stream().findFirst().get().getWorth().toString());
            playerDashboardDTO.setTmValueLastChanged(player.getMarketValues().stream().findFirst().get().getDatePoint().format(dateFormatter));
            playerDashboardDTO.setTmValueLastCheck(player.getMarketValueLastCheck().format(dateTimeFormatter));
            playerDashboardDTO.setAge(String.valueOf(player.getAge()));
            playerDashboardDTO.setNationalTeam(player.getNationalTeam());
            playerDashboardDTO.setClubTeam(player.getClubTeam());
            playerDashboardDTO.setContractUntil(player.getContractUntil());

            playerDashboardDTO.setPesDbPlayerName(player.getPesDbPlayerName());
            playerDashboardDTO.setPesDbTeamName(player.getPesDbTeamName());
            playerDashboardDTO.setFoot(player.getFoot().toString());
            playerDashboardDTO.setWeekCondition(player.getWeekCondition().toString());
            playerDashboardDTO.setPosition(player.getPrimaryPosition());
            playerDashboardDTO.setOtherStrongPositions(player.getOtherStrongPositions());
            playerDashboardDTO.setOtherWeakPositions(player.getOtherWeakPositions());
            playerDashboardDTO.setOffensiveAwareness(player.getOffensiveAwareness());
            playerDashboardDTO.setBallControl(player.getBallControl());
            playerDashboardDTO.setDribbling(player.getDribbling());
            playerDashboardDTO.setTightPossession(player.getTightPossession());
            playerDashboardDTO.setLowPass(player.getLowPass());
            playerDashboardDTO.setLoftedPass(player.getLoftedPass());
            playerDashboardDTO.setFinishing(player.getFinishing());
            playerDashboardDTO.setHeading(player.getHeading());
            playerDashboardDTO.setPlaceKicking(player.getPlaceKicking());
            playerDashboardDTO.setCurl(player.getCurl());
            playerDashboardDTO.setSpeed(player.getSpeed());
            playerDashboardDTO.setAcceleration(player.getAcceleration());
            playerDashboardDTO.setKickingPower(player.getKickingPower());
            playerDashboardDTO.setJump(player.getJump());
            playerDashboardDTO.setPhysicalContact(player.getPhysicalContact());
            playerDashboardDTO.setBalance(player.getBalance());
            playerDashboardDTO.setStamina(player.getStamina());
            playerDashboardDTO.setDefensiveAwareness(player.getDefensiveAwareness());
            playerDashboardDTO.setBallWinning(player.getBallWinning());
            playerDashboardDTO.setAggression(player.getAggression());
            playerDashboardDTO.setGkAwareness(player.getGkAwareness());
            playerDashboardDTO.setGkCatching(player.getGkCatching());
            playerDashboardDTO.setGkClearing(player.getGkClearing());
            playerDashboardDTO.setGkReflexes(player.getGkReflexes());
            playerDashboardDTO.setGkReach(player.getGkReach());
            playerDashboardDTO.setWeakFootUsage(player.getWeakFootUsage());
            playerDashboardDTO.setWeakFootAccuracy(player.getWeakFootAccuracy());
            playerDashboardDTO.setForm(player.getForm());
            playerDashboardDTO.setInjuryResistance(player.getInjuryResistance());
            playerDashboardDTO.setOverallRating(player.getOverallRating());
            playerDashboardDTO.setPlayingStyle(player.getPlayingStyle());
            playerDashboardDTO.setPlayerSkills(player.getPlayerSkills());
            playerDashboardDTO.setComPlayingStyles(player.getComPlayingStyles());
            playerDashboardDTO.setPesDbLastCheck(player.getPesDbLastCheck().format(dateTimeFormatter));

            playerDashboardDTO.setPsmlTeam(player.getPsmlTeam());
            playerDashboardDTO.setPsmlValue(player.getPsmlValue().toString());
            Optional<PsmlTransfer> optionalPsmlTransfer = player.getPsmlTransfers().stream().findFirst();
            if (optionalPsmlTransfer.isPresent()) {
                PsmlTransfer psmlTransfer = optionalPsmlTransfer.get();
                playerDashboardDTO.setPsmlLastTransferDate(psmlTransfer.getDateOfTransfer().format(dateTimeFormatter));
                playerDashboardDTO.setPsmlLastTransferFromTeam(psmlTransfer.getFromTeam());
                playerDashboardDTO.setPsmlLastTransferToTeam(psmlTransfer.getToTeam());
                playerDashboardDTO.setPsmlLastTransferFee(psmlTransfer.getTransferFee().toString());
            }
            playerDashboardDTO.setPsmlLastCheck(player.getPsmlLastCheck().format(dateTimeFormatter));

            Optional<CompetitionStatistic> competitionStatisticOptional = player.getCompetitionStatistics().stream().findFirst();
            if (competitionStatisticOptional.isPresent()) {
                CompetitionStatistic competitionStatistic = competitionStatisticOptional.get();
                playerDashboardDTO.setTotalStartedApps(String.valueOf(competitionStatistic.getStartedApps()));
                playerDashboardDTO.setTotalMins(String.valueOf(competitionStatistic.getMins()));
                playerDashboardDTO.setTotalAssists(String.valueOf(competitionStatistic.getAssists()));
                playerDashboardDTO.setTotalGoals(String.valueOf(competitionStatistic.getGoals()));
                playerDashboardDTO.setTotalAssists(String.valueOf(competitionStatistic.getAssists()));
                playerDashboardDTO.setAverageShotsPerGame(competitionStatistic.getShotsPerGame().toString());
                playerDashboardDTO.setAveragePassSuccess(competitionStatistic.getPassSuccess().toString());
                playerDashboardDTO.setAverageAerialsWon(competitionStatistic.getAerialsWon().toString());
                playerDashboardDTO.setTotalManOfTheMatch(String.valueOf(competitionStatistic.getManOfTheMatch()));
                playerDashboardDTO.setAverageRating(competitionStatistic.getRating().toString());
                playerDashboardDTO.setStatisticsLastCheck(player.getStatisticLastCheck().format(dateTimeFormatter));
            }

            playerDashboardDTOS.add(playerDashboardDTO);
        }
        dashboardDTO.setPlayers(playerDashboardDTOS);
        dashboardDTO.setCurrentPage(playersPage.getNumber());
        dashboardDTO.setTotalPages(playersPage.getTotalPages());

        return dashboardDTO;
    }

    public PlayerCompleteDTO playerToPlayerCompleteDTO(Player player, String username) {
        PlayerCompleteDTO playerCompleteDTO = new PlayerCompleteDTO();

        playerCompleteDTO.setId(player.getId().toString());
        playerCompleteDTO.setPlayerName(player.getPlayerName());
        playerCompleteDTO.setAge(String.valueOf(player.getAge()));
        playerCompleteDTO.setPosition(player.getPrimaryPosition());
        playerCompleteDTO.setOverallRating(player.getOverallRating());

        try {
            player.getUsers().size();
            playerCompleteDTO.setMyPlayer(player.getUsers().stream()
                    .filter(appUserPlayer -> appUserPlayer.getAppUser().getUsername().equals(username))
                    .map(AppUserPlayer::isMyPlayer)
                    .findFirst()
                    .orElse(false));
            playerCompleteDTO.setDateOfBirth(player.getDateOfBirth());
            playerCompleteDTO.setNationality(player.getNationality());
            playerCompleteDTO.setNationalTeam(player.getNationalTeam());
            playerCompleteDTO.setClubTeam(player.getClubTeam());
            playerCompleteDTO.setContractUntil(player.getContractUntil());
            playerCompleteDTO.setTmPosition(player.getTransfermarktPosition());

            playerCompleteDTO.setTransferDTOS(transferConverter.transfersToTransferDTOs(player.getTransfers()));
            playerCompleteDTO.setTransferLastCheck(player.getTransferLastCheck().format(dateTimeFormatter));
            playerCompleteDTO.setMarketValueDTOS(marketValueConverter.marketValuesToMarketValueDTOs(player.getMarketValues()));
            playerCompleteDTO.setMarketValueLastCheck(player.getMarketValueLastCheck().format(dateTimeFormatter));

            playerCompleteDTO.setCompetitionStatisticDTOS(competitionStatisticConverter.competitionStatisticsToCompetitionStatisticDTOs(player.getCompetitionStatistics()));
            playerCompleteDTO.setPositionStatisticDTOS(positionStatisticConverter.positionStatisticsToPositionStatisticDTOs(player.getPositionStatistics()));
            playerCompleteDTO.setGameStatisticDTOS(gameStatisticConverter.gameStatisticsToGameStatisticDTOs(player.getGameStatistics()));
            playerCompleteDTO.setStatisticLastCheck(player.getStatisticLastCheck().format(dateTimeFormatter));
            playerCompleteDTO.setStrengths(player.getStrengths());
            playerCompleteDTO.setWeaknesses(player.getWeaknesses());
            playerCompleteDTO.setStylesOfPlay(player.getStylesOfPlay());

            playerCompleteDTO.setPesDbPlayerName(player.getPesDbPlayerName());
            playerCompleteDTO.setPesDbTeamName(player.getPesDbTeamName());
            playerCompleteDTO.setFoot(player.getFoot().toString());
            playerCompleteDTO.setWeekCondition(player.getWeekCondition().toString());

            playerCompleteDTO.setOtherStrongPositions(player.getOtherStrongPositions());
            playerCompleteDTO.setOtherWeakPositions(player.getOtherWeakPositions());
            playerCompleteDTO.setOffensiveAwareness(player.getOffensiveAwareness());
            playerCompleteDTO.setBallControl(player.getBallControl());
            playerCompleteDTO.setDribbling(player.getDribbling());
            playerCompleteDTO.setTightPossession(player.getTightPossession());
            playerCompleteDTO.setLowPass(player.getLowPass());
            playerCompleteDTO.setLoftedPass(player.getLoftedPass());
            playerCompleteDTO.setFinishing(player.getFinishing());
            playerCompleteDTO.setHeading(player.getHeading());
            playerCompleteDTO.setPlaceKicking(player.getPlaceKicking());
            playerCompleteDTO.setCurl(player.getCurl());
            playerCompleteDTO.setSpeed(player.getSpeed());
            playerCompleteDTO.setAcceleration(player.getAcceleration());
            playerCompleteDTO.setKickingPower(player.getKickingPower());
            playerCompleteDTO.setJump(player.getJump());
            playerCompleteDTO.setPhysicalContact(player.getPhysicalContact());
            playerCompleteDTO.setBalance(player.getBalance());
            playerCompleteDTO.setStamina(player.getStamina());
            playerCompleteDTO.setDefensiveAwareness(player.getDefensiveAwareness());
            playerCompleteDTO.setBallWinning(player.getBallWinning());
            playerCompleteDTO.setAggression(player.getAggression());
            playerCompleteDTO.setGkAwareness(player.getGkAwareness());
            playerCompleteDTO.setGkCatching(player.getGkCatching());
            playerCompleteDTO.setGkClearing(player.getGkClearing());
            playerCompleteDTO.setGkReflexes(player.getGkReflexes());
            playerCompleteDTO.setGkReach(player.getGkReach());
            playerCompleteDTO.setWeakFootUsage(player.getWeakFootUsage());
            playerCompleteDTO.setWeakFootAccuracy(player.getWeakFootAccuracy());
            playerCompleteDTO.setForm(player.getForm());
            playerCompleteDTO.setInjuryResistance(player.getInjuryResistance());
            playerCompleteDTO.setPlayingStyle(player.getPlayingStyle());
            playerCompleteDTO.setPlayerSkills(player.getPlayerSkills());
            playerCompleteDTO.setComPlayingStyles(player.getComPlayingStyles());
            playerCompleteDTO.setPesDbLastCheck(player.getPesDbLastCheck().format(dateTimeFormatter));

            playerCompleteDTO.setPsmlTeam(player.getPsmlTeam());
            playerCompleteDTO.setPsmlValue(player.getPsmlValue().toString());
            playerCompleteDTO.setPsmlTransferDTOS(psmlTransferConverter.psmlTransfersToPsmlTransferDTOs(player.getPsmlTransfers()));
            playerCompleteDTO.setPsmlLastCheck(player.getPsmlLastCheck().format(dateTimeFormatter));
        } catch (LazyInitializationException exception){

        }

        return playerCompleteDTO;
    }

    public List<PlayerDashboardDTO> playersToAddPlayerSearchDTO(List<Player> players, String username) {
        return players.stream()
                .map(player -> {
                    PlayerDashboardDTO playerDashboardDTO = new PlayerDashboardDTO();
                    playerDashboardDTO.setId(player.getId().toString());
                    playerDashboardDTO.setPlayerName(player.getPlayerName());
                    playerDashboardDTO.setPosition(player.getPrimaryPosition());
                    playerDashboardDTO.setOverallRating(player.getOverallRating());
                    playerDashboardDTO.setTmCurrentValue(player.getMarketValues().stream().findFirst().get().getWorth().toString());
                    playerDashboardDTO.setPsmlValue(player.getPsmlValue().toString());
                    playerDashboardDTO.setPsmlTeam(player.getPsmlTeam());
                    return playerDashboardDTO;
                }).collect(Collectors.toList());
    }

    public List<PlayerDashboardDTO> playersToPlayerSearchDTO(List<Player> players, String username){
        return players.stream()
                    .map(player -> {
                        PlayerDashboardDTO playerDashboardDTO = new PlayerDashboardDTO();
                        playerDashboardDTO.setId(player.getId().toString());
                        playerDashboardDTO.setPlayerName(player.getPlayerName());
                        playerDashboardDTO.setPosition(player.getPrimaryPosition());
                        playerDashboardDTO.setOverallRating(player.getOverallRating());
                        return playerDashboardDTO;
                    }).collect(Collectors.toList());
    }
}
