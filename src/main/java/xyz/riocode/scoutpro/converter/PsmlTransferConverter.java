package xyz.riocode.scoutpro.converter;

import xyz.riocode.scoutpro.dto.PsmlTransferDTO;
import xyz.riocode.scoutpro.model.PsmlTransfer;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class PsmlTransferConverter {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy").withLocale(Locale.US);

    public static List<PsmlTransferDTO> psmlTransfersToPsmlTransferDTOs(Collection<PsmlTransfer> psmlTransfers){
        return psmlTransfers.stream()
                .map(PsmlTransferConverter::psmlTransferToPsmlTransferDTO)
                .collect(Collectors.toList());
    }

    public static PsmlTransferDTO psmlTransferToPsmlTransferDTO(PsmlTransfer psmlTransfer) {
        return PsmlTransferDTO.builder()
                .dateOfTransfer(psmlTransfer.getDateOfTransfer()!=null?
                        psmlTransfer.getDateOfTransfer().format(dateTimeFormatter):" - ")
                .fromTeam(psmlTransfer.getFromTeam())
                .toTeam(psmlTransfer.getToTeam())
                .transferFee(psmlTransfer.getTransferFee()!=null?psmlTransfer.getTransferFee():" - ")
                .player(PlayerConverter.playerToPlayerDTO(psmlTransfer.getPlayer()))
                .build();
    }
}
