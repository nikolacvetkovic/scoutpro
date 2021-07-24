package xyz.riocode.scoutpro.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransferDTO {

    private String fromTeam;
    private String toTeam;
    private String dateOfTransfer;
    private String marketValue;
    private String transferFee;

}
