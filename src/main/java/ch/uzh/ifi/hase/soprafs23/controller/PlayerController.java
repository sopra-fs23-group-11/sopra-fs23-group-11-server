package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.Grid.Grid;
import ch.uzh.ifi.hase.soprafs23.entity.Player;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.*;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.PlayerService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//ToDo: We have to change / add (later) the body / annotation for the methods here & in the playerService
/**
 * User Controller
 * This class is responsible for handling all REST request that are related to
 * the user.
 * The controller will receive the request and delegate the execution to the
 * UserService and finally return the result.
 */
@RestController
public class PlayerController {

  private final PlayerService playerService;

  PlayerController(PlayerService playerService) {
    this.playerService = playerService;
  }

    @GetMapping("/players")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<PlayerGetDTO> getAllPlayers() {
        List<Player> players = playerService.getPlayers();
        List<PlayerGetDTO> playerGetDTOs = new ArrayList<>();
        for (Player player : players) {
            playerGetDTOs.add(DTOMapper.INSTANCE.convertEntityToPlayerGetDTO(player));
        }
        return playerGetDTOs;
    }
/*
    @PostMapping("/players/register")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public PlayerGetDTO createPlayer(@RequestBody PlayerPostDTO playerPostDTO) {
        Player userInput = DTOMapper.INSTANCE.convertPlayerPostDTOtoEntity(playerPostDTO);
        Player createdUser = playerService.createPlayer(userInput);
        return DTOMapper.INSTANCE.convertEntityToPlayerGetDTO(createdUser);
    }

    @PutMapping("/players/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public PlayerGetDTO loginPlayer(@RequestBody PlayerPutDTO playerPutDTO){
        Player userInput = DTOMapper.INSTANCE.convertPlayerPutDTOtoEntity(playerPutDTO);
        Player player = playerService.loginPlayer(userInput);
        return DTOMapper.INSTANCE.convertEntityToPlayerGetDTO(player);
    }

*/

 /*
    @PutMapping("/submit/ships")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void submitShips(@RequestBody PlayerPutDTO playerPutDTO){
        Player userInput = DTOMapper.INSTANCE.convertPlayerPutDTOtoEntity(playerPutDTO);
        playerService.validateInput(userInput);
    }


  */
    @GetMapping("/board/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PlayerGetDTO getBoard(@PathVariable("id") long id){
        Player player = playerService.getField(id);
        return DTOMapper.INSTANCE.convertEntityToPlayerGetDTO(player);
    }
}
