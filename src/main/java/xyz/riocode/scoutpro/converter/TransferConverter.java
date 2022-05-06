package xyz.riocode.scoutpro.converter;

import xyz.riocode.scoutpro.dto.TransferDTO;
import xyz.riocode.scoutpro.model.Transfer;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class TransferConverter {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy").withLocale(Locale.US);

    public static List<TransferDTO> transfersToTransferDTOs(Set<Transfer> transfers){
        return transfers.stream()
                .map(TransferConverter::transferToTransferDTO)
                .collect(Collectors.toList());
    }

    public static TransferDTO transferToTransferDTO(Transfer transfer) {
        return TransferDTO.builder()
                .fromTeam(transfer.getFromTeam())
                .toTeam(transfer.getToTeam())
                .dateOfTransfer(transfer.getDateOfTransfer().format(dateFormatter))
                .marketValue(transfer.getMarketValue())
                .transferFee(transfer.getTransferFee())
                .player(PlayerConverter.playerToPlayerDTO(transfer.getPlayer()))
                .build();
    }

}
