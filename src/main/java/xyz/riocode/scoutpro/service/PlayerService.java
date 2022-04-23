package xyz.riocode.scoutpro.service;

import org.springframework.data.domain.Page;
import xyz.riocode.scoutpro.model.Player;

import java.util.List;


public interface PlayerService {
    Player create(Player player);
    Player createAndAddToUser(Player player, String username);
    Player changePlayerOwnership(Long id, boolean isUserPlayer, String username);
    Player addExistingPlayerToUser(Long id, boolean isUserPlayer, String username);
    Player getByIdAndUser(Long id, String username);
    Player getByIdAndUserComplete(Long id, String username);
    List<Player> getByNameAndUserUnfollowed(String playerName, String username);
    List<Player> getByNameAndUserFollowed(String playerName, String username);
    List<Player> getByName(String playerName);
    Page<Player> getAllPaging(int page, int pageSize);
    Page<Player> getByUserPaging(String username, int page);
    Page<Player> getByUserAndPositionPaging(String username, String position, int page);
    void deleteFromUser(Long playerId, String username);
}
