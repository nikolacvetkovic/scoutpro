package xyz.riocode.scoutpro.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.riocode.scoutpro.exception.AppUserNotFoundException;
import xyz.riocode.scoutpro.exception.DuplicateAppUserUsernameException;
import xyz.riocode.scoutpro.model.AppUser;
import xyz.riocode.scoutpro.repository.AppUserRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppUserServiceImplTest {

    private final static Long APP_USER_ID = 1L;
    private final static String APP_USER_USERNAME = "cvelenidza";
    private final static String APP_USER_PASSWORD = "12345";

    @Mock
    AppUserRepository appUserRepository;

    @InjectMocks
    AppUserServiceImpl appUserService;

    AppUser user;

    @BeforeEach
    void setUp(){
        user = new AppUser();
        user.setId(APP_USER_ID);
        user.setUsername(APP_USER_USERNAME);
        user.setPassword(APP_USER_PASSWORD);
        user.setEnabled(false);
    }

    @Test
    void createUserOk(){
        when(appUserRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        ArgumentCaptor<AppUser> argumentCaptor = ArgumentCaptor.forClass(AppUser.class);

        appUserService.create(user);

        verify(appUserRepository).findByUsername(anyString());
        verify(appUserRepository).save(argumentCaptor.capture());
        AppUser savedUser = argumentCaptor.getValue();
        assertEquals(APP_USER_ID, savedUser.getId());
        assertEquals(APP_USER_USERNAME, savedUser.getUsername());
        assertEquals(false, savedUser.isEnabled());
    }

    @Test
    void createUserDuplicateUsername(){
        when(appUserRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        assertThrows(DuplicateAppUserUsernameException.class, () -> appUserService.create(user));

        verify(appUserRepository).findByUsername(anyString());
    }

    @Disabled
    void testGetAllPagingOk(){

    }

    @Disabled
    void testGetAllPagingEmpty(){

    }

    @Test
    void testGetAppUsersByUsernameOk(){
        AppUser user1 = new AppUser();
        user1.setId(2L);
        user1.setUsername("ana");
        AppUser user2 = new AppUser();
        user2.setId(3L);
        user2.setUsername("mara");

        when(appUserRepository.findAppUsersByUsername(anyString())).thenReturn(new HashSet<>(Arrays.asList(user, user1, user2)));

        Set<AppUser> returnedAppUsers = appUserService.getAppUsersByUsername(APP_USER_USERNAME);

        assertNotNull(returnedAppUsers);
        assertEquals(3, returnedAppUsers.size());
    }

    @Test
    void testGetAppUsersByUsernameEmpty(){
        when(appUserRepository.findAppUsersByUsername(anyString())).thenReturn(Collections.EMPTY_SET);

        Set<AppUser> returnedAppUsers = appUserService.getAppUsersByUsername(APP_USER_USERNAME);

        assertNotNull(returnedAppUsers);
        assertEquals(0, returnedAppUsers.size());
    }

    @Test
    void testGetByUsernameOk(){

        when(appUserRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        AppUser returnedAppUser = appUserService.getByUsername(APP_USER_USERNAME);

        assertNotNull(returnedAppUser);
        assertEquals(APP_USER_ID, returnedAppUser.getId());
        assertEquals(APP_USER_USERNAME, returnedAppUser.getUsername());
    }

    @Test
    void testGetByUsernameNotFound(){
        when(appUserRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(AppUserNotFoundException.class, () -> appUserService.getByUsername(APP_USER_USERNAME));
    }

    @Test
    void changePasswordOk(){
        String pass = "aaaa";
        AppUser appUserForUpdate = new AppUser();
        appUserForUpdate.setPassword(pass);

        when(appUserRepository.findByUsername(anyString())).thenReturn(Optional.of(appUserForUpdate));
        ArgumentCaptor<AppUser> argumentCaptor = ArgumentCaptor.forClass(AppUser.class);

        appUserService.changePassword(user);

        verify(appUserRepository).findByUsername(anyString());
        verify(appUserRepository).save(argumentCaptor.capture());
        AppUser updatedUser = argumentCaptor.getValue();
        assertNotEquals(pass, updatedUser.getPassword());
        assertEquals(APP_USER_PASSWORD, updatedUser.getPassword());
    }

    @Test
    void changePasswordUserNotExists(){
        when(appUserRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(AppUserNotFoundException.class, () -> appUserService.changePassword(user));
    }

    @Test
    void testDisableByIdOk(){
        when(appUserRepository.findById(anyLong())).thenReturn(Optional.of(user));
        ArgumentCaptor<AppUser> argumentCaptor = ArgumentCaptor.forClass(AppUser.class);

        appUserService.disableById(1L);

        verify(appUserRepository).save(any(AppUser.class));
        verify(appUserRepository).save(argumentCaptor.capture());
        AppUser disabledAppUser = argumentCaptor.getValue();
        assertNotNull(disabledAppUser);
        assertEquals(false, disabledAppUser.isEnabled());
    }

    @Test
    void testDisableByIdNotFound(){
        when(appUserRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(AppUserNotFoundException.class, () -> appUserService.disableById(1L));
    }
}