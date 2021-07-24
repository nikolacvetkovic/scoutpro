package xyz.riocode.scoutpro.converter;

import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.dto.PsmlTransferDTO;
import xyz.riocode.scoutpro.model.PsmlTransfer;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PsmlTransferConverter {

    public List<PsmlTransferDTO> psmlTransfersToPsmlTransferDTOs(Set<PsmlTransfer> psmlTransfers){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").withLocale(Locale.US);
        return psmlTransfers.stream().map(psmlTransfer -> {
            PsmlTransferDTO psmlTransferDTO = new PsmlTransferDTO();
            psmlTransferDTO.setDateOfTransfer(psmlTransfer.getDateOfTransfer().format(dateTimeFormatter));
            psmlTransferDTO.setFromTeam(psmlTransfer.getFromTeam());
            psmlTransferDTO.setToTeam(psmlTransfer.getToTeam());
            psmlTransferDTO.setTransferFee(psmlTransfer.getTransferFee().toString());
            return psmlTransferDTO;
        }).collect(Collectors.toList());
    }
}
