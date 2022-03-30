package xyz.riocode.scoutpro.service.security;

import xyz.riocode.scoutpro.model.security.AppUser;

import java.util.Set;

public interface AppUserService {

    AppUser create(AppUser appUser);
    Set<AppUser> getAllPaging(int page);
    AppUser getByUsername(String username);
    AppUser changePassword(AppUser appUser);
    void disableById(Long userId);
}
