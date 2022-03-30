package xyz.riocode.scoutpro.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.riocode.scoutpro.converter.PlayerConverter;
import xyz.riocode.scoutpro.dto.DashboardDTO;
import xyz.riocode.scoutpro.dto.PlayerCompleteDTO;
import xyz.riocode.scoutpro.dto.PlayerFormDTO;
import xyz.riocode.scoutpro.dto.PlayerSearchDTO;
import xyz.riocode.scoutpro.service.PlayerService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Log4j2
@RequestMapping("/player")
@Controller
public class PlayerController {
    
    private final PlayerService playerService;
    private final PlayerConverter playerConverter;

    public PlayerController(PlayerService playerService, PlayerConverter playerConverter) {
        this.playerService = playerService;
        this.playerConverter = playerConverter;
    }

    @GetMapping("/new")
    public String showPlayerForm(ModelMap modelMap){
        modelMap.addAttribute("player", new PlayerFormDTO());
        return "player/playerForm";
    }

    @GetMapping("/{playerId}/edit")
    public String showPlayerFormForEdit(@PathVariable Long playerId,
                                        ModelMap modelMap,
                                        Principal principal){
        PlayerFormDTO foundPlayer = playerConverter.playerToPlayerFormDTO(
                                                        playerService.getByIdAndUser(playerId, principal.getName()),
                                                        principal.getName());
        modelMap.addAttribute("player", foundPlayer);
        return "player/playerForm";
    }

    @PostMapping
    public String saveNewPlayerAndAddToUser(@Valid PlayerFormDTO player,
                                            BindingResult bindingResult,
                                            ModelMap modelMap,
                                            Principal principal){
        if(bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().stream()
                                            .map(fieldError -> fieldError.getField() + "-" + fieldError.getRejectedValue())
                                            .forEach(log::error);
            modelMap.addAttribute("player", player);
            return "player/playerForm";
        }
        PlayerCompleteDTO createdPlayer = playerConverter.playerToPlayerCompleteDTO(
                                            playerService.createOrUpdate(
                                                playerConverter.playerFormDTOToPlayer(player), principal.getName()),
                                                principal.getName());

        return "redirect:/player/"+ createdPlayer.getId() +"/show";
    }

    @GetMapping("/{playerId}/{isUserPlayer}/follow")
    public String follow(@PathVariable Long playerId,
                         @PathVariable Boolean isUserPlayer,
                         Principal principal){
        playerService.addExistingPlayerToUser(playerId, isUserPlayer, principal.getName());
        return "redirect:/player/"+ playerId + "/show";
    }

    @GetMapping("/{playerId}/show")
    public String show(@PathVariable Long playerId,
                       ModelMap modelMap){
        PlayerCompleteDTO foundPlayer = playerConverter.playerToPlayerCompleteDTO(playerService.getByIdAndUserComplete(playerId, "cvele"), "cvele");
        modelMap.addAttribute("player", foundPlayer);
        return "player/showPlayer";
    }

    @GetMapping("/compare")
    public String compare(ModelMap modelMap){
        modelMap.addAttribute("player1", new PlayerCompleteDTO());
        return "player/compare";
    }

    @GetMapping("/{playerId}/compare")
    public String compare(@PathVariable Long playerId,
                          ModelMap modelMap,
                          Principal principal){
        PlayerCompleteDTO playerCompleteDTO = playerConverter.playerToPlayerCompleteDTO(
                                                playerService.getByIdAndUserComplete(playerId, principal.getName()),
                                                principal.getName());
        modelMap.addAttribute("player1", playerCompleteDTO);
        return "player/compare";
    }

    @GetMapping("/{playerId}/unfollow")
    public String unfollow(@PathVariable Long playerId,
                           Principal principal){
        playerService.delete(playerId, principal.getName());
        return "redirect:/dashboard";
    }

    @GetMapping(value = "/{pageNumber}/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DashboardDTO> getPlayers(@PathVariable int pageNumber,
                                                   @RequestParam(required = false) String position,
                                                   Principal principal){
        log.info("Get players by page number: {}", pageNumber);
        DashboardDTO dashboardDTO;
        if (position == null) {
            dashboardDTO = playerConverter.playersToDashboardDTO(
                                        playerService.getByUserPaging(principal.getName(), pageNumber),
                                        principal.getName());
        } else {
            dashboardDTO = playerConverter.playersToDashboardDTO(
                                        playerService.getByUserAndPositionPaging(principal.getName(), position, pageNumber),
                                        principal.getName());
        }
        return new ResponseEntity<>(dashboardDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{playerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerCompleteDTO> getPlayerById(@PathVariable Long playerId,
                                                           Principal principal){
        PlayerCompleteDTO playerCompleteDTO = playerConverter.playerToPlayerCompleteDTO(
                                        playerService.getByIdAndUserComplete(playerId, principal.getName()),
                                        principal.getName());
        return new ResponseEntity<>(playerCompleteDTO, HttpStatus.OK);
    }

    @GetMapping("/{playerName}/name")
    public ResponseEntity<List<PlayerSearchDTO>> getPlayerByName(@PathVariable String playerName,
                                                                 Principal principal){
        List<PlayerSearchDTO> playerSearchDTOS = playerConverter.playersToPlayerSearchDTO(
                                                        playerService.getByName(playerName), principal.getName());
        return new ResponseEntity<>(playerSearchDTOS, HttpStatus.OK);
    }

    @GetMapping("/{playerName}/name/unfollowed")
    public ResponseEntity<List<PlayerSearchDTO>> getPlayerByNameUnfollowed(@PathVariable String playerName,
                                                                           Principal principal){
        List<PlayerSearchDTO> playerSearchDTOS = playerConverter.playersToAddPlayerSearchDTO(
                                                        playerService.getByNameAndUserUnfollowed(playerName, principal.getName()),
                                                        principal.getName());
        return new ResponseEntity<>(playerSearchDTOS, HttpStatus.OK);
    }

    @GetMapping("/{playerName}/name/followed")
    public ResponseEntity<List<PlayerSearchDTO>> getPlayerByNameFollowed(@PathVariable String playerName,
                                                                         Principal principal){
        List<PlayerSearchDTO> playerSearchDTOS = playerConverter.playersToPlayerSearchDTO(
                                                        playerService.getByNameAndUserFollowed(playerName, principal.getName()),
                                                        principal.getName());
        return new ResponseEntity<>(playerSearchDTOS, HttpStatus.OK);
    }
}
