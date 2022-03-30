package xyz.riocode.scoutpro.service.security;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.exception.AppUserNotFoundException;
import xyz.riocode.scoutpro.exception.DuplicateAppUserUsernameException;
import xyz.riocode.scoutpro.model.security.AppUser;
import xyz.riocode.scoutpro.repository.AppUserRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Component
@Transactional
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserServiceImpl(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser create(AppUser appUser) {
        appUserRepository.findByUsername(appUser.getUsername())
                .ifPresent(u -> {
                    throw new DuplicateAppUserUsernameException();
                });

        return appUserRepository.save(appUser);
    }

    @Override
    public Set<AppUser> getAllPaging(int page) {
        return new HashSet<>(appUserRepository.findAll(PageRequest.of(page, 25, Sort.by("username").ascending())).getContent());
    }

    @Override
    public AppUser getByUsername(String username) {
        return appUserRepository.findByUsername(username).orElseThrow(AppUserNotFoundException::new);
    }

    @Override
    public AppUser changePassword(AppUser appUser) {
        AppUser user = appUserRepository.findByUsername(appUser.getUsername())
            .orElseThrow(AppUserNotFoundException::new);

        user.setPassword(passwordEncoder.encode(appUser.getPassword()));

        return appUserRepository.save(user);
    }

    @Override
    public void disableById(Long appUserId) {
        AppUser foundAppUser = appUserRepository.findById(appUserId).orElseThrow(AppUserNotFoundException::new);
        foundAppUser.setEnabled(false);
        appUserRepository.save(foundAppUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username).orElseThrow(() -> {
                return new UsernameNotFoundException("Username " + username + " not found.");
        });
    }
}
