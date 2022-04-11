package xyz.riocode.scoutpro.converter;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.riocode.scoutpro.dto.PsmlTransferDTO;
import xyz.riocode.scoutpro.model.PsmlTransfer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class PsmlTransferConverterTest {

    Set<PsmlTransfer> psmlTransfers;

    @BeforeEach
    void setUp() {
        psmlTransfers = new HashSet<>();

        PsmlTransfer psmlTransfer1 = new PsmlTransfer();
        psmlTransfer1.setFromTeam("Atomic Ants FC");
        psmlTransfer1.setToTeam("Hull City");
        psmlTransfer1.setTransferFee("75000000");
        psmlTransfer1.setDateOfTransfer(LocalDate.of(2014, 7, 30));
        psmlTransfers.add(psmlTransfer1);

        PsmlTransfer psmlTransfer2 = new PsmlTransfer();
        psmlTransfer2.setFromTeam("Hull City");
        psmlTransfer2.setToTeam("Vukovi");
        psmlTransfer2.setTransferFee("80000000");
        psmlTransfer2.setDateOfTransfer(LocalDate.of(2017, 7, 10));
        psmlTransfers.add(psmlTransfer2);

        PsmlTransfer psmlTransfer3 = new PsmlTransfer();
        psmlTransfer3.setFromTeam("Vukovi");
        psmlTransfer3.setToTeam("Top Hit");
        psmlTransfer3.setTransferFee("100000000");
        psmlTransfer3.setDateOfTransfer(LocalDate.of(2019, 8, 8));
        psmlTransfers.add(psmlTransfer3);


    }

    @Test
    void psmlTransfersToPsmlTransferDTOs() {

        PsmlTransferConverter transferConverter = new PsmlTransferConverter();
        List<PsmlTransferDTO> transferDTOS = transferConverter.psmlTransfersToPsmlTransferDTOs(psmlTransfers);

        Assertions.assertNotNull(transferDTOS);
        MatcherAssert.assertThat(transferDTOS, Matchers.hasSize(3));

    }

    @Test
    void psmlTransfersToPsmlTransferDTOsEmptyList() {
        Set<PsmlTransfer> emptyPsmlTransfers = new HashSet<>();

        PsmlTransferConverter transferConverter = new PsmlTransferConverter();
        List<PsmlTransferDTO> transferDTOS = transferConverter.psmlTransfersToPsmlTransferDTOs(emptyPsmlTransfers);

        Assertions.assertNotNull(transferDTOS);
        MatcherAssert.assertThat(transferDTOS, Matchers.hasSize(0));

    }
}