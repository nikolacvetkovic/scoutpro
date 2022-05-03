package xyz.riocode.scoutpro.converter;

import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.dto.CompetitionStatisticDTO;
import xyz.riocode.scoutpro.model.CompetitionStatistic;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CompetitionStatisticConverter {

    public List<CompetitionStatisticDTO> competitionStatisticsToCompetitionStatisticDTOs(Set<CompetitionStatistic> competitionStatistics) {
        return competitionStatistics.stream()
                .map(this::competitionStatisticToCompetitionStatisticsDTO)
                .collect(Collectors.toList());
    }

    public CompetitionStatisticDTO competitionStatisticToCompetitionStatisticsDTO(CompetitionStatistic cs) {
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
