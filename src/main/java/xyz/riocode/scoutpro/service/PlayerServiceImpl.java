package xyz.riocode.scoutpro.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.riocode.scoutpro.exception.AppUserNotFoundException;
import xyz.riocode.scoutpro.exception.DuplicatePlayerException;
import xyz.riocode.scoutpro.exception.PlayerNotFoundException;
import xyz.riocode.scoutpro.model.Player;
import xyz.riocode.scoutpro.model.security.AppUser;
import xyz.riocode.scoutpro.model.security.AppUserPlayer;
import xyz.riocode.scoutpro.model.security.AppUserPlayerId;
import xyz.riocode.scoutpro.repository.AppUserPlayerRepository;
import xyz.riocode.scoutpro.repository.AppUserRepository;
import xyz.riocode.scoutpro.repository.PlayerRepository;
import xyz.riocode.scoutpro.scrape.engine.ScrapeEngine;

import javax.transaction.Transactional;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final AppUserRepository appUserRepository;
    private final AppUserPlayerRepository appUserPlayerRepository;
    private final ScrapeEngine scrapeEngine;

    public PlayerServiceImpl(PlayerRepository playerRepository, AppUserRepository appUserRepository,
                             AppUserPlayerRepository appUserPlayerRepository, ScrapeEngine scrapeEngine) {
        this.playerRepository = playerRepository;
        this.appUserRepository = appUserRepository;
        this.appUserPlayerRepository = appUserPlayerRepository;
        this.scrapeEngine = scrapeEngine;
    }

    @Override
    public Player create(Player player) {
        scrapeAll(player);
        return playerRepository.save(player);
    }

    @Override
    public Player createAndAddToUser(Player player, String username) {
        scrapeAll(player);
        AppUser appUser = appUserRepository.findByUsername(username).get();
        player.getUsers().stream().findFirst().get().setAppUser(appUser);
        player.setInserted(LocalDateTime.now());
        return playerRepository.save(player);
    }

    @Override
    public Player changePlayerOwnership(Long id, boolean isUserPlayer, String username) {
        Player foundPlayer = playerRepository.findById(id).orElseThrow(PlayerNotFoundException::new);
        AppUserPlayer foundedAppUserPlayer = foundPlayer.getUsers()
                .stream()
                .filter(appUserPlayer -> appUserPlayer.getAppUser().getUsername().equals(username))
                .findFirst().orElseThrow(() -> new RuntimeException("The player is not followed by the user " + username));
        if (isUserPlayer) {
            if (foundPlayer.getUsers()
                    .stream()
                    .filter(AppUserPlayer::isMyPlayer)
                    .anyMatch(appUserPlayer -> !appUserPlayer.getAppUser().getUsername().equals(username)))
                throw new RuntimeException("The player is already owned.");
        }
        foundedAppUserPlayer.setMyPlayer(isUserPlayer);

        return playerRepository.save(foundPlayer);
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
        return playerRepository.findByIdAndUsername(id, username).orElseThrow(PlayerNotFoundException::new);
    }

    @Override
    public Player getByIdAndUserComplete(Long id, String username) {
        Player foundPlayer = playerRepository.findByIdAndUsernameComplete(id, username).orElse(null);
        if(foundPlayer != null) return foundPlayer;
        return playerRepository.findById(id).orElseThrow(PlayerNotFoundException::new);
    }

    @Override
    public List<Player> getByNameAndUserUnfollowed(String playerName, String username) {
        return playerRepository.findByNameContainsAndWithoutUser(playerName, username);
    }

    @Override
    public List<Player> getByNameAndUserFollowed(String playerName, String username) {
        return playerRepository.findByNameContainsAndUser(playerName, username);
    }

    @Override
    public List<Player> getByName(String playerName) {
        return playerRepository.findByNameContains(playerName);
    }

    @Override
    public Page<Player> getAllPaging(int page, int pageSize) {
        return playerRepository.findAll(PageRequest.of(page, pageSize));
    }

    @Override
    public Page<Player> getByUserPaging(String username, int page) {
        return playerRepository.findByUsername(username, PageRequest.of(page, 25,
                                                            Sort.by(Sort.Direction.DESC, "inserted")));
    }

    @Override
    public Page<Player> getByUserAndPositionPaging(String username, String position, int page) {
        return playerRepository.findByUsernameAndPosition(username, position, PageRequest.of(page, 25,
                                                            Sort.by(Sort.Direction.DESC, "inserted")));
    }

    @Override
    public void deleteFromUser(Long playerId, String username) {
        Player foundPlayer = playerRepository.findByIdAndUsername(playerId, username).orElseThrow(PlayerNotFoundException::new);

        AppUserPlayer appUserPlayer = foundPlayer.getUsers().stream().findFirst().get();
        AppUser appUser = appUserPlayer.getAppUser();

        foundPlayer.removeUser(appUser);
    }

    private void scrapeAll(Player player) {
        Player foundPlayer = playerRepository.findByTransfermarktUrl(player.getTransfermarktUrl());
        if(foundPlayer != null) throw new DuplicatePlayerException();

        try {
            scrapeEngine.work(new URL(player.getTransfermarktUrl()), player);
            scrapeEngine.work(new URL(player.getPesDbUrl()), player);
            scrapeEngine.work(new URL(player.getPsmlUrl()), player);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }
//        CompletableFuture<Player> tmAll = scrapeAsyncWrapper.tmAllScrape(player, tmScrapeFields);
//        CompletableFuture<Player> pesDb = scrapeAsyncWrapper.pesDbScrape(player, pesdbScrapeFields);
//        CompletableFuture<Player> psml = scrapeAsyncWrapper.psmlScrape(player, psmlScrapeFields, psmlPageSupplier);
//
//        CompletableFuture.allOf(tmAll, pesDb, psml).join();
//        Player p = null;

//        try {
//            p = tmAll.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
    }
}
