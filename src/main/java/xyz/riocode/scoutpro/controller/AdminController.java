package xyz.riocode.scoutpro.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.riocode.scoutpro.scrape.service.ScrapeRegExpressionService;
import xyz.riocode.scoutpro.service.AppUserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AppUserService userService;
    private final ScrapeRegExpressionService scrapeRegExpService;

    public AdminController(AppUserService userService, ScrapeRegExpressionService scrapeRegExpService) {
        this.userService = userService;
        this.scrapeRegExpService = scrapeRegExpService;
    }


    @GetMapping("/dashboard")
    public String showAdminDashboard(){

        return "admin/dashboard";
    }

    @GetMapping("/user")
    public String showUserForm(){

        return "admin/userForm";
    }

    @PostMapping("/user")
    public String saveOrUpdateUser(){

        return "redirect:/admin/user";
    }

    @GetMapping("/regex")
    public String showRegex(){

        return "admin/regex";
    }

    @PostMapping("regex")
    public String updateRegex(){

        return "redirect:/admin/regex";
    }

    @GetMapping("/{tmUrl}/tm")
    public ResponseEntity checkTMScrape(@PathVariable String tmUrl){

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{wsUrl}/ws")
    public ResponseEntity checkWSScrape(@PathVariable String wsUrl){

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{pesDbUrl}/pesdb")
    public ResponseEntity checkPesDbScrape(@PathVariable String pesDbUrl){

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{psmlUrl}/psml")
    public ResponseEntity checkPsmlScrape(@PathVariable String psmlUrl){

        return new ResponseEntity(HttpStatus.OK);
    }
}
