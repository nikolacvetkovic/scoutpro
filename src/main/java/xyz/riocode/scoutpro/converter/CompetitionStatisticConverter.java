package xyz.riocode.scoutpro.converter;

import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.dto.CompetitionStatisticDTO;
import xyz.riocode.scoutpro.model.CompetitionStatistic;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class CompetitionStatisticConverter {

    public List<CompetitionStatisticDTO> competitionStatisticsToCompetitionStatisticDTOs(Set<CompetitionStatistic> competitionStatistics) {
        List<CompetitionStatisticDTO> competitionStatisticDTOS = new ArrayList<>();
        CompetitionStatisticDTO last = null;
        for (CompetitionStatistic cs : competitionStatistics) {
            CompetitionStatisticDTO competitionStatisticDTO = new CompetitionStatisticDTO();
            competitionStatisticDTO.setCompetition(cs.getCompetition());
            competitionStatisticDTO.setStartedApps(cs.getStartedApps());
            competitionStatisticDTO.setSubApps(cs.getSubApps());
            competitionStatisticDTO.setGoals(cs.getGoals());
            competitionStatisticDTO.setAssists(cs.getAssists());
            competitionStatisticDTO.setYellowCards(cs.getYellowCards());
            competitionStatisticDTO.setRedCards(cs.getRedCards());
            competitionStatisticDTO.setShotsPerGame(cs.getShotsPerGame().toString());
            competitionStatisticDTO.setPassSuccess(cs.getPassSuccess().toString());
            competitionStatisticDTO.setAerialsWon(cs.getAerialsWon().toString());
            competitionStatisticDTO.setManOfTheMatch(cs.getManOfTheMatch());
            competitionStatisticDTO.setRating(cs.getRating().toString());
            if (competitionStatisticDTO.getCompetition().equals("Total / Average")) {
                last = competitionStatisticDTO;
                continue;
            }
            competitionStatisticDTOS.add(competitionStatisticDTO);
        }
        if (last != null) competitionStatisticDTOS.add(last);
        return competitionStatisticDTOS;
    }
}
