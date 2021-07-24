package xyz.riocode.scoutpro.converter;

import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.dto.TransferDTO;
import xyz.riocode.scoutpro.model.Transfer;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TransferConverter {

    public List<TransferDTO> transfersToTransferDTOs(Set<Transfer> transfers){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy").withLocale(Locale.US);
        return transfers.stream().map(t -> {
            TransferDTO transferDTO = new TransferDTO();
            transferDTO.setDateOfTransfer(t.getDateOfTransfer().format(dateFormatter));
            transferDTO.setFromTeam(t.getFromTeam());
            transferDTO.setToTeam(t.getToTeam());
            transferDTO.setMarketValue(t.getMarketValue());
            transferDTO.setTransferFee(t.getTransferFee());
            return transferDTO;
        }).collect(Collectors.toList());
    }

}
