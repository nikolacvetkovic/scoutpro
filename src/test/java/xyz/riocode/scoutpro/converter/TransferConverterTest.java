package xyz.riocode.scoutpro.converter;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.riocode.scoutpro.dto.TransferDTO;
import xyz.riocode.scoutpro.model.Transfer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TransferConverterTest {

    Set<Transfer> transfers;

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
    }

    @Test
    void transfersToTransferDTOs() {

        TransferConverter transferConverter = new TransferConverter();
        List<TransferDTO> transferDTOS = transferConverter.transfersToTransferDTOs(transfers);

        assertNotNull(transferDTOS);
        MatcherAssert.assertThat(transferDTOS, Matchers.hasSize(3));

    }

    @Test
    void transfersToTransferDTOsEmptyList() {
        Set<Transfer> emptyTransfers = new HashSet<>();

        TransferConverter transferConverter = new TransferConverter();
        List<TransferDTO> transferDTOS = transferConverter.transfersToTransferDTOs(emptyTransfers);

        assertNotNull(transferDTOS);
        MatcherAssert.assertThat(transferDTOS, Matchers.hasSize(0));

    }
}