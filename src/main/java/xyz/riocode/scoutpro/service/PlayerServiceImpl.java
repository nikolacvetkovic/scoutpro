package xyz.riocode.scoutpro.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.riocode.scoutpro.exception.AppUserNotFoundException;
import xyz.riocode.scoutpro.exception.DuplicatePlayerException;
import xyz.riocode.scoutpro.exception.PlayerNotFoundException;
import xyz.riocode.scoutpro.model.AppUser;
import xyz.riocode.scoutpro.model.AppUserPlayer;
import xyz.riocode.scoutpro.model.AppUserPlayerId;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.repository.AppUserPlayerRepository;
import xyz.riocode.scoutpro.repository.AppUserRepository;
import xyz.riocode.scoutpro.repository.PlayerRepository;
import xyz.riocode.scoutpro.scrape.template.async.ScrapeAsyncWrapper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final AppUserRepository appUserRepository;
    private final AppUserPlayerRepository appUserPlayerRepository;
    private final ScrapeAsyncWrapper scrapeAsyncWrapper;

    public PlayerServiceImpl(PlayerRepository playerRepository, AppUserRepository appUserRepository, AppUserPlayerRepository appUserPlayerRepository, ScrapeAsyncWrapper scrapeAsyncWrapper) {
        this.playerRepository = playerRepository;
        this.appUserRepository = appUserRepository;
        this.appUserPlayerRepository = appUserPlayerRepository;
        this.scrapeAsyncWrapper = scrapeAsyncWrapper;
    }

    @Override
    public Player create(Player player) {
        return playerRepository.save(scrapeAll(player));
    }

    @Override
    public Player createOrUpdate(Player player, String username){
        Player p = null;
        if (player.getId() == null) {
            p = scrapeAll(player);
            AppUser appUser = appUserRepository.findByUsername(username).get();
            p.getUsers().stream().findFirst().get().setAppUser(appUser);
        } else {
            p = update(player, username);
        }
        return playerRepository.save(p);
    }

    @Override
    public Player addExistingPlayerToUser(Long id, boolean isUserPlayer, String username) {
        Player foundPlayer = playerRepository.findById(id).orElseThrow(PlayerNotFoundException::new);
        AppUser foundUser = appUserRepository.findByUsername(username).orElseThrow(AppUserNotFoundException::new);

        AppUserPlayerId appUserPlayerId = new AppUserPlayerId();
        AppUserPlayer appUserPlayer = new AppUserPlayer();
        appUserPlayer.setAppUserPlayerId(appUserPlayerId);
        appUserPlayer.setPlayer(foundPlayer);
        appUserPlayer.setAppUser(foundUser);
        appUserPlayer.setMyPlayer(isUserPlayer);

        appUserPlayerRepository.save(appUserPlayer);

        return appUserPlayer.getPlayer();
    }

    @Override
    public Player getByIdAndUser(Long id, String username) {
        return playerRepository.findPlayerByIdAndUsername(id, username).orElseThrow(PlayerNotFoundException::new);
    }

    @Override
    public Player getByIdAndUserComplete(Long id, String username) {
        Player foundPlayer = playerRepository.findPlayerByIdAndUsernameComplete(id, username).orElse(null);
        if(foundPlayer != null) return foundPlayer;
        return playerRepository.findById(id).orElseThrow(PlayerNotFoundException::new);
    }

    @Override
    public List<Player> getByNameAndUserUnfollowed(String playerName, String username) {
        return playerRepository.findByPlayerNameWithUser(playerName, username);
    }

    @Override
    public List<Player> getByName(String playerName) {
        return playerRepository.findByPlayerNameContains(playerName);
    }

    @Override
    public Page<Player> getByUserPaging(String username, int page) {
        return playerRepository.findPlayersByUsername(username, PageRequest.of(page, 25, Sort.by(Sort.Direction.DESC, "inserted")));
    }

    @Override
    public Page<Player> getByUserAndPositionPaging(String username, String position, int page) {
        return playerRepository.findPlayersByUsernameAndPosition(username, position, PageRequest.of(page, 25, Sort.by(Sort.Direction.DESC, "inserted")));
    }

    @Override
    public void delete(Long playerId, String username) {
        Player foundPlayer = playerRepository.findPlayerByIdAndUsername(playerId, username).orElseThrow(PlayerNotFoundException::new);

        AppUserPlayer appUserPlayer = foundPlayer.getUsers().stream().findFirst().get();
        AppUser appUser = appUserPlayer.getAppUser();

        foundPlayer.removeUser(appUser);
    }

    private Player scrapeAll(Player player){
        Player foundPlayer = playerRepository.findByTransfermarktUrl(player.getTransfermarktUrl());
        if(foundPlayer != null) throw new DuplicatePlayerException();

        CompletableFuture<Player> tmAll = scrapeAsyncWrapper.tmAllScrape(player);
        CompletableFuture<Player> pesDb = scrapeAsyncWrapper.pesDbScrape(player);
//        CompletableFuture<Player> wsAll = scrapeAsyncWrapper.wsAllScrape(player);
        CompletableFuture<Player> psml = scrapeAsyncWrapper.psmlScrape(player);

//        CompletableFuture.allOf(tmAll, pesDb, wsAll, psml).join();
        CompletableFuture.allOf(tmAll, pesDb, psml).join();
        Player p = null;

        try {
            p = tmAll.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return p;
    }

    private Player update(Player player, String username) {
        Player foundPlayer = playerRepository.findPlayerByIdAndUsername(player.getId(), username).orElseThrow(PlayerNotFoundException::new);

        AppUserPlayer foundAppUserPlayer = foundPlayer.getUsers().stream().findFirst().get();
        foundAppUserPlayer.setMyPlayer(player.getUsers().stream().findFirst().get().isMyPlayer());

        return playerRepository.save(foundPlayer);
    }
}
