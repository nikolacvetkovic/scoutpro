package xyz.riocode.scoutpro.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PsmlTransferDTO {

    private String fromTeam;
    private String toTeam;
    private String dateOfTransfer;
    private String transferFee;
    private PlayerDTO player;

}
