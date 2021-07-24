package xyz.riocode.scoutpro.converter;

import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.dto.MarketValueDTO;
import xyz.riocode.scoutpro.model.MarketValue;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MarketValueConverter {

    public List<MarketValueDTO> marketValuesToMarketValueDTOs(Set<MarketValue> marketValues){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy").withLocale(Locale.US);
        return marketValues.stream()
            .sorted(Comparator.comparing(marketValue -> marketValue.getDatePoint()))
            .map(marketValue -> {
                MarketValueDTO marketValueDTO = new MarketValueDTO();
                marketValueDTO.setDatePoint(marketValue.getDatePoint().format(dateFormatter));
                marketValueDTO.setWorth(marketValue.getWorth().toString());
                marketValueDTO.setClubTeam(marketValue.getClubTeam());
                return marketValueDTO;
        }).collect(Collectors.toList());
    }
}
