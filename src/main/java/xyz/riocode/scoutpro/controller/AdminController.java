package xyz.riocode.scoutpro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.riocode.scoutpro.scrape.service.ScrapeFieldService;
import xyz.riocode.scoutpro.service.security.AppUserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AppUserService userService;
    private final ScrapeFieldService scrapeFieldService;

    public AdminController(AppUserService userService, ScrapeFieldService scrapeFieldService) {
        this.userService = userService;
        this.scrapeFieldService = scrapeFieldService;
    }

    @GetMapping("/dashboard")
    public String showAdminDashboard(){
        return "admin/dashboard";
    }

    @GetMapping("/scrapefields")
    public String showScrapeFields(){

        return "admin/scrapeFields";
    }

    @GetMapping("/jobs")
    public String showJobDashboard() {
        return "admin/jobDashboard";
    }

    @GetMapping("/user")
    public String showUserForm(){

        return "admin/userForm";
    }

    @PostMapping("/user")
    public String saveOrUpdateUser(){

        return "redirect:/admin/user";
    }
}
