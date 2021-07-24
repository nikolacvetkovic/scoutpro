package xyz.riocode.scoutpro.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.exception.AppUserNotFoundException;
import xyz.riocode.scoutpro.exception.DuplicateAppUserUsernameException;
import xyz.riocode.scoutpro.model.AppUser;
import xyz.riocode.scoutpro.repository.AppUserRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Component
@Transactional
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
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
    public Set<AppUser> getAppUsersByUsername(String username) {
        return appUserRepository.findAppUsersByUsername(username);
    }

    @Override
    public AppUser getByUsername(String username) {
        return appUserRepository.findByUsername(username).orElseThrow(AppUserNotFoundException::new);
    }

    @Override
    public AppUser changePassword(AppUser appUser) {
        AppUser user = appUserRepository.findByUsername(appUser.getUsername())
            .orElseThrow(AppUserNotFoundException::new);

        //todo implement password encoding
        user.setPassword(appUser.getPassword());

        return appUserRepository.save(user);
    }

    @Override
    public void disableById(Long appUserId) {
        AppUser foundAppUser = appUserRepository.findById(appUserId).orElseThrow(AppUserNotFoundException::new);
        foundAppUser.setEnabled(false);
        appUserRepository.save(foundAppUser);
    }
}
