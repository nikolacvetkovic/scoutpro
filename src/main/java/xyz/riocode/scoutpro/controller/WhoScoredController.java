package xyz.riocode.scoutpro.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.riocode.scoutpro.dto.PlayerCompleteDTO;
import xyz.riocode.scoutpro.dto.PlayerDashboardDTO;

@Log4j2
@RequestMapping("/ws/scrape")
@Controller
public class WhoScoredController {

    @GetMapping("/light/{playerId}")
    public PlayerDashboardDTO lightScrape(@PathVariable Long playerId){
        return null;
    }

    @GetMapping("/full/{playerId}")
    public PlayerCompleteDTO fullScrape(@PathVariable Long playerId){
        return null;
    }

}
