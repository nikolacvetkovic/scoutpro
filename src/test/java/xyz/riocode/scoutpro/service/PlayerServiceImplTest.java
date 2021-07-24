package xyz.riocode.scoutpro.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.riocode.scoutpro.exception.PlayerNotFoundException;
import xyz.riocode.scoutpro.model.AppUser;
import xyz.riocode.scoutpro.model.AppUserPlayer;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.repository.PlayerRepository;
import xyz.riocode.scoutpro.scrape.template.async.ScrapeAsyncWrapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {

//    private static final Long PLAYER_ID = 1L;
//    private static final String PLAYER_NAME = "Messi";
//    private static final String USERNAME = "cvele";
//    private static final String TM_URL = "https://www.transfermarkt.com/kylian-mbappe/profil/spieler/342229";
//
//    @Mock
//    PlayerRepository playerRepository;
//    @Mock
//    ScrapeAsyncWrapper scrapeAsyncWrapper;
//
//    @InjectMocks
//    PlayerServiceImpl playerService;
//
//    Player player;
//    AppUser appUser;
//
//    @BeforeEach
//    void setUp(){
//        player = new Player();
//        player.setId(PLAYER_ID);
//        player.setPlayerName(PLAYER_NAME);
//        player.setTransfermarktUrl(TM_URL);
//        appUser = new AppUser();
//        appUser.setUsername(USERNAME);
//        AppUserPlayer appUserPlayer = new AppUserPlayer();
//        appUserPlayer.setPlayer(player);
//        appUserPlayer.setAppUser(appUser);
//        appUserPlayer.setMyPlayer(true);
//        player.getUsers().add(appUserPlayer);
//        appUser.getPlayers().add(appUserPlayer);
//    }
//
//    @Disabled
//    @Test
//    void testGetByIdAndUserOk(){
//        when(playerRepository.findPlayerByIdAndUsername(anyLong(), anyString())).thenReturn(Optional.of(player));
//
//        Player foundPlayer = playerService.getByIdAndUser(PLAYER_ID, USERNAME);
//
//        assertNotNull(foundPlayer);
//        assertEquals(PLAYER_ID, foundPlayer.getId());
//        assertEquals(PLAYER_NAME, foundPlayer.getPlayerName());
//    }
//
//    @Disabled
//    @Test
//    void testGetByIdAndUserNotFound(){
//        when(playerRepository.findPlayerByIdAndUsername(anyLong(), anyString())).thenReturn(Optional.empty());
//
//        assertThrows(PlayerNotFoundException.class, () -> playerService.getByIdAndUser(PLAYER_ID, USERNAME));
//    }
//
//    @Disabled
//    @Test
//    void testGetByNameAndUserOk(){
//        Player player1 = new Player();
//        player1.setId(2L);
//        player1.setPlayerName("Pirlo");
//        List<Player> players = new ArrayList<>(Arrays.asList(player, player1));
//
//        when(playerRepository.findByPlayerNameAndUsername(anyString(), anyString())).thenReturn(players);
//
//        List<Player> foundedPlayers = playerService.getByNameAndUser(PLAYER_NAME, USERNAME);
//
//        assertNotNull(foundedPlayers);
//        assertEquals(2, foundedPlayers.size());
//        verify(playerRepository).findByPlayerNameAndUsername(anyString(), anyString());
//    }
//
//    @Disabled
//    @Test
//    void testGetByNameAndUserNotFound(){
//        when(playerRepository.findByPlayerNameAndUsername(anyString(), anyString())).thenReturn(Collections.emptyList());
//
//        List<Player> foundedPlayers = playerService.getByNameAndUser(PLAYER_NAME, USERNAME);
//
//        assertNotNull(foundedPlayers);
//        verify(playerRepository).findByPlayerNameAndUsername(anyString(), anyString());
//        assertEquals(0, foundedPlayers.size());
//    }
//
//    @Disabled
//    void testGetByUserPagingOk(){
//
//    }
//
//    @Disabled
//    void testGetByUserPagingEmpty(){
//
//    }
//
//    @Disabled
//    @Test
//    void testUpdateOk(){
////        Player playerForUpdate = new Player();
////        playerForUpdate.setId(PLAYER_ID);
////        playerForUpdate.setPlayerName(PLAYER_NAME);
////        AppUser appUser = new AppUser();
////        appUser.setUsername(USERNAME);
////        AppUserPlayer appUserPlayer = new AppUserPlayer();
////        appUserPlayer.setPlayer(playerForUpdate);
////        appUserPlayer.setAppUser(appUser);
////        appUserPlayer.setMyPlayer(false);
////        playerForUpdate.getUsers().add(appUserPlayer);
////        appUser.getPlayers().add(appUserPlayer);
////
////        when(playerRepository.findPlayerByIdAndUsername(anyLong(), anyString())).thenReturn(Optional.of(player));
////        ArgumentCaptor<Player> argumentCaptor = ArgumentCaptor.forClass(Player.class);
////
////        playerService.update(playerForUpdate, USERNAME);
////
////        verify(playerRepository).findPlayerByIdAndUsername(anyLong(), anyString());
////        verify(playerRepository).save(any(Player.class));
////        verify(playerRepository).save(argumentCaptor.capture());
////        Player updatedPlayer = argumentCaptor.getValue();
////        assertEquals(PLAYER_NAME, updatedPlayer.getPlayerName());
////        assertFalse(updatedPlayer.getUsers().stream().findFirst().get().isMyPlayer());
//
//    }
//
//    @Disabled
//    @Test
//    void testUpdateNotFound(){
////        when(playerRepository.findPlayerByIdAndUsername(anyLong(), anyString())).thenReturn(Optional.empty());
////
////        assertThrows(PlayerNotFoundException.class, () -> playerService.update(player, USERNAME));
//    }
//
//    @Disabled
//    @Test
//    void testDeleteOk(){
//
//        when(playerRepository.findPlayerByIdAndUsername(anyLong(), anyString())).thenReturn(Optional.of(player));
//        ArgumentCaptor<Player> argumentCaptor = ArgumentCaptor.forClass(Player.class);
//
//        playerService.delete(PLAYER_ID, USERNAME);
//
//        verify(playerRepository).save(any(Player.class));
//        verify(playerRepository).save(argumentCaptor.capture());
//        Player deletedPlayer = argumentCaptor.getValue();
//        assertNotNull(deletedPlayer);
//        assertEquals(0, deletedPlayer.getUsers().size());
//
//    }
//
//    @Disabled
//    @Test
//    void testDeleteNotFound(){
//        when(playerRepository.findPlayerByIdAndUsername(anyLong(), anyString())).thenReturn(Optional.empty());
//
//        assertThrows(PlayerNotFoundException.class, () -> playerService.delete(PLAYER_ID, USERNAME));
//    }
//
//    @Disabled
//    @Test
//    void testCreateOk(){
////        TransfermarktInfo transfermarktInfo = new TransfermarktInfo();
////        transfermarktInfo.setAge(25);
////        transfermarktInfo.setClubTeam("Arsenal");
////        transfermarktInfo.setPlayer(player);
////        player.setTransfermarktInfo(transfermarktInfo);
////        Transfer transfer = new Transfer();
////        transfer.setFromTeam("Partizan");
////        transfer.setToTeam("Arsenal");
////        transfer.setMarketValue("25000000");
////        transfer.setPlayer(player);
////        player.getTransfers().add(transfer);
////
////        when(playerRepository.findByTransfermarktUrl(anyString())).thenReturn(null);
////        when(scrapeAsyncWrapper.tmAllScrape(any(Player.class))).thenReturn(CompletableFuture.completedFuture(player));
////        when(scrapeAsyncWrapper.pesDbScrape(any(Player.class))).thenReturn(CompletableFuture.completedFuture(player));
////        when(scrapeAsyncWrapper.wsAllScrape(any(Player.class))).thenReturn(CompletableFuture.completedFuture(player));
////        when(scrapeAsyncWrapper.psmlScrape(any(Player.class))).thenReturn(CompletableFuture.completedFuture(player));
////
////        playerService.create(player, USERNAME);
////
////        ArgumentCaptor<Player> argumentCaptor = ArgumentCaptor.forClass(Player.class);
////
////        verify(playerRepository).save(argumentCaptor.capture());
////        Player savedPlayer = argumentCaptor.getValue();
////        assertNotNull(savedPlayer);
////        assertEquals(PLAYER_ID, savedPlayer.getId());
////        assertEquals(1, savedPlayer.getTransfers().size());
//
//    }
//
//    @Disabled
//    @Test
//    void testCreateDuplicate(){
////        when(playerRepository.findByTransfermarktUrl(anyString())).thenReturn(player);
////
////        assertThrows(DuplicatePlayerException.class, () -> playerService.create(player, USERNAME));
//    }

}