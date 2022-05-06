package xyz.riocode.scoutpro.converter;

import xyz.riocode.scoutpro.dto.CompetitionStatisticDTO;
import xyz.riocode.scoutpro.model.CompetitionStatistic;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CompetitionStatisticConverter {

    public static List<CompetitionStatisticDTO> competitionStatisticsToCompetitionStatisticDTOs(Set<CompetitionStatistic> competitionStatistics) {
        return competitionStatistics.stream()
                .map(CompetitionStatisticConverter::competitionStatisticToCompetitionStatisticsDTO)
                .collect(Collectors.toList());
    }

    public static CompetitionStatisticDTO competitionStatisticToCompetitionStatisticsDTO(CompetitionStatistic cs) {
        return CompetitionStatisticDTO.builder()
                .competitionName(cs.getCompetitionName())
                .appearances(cs.getAppearances())
                .goals(cs.getGoals())
                .assists(cs.getAssists())
                .yellowCards(cs.getYellowCards())
                .redCards(cs.getRedCards())
                .minutesPlayed(cs.getMinutesPlayed())
                .build();
    }
}
