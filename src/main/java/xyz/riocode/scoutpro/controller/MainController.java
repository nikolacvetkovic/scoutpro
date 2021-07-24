package xyz.riocode.scoutpro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.riocode.scoutpro.dto.PlayerCompleteDTO;

@Controller
public class MainController {

    @GetMapping({"/", "/dashboard"})
    public String showDashboard(){
        return "player/dashboard";
    }

    @GetMapping("/compare")
    public String compare(ModelMap modelMap){
        modelMap.addAttribute("player1", new PlayerCompleteDTO());
        return "player/compare";
    }

    @GetMapping("/transfersandnews")
    public String showTransfersAndNews(){
        return "player/transfersAndNews";
    }

}
