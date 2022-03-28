package xyz.riocode.scoutpro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "core/login";
    }

    @GetMapping({"/", "/dashboard"})
    public String showDashboard(){
        return "player/dashboard";
    }

    @GetMapping("/transfersandnews")
    public String showTransfersAndNews(){
        return "player/transfersAndNews";
    }

}
