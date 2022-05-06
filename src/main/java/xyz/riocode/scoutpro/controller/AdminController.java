package xyz.riocode.scoutpro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.riocode.scoutpro.converter.JobConverter;
import xyz.riocode.scoutpro.converter.JobExecutionHistoryConverter;
import xyz.riocode.scoutpro.converter.ScrapeErrorConverter;
import xyz.riocode.scoutpro.converter.ScrapeSiteConverter;
import xyz.riocode.scoutpro.dto.JobDTO;
import xyz.riocode.scoutpro.dto.JobExecutionHistoryDTO;
import xyz.riocode.scoutpro.dto.ScrapeErrorDTO;
import xyz.riocode.scoutpro.dto.ScrapeSiteDTO;
import xyz.riocode.scoutpro.scheduler.service.JobExecutionHistoryService;
import xyz.riocode.scoutpro.scheduler.service.JobService;
import xyz.riocode.scoutpro.scrape.service.ScrapeErrorService;
import xyz.riocode.scoutpro.scrape.service.ScrapeSiteService;
import xyz.riocode.scoutpro.security.privilege.AdminDashboardPrivilege;
import xyz.riocode.scoutpro.security.privilege.JobReadPrivilege;
import xyz.riocode.scoutpro.security.privilege.ScrapeSiteReadPrivilege;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ScrapeSiteService scrapeSiteService;
    private final ScrapeErrorService scrapeErrorService;
    private final JobExecutionHistoryService jobExecutionHistoryService;
    private final JobService jobService;

    public AdminController(ScrapeSiteService scrapeSiteService, ScrapeErrorService scrapeErrorService,
                           JobExecutionHistoryService jobExecutionHistoryService, JobService jobService) {
        this.scrapeSiteService = scrapeSiteService;
        this.scrapeErrorService = scrapeErrorService;
        this.jobExecutionHistoryService = jobExecutionHistoryService;
        this.jobService = jobService;
    }

    @AdminDashboardPrivilege
    @GetMapping("/dashboard")
    public String showAdminDashboard(ModelMap modelMap){
        List<ScrapeSiteDTO> scrapeSiteDTOs = ScrapeSiteConverter.scrapeSitesToScrapeSiteDTOs(
                                                                                    scrapeSiteService.getAll());
        List<ScrapeErrorDTO> scrapeErrorsDTOs = ScrapeErrorConverter.scrapeErrorToScrapeErrorDTO(
                                                                                    scrapeErrorService.getAll());
        List<JobExecutionHistoryDTO> jobExecutionHistoryDTOs = JobExecutionHistoryConverter.jobExecutionToJobExecutionDTO(
                                                                                    jobExecutionHistoryService.getAll());

        modelMap.addAttribute("scrapeSites", scrapeSiteDTOs);
        modelMap.addAttribute("scrapeErrors", scrapeErrorsDTOs);
        modelMap.addAttribute("jobExecutions", jobExecutionHistoryDTOs);
        return "admin/dashboard";
    }

    @ScrapeSiteReadPrivilege
    @GetMapping("/scrapefields")
    public String showScrapeFields(ModelMap modelMap){
        List<ScrapeSiteDTO> scrapeSiteDTOs = ScrapeSiteConverter.scrapeSitesToScrapeSiteDTOs(scrapeSiteService.getAll());
        modelMap.addAttribute("scrapeSites", scrapeSiteDTOs);
        return "admin/scrapeFields";
    }

    @JobReadPrivilege
    @GetMapping("/jobs")
    public String showJobDashboard(ModelMap modelMap) {
        List<JobDTO> jobDTOs = JobConverter.jobsToJobDTOs(jobService.getAll());
        modelMap.addAttribute("jobs", jobDTOs);
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
