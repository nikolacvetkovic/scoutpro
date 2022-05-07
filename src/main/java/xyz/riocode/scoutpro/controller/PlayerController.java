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
import xyz.riocode.scoutpro.converter.PsmlTransferConverter;
import xyz.riocode.scoutpro.converter.TransferConverter;
import xyz.riocode.scoutpro.dto.*;
import xyz.riocode.scoutpro.security.privilege.PlayerCreatePrivilege;
import xyz.riocode.scoutpro.security.privilege.PlayerDeletePrivilege;
import xyz.riocode.scoutpro.security.privilege.PlayerReadPrivilege;
import xyz.riocode.scoutpro.security.privilege.PlayerTransferReadPrivilege;
import xyz.riocode.scoutpro.service.PlayerService;
import xyz.riocode.scoutpro.service.PsmlTransferService;
import xyz.riocode.scoutpro.service.TransferService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Log4j2
@RequestMapping("/player")
@Controller
public class PlayerController {
    
    private final PlayerService playerService;
    private final TransferService transferService;
    private final PsmlTransferService psmlTransferService;

    public PlayerController(PlayerService playerService, TransferService transferService, PsmlTransferService psmlTransferService) {
        this.playerService = playerService;
        this.transferService = transferService;
        this.psmlTransferService = psmlTransferService;
    }

    @PlayerCreatePrivilege
    @GetMapping("/new")
    public String showPlayerForm(ModelMap modelMap){
        modelMap.addAttribute("player", new PlayerFormDTO());
        return "player/playerForm";
    }

    @PlayerCreatePrivilege
    @PostMapping
    public String createPlayerForm(@Valid PlayerFormDTO player,
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
        PlayerCompleteDTO createdPlayer = PlayerConverter.playerToPlayerCompleteDTO(
                                            playerService.createAndAddToUser(
                                                PlayerConverter.playerFormDTOToPlayer(player), principal.getName()),
                                                principal.getName());

        return "redirect:/player/"+ createdPlayer.getId() +"/show";
    }

    @PlayerReadPrivilege
    @GetMapping("/existing")
    public String showPlayerAddExisting(ModelMap modelMap){
        return "player/playerAddExisting";
    }

    @PlayerReadPrivilege
    @GetMapping("/{playerId}/{isUserPlayer}/follow")
    public String follow(@PathVariable Long playerId,
                         @PathVariable Boolean isUserPlayer,
                         Principal principal){
        playerService.addExistingPlayerToUser(playerId, isUserPlayer, principal.getName());
        return "redirect:/player/"+ playerId + "/show";
    }

    @PlayerReadPrivilege
    @GetMapping("/{playerId}/show")
    public String show(@PathVariable Long playerId,
                       ModelMap modelMap,
                       Principal principal){
        PlayerCompleteDTO foundPlayer = PlayerConverter.playerToPlayerCompleteDTO(
                                                playerService.getByIdAndUserComplete(playerId, principal.getName()),
                                                principal.getName());
        modelMap.addAttribute("player", foundPlayer);
        return "player/showPlayer";
    }

    @PlayerReadPrivilege
    @GetMapping("/compare")
    public String compare(ModelMap modelMap){
        modelMap.addAttribute("player1", new PlayerCompleteDTO());
        return "player/compare";
    }

    @PlayerReadPrivilege
    @GetMapping("/{playerId}/compare")
    public String compare(@PathVariable Long playerId,
                          ModelMap modelMap,
                          Principal principal){
        PlayerCompleteDTO playerCompleteDTO = PlayerConverter.playerToPlayerCompleteDTO(
                                                playerService.getByIdAndUserComplete(playerId, principal.getName()),
                                                principal.getName());
        modelMap.addAttribute("player1", playerCompleteDTO);
        return "player/compare";
    }

    @PlayerDeletePrivilege
    @GetMapping("/{playerId}/unfollow")
    public String unfollow(@PathVariable Long playerId,
                           Principal principal){
        playerService.deleteFromUser(playerId, principal.getName());
        return "redirect:/dashboard";
    }

    @GetMapping("/ownership/{isMyPlayer}/{playerId}")
    public ResponseEntity<?> changeOwnership(@PathVariable boolean isMyPlayer,
                                             @PathVariable Long playerId,
                                             Principal principal) {
        playerService.changePlayerOwnership(playerId, isMyPlayer, principal.getName());
        return ResponseEntity.ok().build();
    }

    @PlayerReadPrivilege
    @GetMapping(value = "/{pageNumber}/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DashboardDTO> getPlayers(@PathVariable int pageNumber,
                                                   @RequestParam(required = false) String position,
                                                   Principal principal){
        log.info("Get players by page number: {}", pageNumber);
        DashboardDTO dashboardDTO;
        if (position == null) {
            dashboardDTO = PlayerConverter.playersToDashboardDTO(
                                        playerService.getByUserPaging(principal.getName(), pageNumber),
                                        principal.getName());
        } else {
            dashboardDTO = PlayerConverter.playersToDashboardDTO(
                                        playerService.getByUserAndPositionPaging(principal.getName(), position, pageNumber),
                                        principal.getName());
        }
        return new ResponseEntity<>(dashboardDTO, HttpStatus.OK);
    }

    @PlayerReadPrivilege
    @GetMapping(value = "/{playerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerCompleteDTO> getPlayerById(@PathVariable Long playerId,
                                                           Principal principal){
        PlayerCompleteDTO playerCompleteDTO = PlayerConverter.playerToPlayerCompleteDTO(
                                        playerService.getByIdAndUserComplete(playerId, principal.getName()),
                                        principal.getName());
        return new ResponseEntity<>(playerCompleteDTO, HttpStatus.OK);
    }

    @PlayerReadPrivilege
    @GetMapping("/{playerName}/name")
    public ResponseEntity<List<PlayerSearchDTO>> getPlayerByName(@PathVariable String playerName,
                                                                 Principal principal){
        List<PlayerSearchDTO> playerSearchDTOS = PlayerConverter.playersToPlayerSearchDTO(
                                                        playerService.getByName(playerName), principal.getName());
        return new ResponseEntity<>(playerSearchDTOS, HttpStatus.OK);
    }

    @PlayerReadPrivilege
    @GetMapping("/{playerName}/name/unfollowed")
    public ResponseEntity<List<PlayerSearchDTO>> getPlayerByNameUnfollowed(@PathVariable String playerName,
                                                                           Principal principal){
        List<PlayerSearchDTO> playerSearchDTOS = PlayerConverter.playersToAddPlayerSearchDTO(
                                                        playerService.getByNameAndUserUnfollowed(playerName, principal.getName()),
                                                        principal.getName());
        return new ResponseEntity<>(playerSearchDTOS, HttpStatus.OK);
    }

    @PlayerReadPrivilege
    @GetMapping("/{playerName}/name/followed")
    public ResponseEntity<List<PlayerSearchDTO>> getPlayerByNameFollowed(@PathVariable String playerName,
                                                                         Principal principal){
        List<PlayerSearchDTO> playerSearchDTOS = PlayerConverter.playersToPlayerSearchDTO(
                                                        playerService.getByNameAndUserFollowed(playerName, principal.getName()),
                                                        principal.getName());
        return new ResponseEntity<>(playerSearchDTOS, HttpStatus.OK);
    }

    @PlayerTransferReadPrivilege
    @GetMapping("/transfers")
    public String showTransfers(ModelMap modelMap, Principal principal){
        List<TransferDTO> transfers = TransferConverter.transfersToTransferDTOs(
                transferService.getInLastMonthByUser(principal.getName()));
        List<PsmlTransferDTO> psmlTransfers = PsmlTransferConverter.psmlTransfersToPsmlTransferDTOs(
                psmlTransferService.getInLastMonthByUser(principal.getName()));

        modelMap.addAttribute("transfers", transfers);
        modelMap.addAttribute("psmlTransfers", psmlTransfers);
        return "player/transfers";
    }
}
