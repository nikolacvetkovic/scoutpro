package xyz.riocode.scoutpro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.riocode.scoutpro.security.privilege.PlayerNewsReadPrivilege;
import xyz.riocode.scoutpro.security.privilege.PlayerTransferReadPrivilege;

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
    @PlayerTransferReadPrivilege
    @GetMapping("/transfers")
    public String showTransfers(){
        return "player/transfers";
    }

    @PlayerNewsReadPrivilege
    @GetMapping("/news")
    public String showNews(){
        return "player/news";
    }

}
