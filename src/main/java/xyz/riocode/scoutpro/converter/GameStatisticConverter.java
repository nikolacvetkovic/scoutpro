package xyz.riocode.scoutpro.converter;

import xyz.riocode.scoutpro.dto.GameStatisticDTO;
import xyz.riocode.scoutpro.model.GameStatistic;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class GameStatisticConverter {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy").withLocale(Locale.US);

    public static List<GameStatisticDTO> gameStatisticsToGameStatisticDTOs(Set<GameStatistic> gameStatistics) {
        return gameStatistics.stream()
                .map(GameStatisticConverter::gameStatisticToGameStatisticDTO)
                .collect(Collectors.toList());
    }

    public static GameStatisticDTO gameStatisticToGameStatisticDTO(GameStatistic gs) {
        return GameStatisticDTO.builder()
                .dateOfGame(gs.getDateOfGame().format(dateFormatter))
                .playerTeam(gs.getPlayerTeam())
                .opponentTeam(gs.getOpponentTeam())
                .homeAwayFlag(gs.getHomeAwayFlag())
                .result(gs.getResult())
                .position(gs.getPosition())
                .goals(gs.getGoals())
                .assists(gs.getAssists())
                .yellowCard(gs.isYellowCard())
                .redCard(gs.isRedCard())
                .minutesPlayed(gs.getMinutesPlayed())
                .notPlayedReason(gs.getNotPlayedReason())
                .build();
    }
}
