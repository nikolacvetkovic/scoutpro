package xyz.riocode.scoutpro.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransferDTO {

    private String fromTeam;
    private String toTeam;
    private String dateOfTransfer;
    private String marketValue;
    private String transferFee;
    private PlayerDTO player;

}
