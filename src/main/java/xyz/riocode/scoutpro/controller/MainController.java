package xyz.riocode.scoutpro.controller;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.riocode.scoutpro.security.privilege.PlayerNewsReadPrivilege;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(required = false) String error,
                                HttpServletRequest request,
                                ModelMap modelMap) {
        if (error != null) {
            HttpSession session = request.getSession(false);
            String errorMessage = null;
            if (session != null) {
                AuthenticationException exception =
                        (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
                if (exception != null) {
                    errorMessage = exception.getMessage();
                }
            }
            modelMap.addAttribute("errorMessage", errorMessage);
        }
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
