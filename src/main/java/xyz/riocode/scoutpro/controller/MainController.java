package xyz.riocode.scoutpro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.riocode.scoutpro.security.privilege.PlayerNewsReadPrivilege;

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

    @PlayerNewsReadPrivilege
    @GetMapping("/news")
    public String showNews(){
        return "player/news";
    }

}
