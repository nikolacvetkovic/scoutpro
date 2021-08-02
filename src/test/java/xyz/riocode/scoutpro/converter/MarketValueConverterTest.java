package xyz.riocode.scoutpro.converter;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.riocode.scoutpro.dto.MarketValueDTO;
import xyz.riocode.scoutpro.model.MarketValue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MarketValueConverterTest {

    Set<MarketValue> marketValues;

    @BeforeEach
    void setUp() {
        marketValues = new HashSet<>();
        MarketValue marketValue1 = new MarketValue();
        marketValue1.setClubTeam("Everton FC");
        marketValue1.setDatePoint(LocalDate.of(2015, 7, 1));
        marketValue1.setWorth(new BigDecimal(28000000));
        marketValues.add(marketValue1);

        MarketValue marketValue2 = new MarketValue();
        marketValue2.setClubTeam("Manchester United");
        marketValue2.setDatePoint(LocalDate.of(2018, 1, 2));
        marketValue2.setWorth(new BigDecimal(85000000));
        marketValues.add(marketValue2);

        MarketValue marketValue3 = new MarketValue();
        marketValue3.setClubTeam("Inter Milan");
        marketValue3.setDatePoint(LocalDate.of(2021, 5, 26));
        marketValue3.setWorth(new BigDecimal(100000000));
        marketValues.add(marketValue3);
    }

    @Test
    void marketValuesToMarketValueDTOs() {

        MarketValueConverter marketValueConverter = new MarketValueConverter();
        List<MarketValueDTO> marketValuesDTOS = marketValueConverter.marketValuesToMarketValueDTOs(marketValues);

        assertNotNull(marketValuesDTOS);
        assertThat(marketValuesDTOS, Matchers.hasSize(3));

    }

    @Test
    void marketValuesToMarketValuesDTOsEmptyList() {
        Set<MarketValue> emptyMarketValues = new HashSet<>();

        MarketValueConverter marketValueConverter = new MarketValueConverter();
        List<MarketValueDTO> marketValuesDTOS = marketValueConverter.marketValuesToMarketValueDTOs(emptyMarketValues);

        assertNotNull(marketValuesDTOS);
        assertThat(marketValuesDTOS, Matchers.hasSize(0));

    }
}