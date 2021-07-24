package xyz.riocode.scoutpro.converter;

import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.dto.GameStatisticDTO;
import xyz.riocode.scoutpro.model.GameStatistic;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GameStatisticConverter {

    public List<GameStatisticDTO> gameStatisticsToGameStatisticDTOs(Set<GameStatistic> gameStatistics) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy").withLocale(Locale.US);
        return gameStatistics.stream().map(gameStatistic -> {
            GameStatisticDTO gameStatisticDTO = new GameStatisticDTO();
            gameStatisticDTO.setDateOfGame(gameStatistic.getDateOfGame().format(dateFormatter));
            gameStatisticDTO.setCompetition(gameStatistic.getCompetition());
            gameStatisticDTO.setTeam1(gameStatistic.getTeam1());
            gameStatisticDTO.setTeam2(gameStatistic.getTeam2());
            gameStatisticDTO.setResult(gameStatistic.getResult());
            gameStatisticDTO.setMinutesPlayed(gameStatistic.getMinutesPlayed());
            gameStatisticDTO.setGoals(gameStatistic.getGoals());
            gameStatisticDTO.setAssists(gameStatistic.getAssists());
            gameStatisticDTO.setYellowCard(gameStatistic.isYellowCard());
            gameStatisticDTO.setRedCard(gameStatistic.isRedCard());
            gameStatisticDTO.setManOfTheMatch(gameStatistic.isManOfTheMatch());
            gameStatisticDTO.setRating(gameStatistic.getRating().toString());
            return gameStatisticDTO;
        }).collect(Collectors.toList());
    }
}
