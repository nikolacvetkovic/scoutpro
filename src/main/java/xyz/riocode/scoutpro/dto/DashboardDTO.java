package xyz.riocode.scoutpro.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DashboardDTO {

    private List<PlayerDashboardDTO> players;
    private int currentPage;
    private int totalPages;

}
