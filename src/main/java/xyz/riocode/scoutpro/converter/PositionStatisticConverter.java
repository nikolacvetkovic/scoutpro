package xyz.riocode.scoutpro.converter;

import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.dto.PositionStatisticDTO;
import xyz.riocode.scoutpro.model.PositionStatistic;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PositionStatisticConverter {

    public List<PositionStatisticDTO> positionStatisticsToPositionStatisticDTOs(Set<PositionStatistic> positionStatistics){
        return positionStatistics.stream().map(positionStatistic -> {
            PositionStatisticDTO positionStatisticDTO = new PositionStatisticDTO();
            positionStatisticDTO.setPosition(positionStatistic.getPosition());
            positionStatisticDTO.setApps(positionStatistic.getApps());
            positionStatisticDTO.setGoals(positionStatistic.getGoals());
            positionStatisticDTO.setAssists(positionStatistic.getAssists());
            positionStatisticDTO.setRating(positionStatistic.getRating().toString());
            return positionStatisticDTO;
        }).collect(Collectors.toList());
    }
}
