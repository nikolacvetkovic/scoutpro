package xyz.riocode.scoutpro.converter;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import xyz.riocode.scoutpro.dto.PlayerDTO;
import xyz.riocode.scoutpro.dto.TransferDTO;
import xyz.riocode.scoutpro.model.Transfer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

class TransferConverterTest {

    Set<Transfer> transfers;
    PlayerDTO playerDTO;

    @BeforeEach
    void setUp() {
        transfers = new HashSet<>();

        Transfer transfer1 = new Transfer();
        transfer1.setFromTeam("Chelsea");
        transfer1.setToTeam("Everton");
        transfer1.setTransferFee("€35.36m");
        transfer1.setMarketValue("€25.00m");
        transfer1.setDateOfTransfer(LocalDate.of(2014, 7, 30));
        transfers.add(transfer1);

        Transfer transfer2 = new Transfer();
        transfer2.setFromTeam("Everton");
        transfer2.setToTeam("Man Utd");
        transfer2.setTransferFee("€84.70m");
        transfer2.setMarketValue("€50.00m");
        transfer2.setDateOfTransfer(LocalDate.of(2017, 7, 10));
        transfers.add(transfer2);

        Transfer transfer3 = new Transfer();
        transfer3.setFromTeam("Man Utd");
        transfer3.setToTeam("Inter");
        transfer3.setTransferFee("€74.00m");
        transfer3.setMarketValue("€75.00m");
        transfer3.setDateOfTransfer(LocalDate.of(2019, 8, 8));
        transfers.add(transfer3);

        playerDTO = PlayerDTO.builder()
                .name("Roberto Firmino")
                .psmlTeam("Atomic Ants FC")
                .psmlValue("72000000")
                .build();
    }

    @Test
    void transfersToTransferDTOs() {
        try (MockedStatic<PlayerConverter> mockedStatic = Mockito.mockStatic(PlayerConverter.class)){
            mockedStatic.when(() -> PlayerConverter.playerToPlayerDTO(any())).thenReturn(playerDTO);
            List<TransferDTO> transferDTOS = TransferConverter.transfersToTransferDTOs(transfers);
            assertNotNull(transferDTOS);
            MatcherAssert.assertThat(transferDTOS, Matchers.hasSize(3));
        }
    }

    @Test
    void transfersToTransferDTOsEmptyList() {
        Set<Transfer> emptyTransfers = new HashSet<>();

        List<TransferDTO> transferDTOS = TransferConverter.transfersToTransferDTOs(emptyTransfers);

        assertNotNull(transferDTOS);
        MatcherAssert.assertThat(transferDTOS, Matchers.hasSize(0));

    }
}