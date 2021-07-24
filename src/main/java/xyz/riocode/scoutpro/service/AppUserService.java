package xyz.riocode.scoutpro.service;

import xyz.riocode.scoutpro.model.AppUser;

import java.util.Set;

public interface AppUserService {

    AppUser create(AppUser appUser);
    Set<AppUser> getAllPaging(int page);
    Set<AppUser> getAppUsersByUsername(String username);
    AppUser getByUsername(String username);
    AppUser changePassword(AppUser appUser);
    void disableById(Long userId);
}
