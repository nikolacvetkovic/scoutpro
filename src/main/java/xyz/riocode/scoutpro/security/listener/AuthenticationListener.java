package xyz.riocode.scoutpro.security.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.model.security.AppUser;

@Slf4j
@Component
public class AuthenticationListener {

    @EventListener
    public void listenSuccessAuth(AuthenticationSuccessEvent authenticationSuccessEvent) {
        String username = null;
        String ipAddress = null;

        if (authenticationSuccessEvent.getSource() instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authenticationSuccessEvent.getSource();
            if (token.getPrincipal() instanceof AppUser) {
                AppUser appUser = (AppUser) token.getPrincipal();
                username = appUser.getUsername();
            }
            if (token.getDetails() instanceof WebAuthenticationDetails) {
                WebAuthenticationDetails authenticationDetails = (WebAuthenticationDetails) token.getDetails();
                ipAddress = authenticationDetails.getRemoteAddress();
            }
            log.info("Login successful with username: {}, ipAddress: {}", username, ipAddress);
        }
    }

    @EventListener
    public void listenFailedAuth(AuthenticationFailureBadCredentialsEvent authenticationFailureBadCredentialsEvent) {
        String username = null;
        String password = null;
        String ipAddress = null;

        if (authenticationFailureBadCredentialsEvent.getSource() instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authenticationFailureBadCredentialsEvent.getSource();
            if (token.getPrincipal() instanceof String) {
                username = (String) token.getPrincipal();
            }
            if (token.getCredentials() instanceof String) {
                password = (String) token.getCredentials();
            }
            if (token.getDetails() instanceof WebAuthenticationDetails) {
                ipAddress = ((WebAuthenticationDetails) token.getDetails()).getRemoteAddress();
            }
            log.warn("Login failure with username: {}, password: {}, ipAddress: {}", username, password, ipAddress);
        }
    }
}
