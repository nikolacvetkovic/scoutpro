package xyz.riocode.scoutpro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.riocode.scoutpro.converter.ScrapeSiteConverter;
import xyz.riocode.scoutpro.dto.ScrapeSiteDTO;
import xyz.riocode.scoutpro.scrape.service.ScrapeFieldService;
import xyz.riocode.scoutpro.scrape.service.ScrapeSiteService;
import xyz.riocode.scoutpro.security.privilege.AdminDashboardPrivilege;
import xyz.riocode.scoutpro.security.privilege.JobReadPrivilege;
import xyz.riocode.scoutpro.security.privilege.ScrapeSiteReadPrivilege;
import xyz.riocode.scoutpro.service.security.AppUserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AppUserService userService;
    private final ScrapeSiteService scrapeSiteService;
    private final ScrapeFieldService scrapeFieldService;

    private final ScrapeSiteConverter scrapeSiteConverter;

    public AdminController(AppUserService userService, ScrapeSiteService scrapeSiteService, ScrapeFieldService scrapeFieldService, ScrapeSiteConverter scrapeSiteConverter) {
        this.userService = userService;
        this.scrapeSiteService = scrapeSiteService;
        this.scrapeFieldService = scrapeFieldService;
        this.scrapeSiteConverter = scrapeSiteConverter;
    }

    @AdminDashboardPrivilege
    @GetMapping("/dashboard")
    public String showAdminDashboard(){
        return "admin/dashboard";
    }
    @ScrapeSiteReadPrivilege
    @GetMapping("/scrapefields")
    public String showScrapeFields(ModelMap modelMap){
        List<ScrapeSiteDTO> scrapeSiteDTOS = scrapeSiteConverter.scrapeSiteToScrapeSiteDTO(scrapeSiteService.getAll());
        modelMap.addAttribute("scrapeSites", scrapeSiteDTOS);
        return "admin/scrapeFields";
    }
    @JobReadPrivilege
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
