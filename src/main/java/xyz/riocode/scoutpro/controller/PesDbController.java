package xyz.riocode.scoutpro.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.riocode.scoutpro.dto.PlayerDashboardDTO;

@Log4j2
@RequestMapping("/pesdb/scrape")
@Controller
public class PesDbController {

    @GetMapping("/{playerId}")
    public PlayerDashboardDTO scrape(@PathVariable Long playerId){
        return null;
    }

}
