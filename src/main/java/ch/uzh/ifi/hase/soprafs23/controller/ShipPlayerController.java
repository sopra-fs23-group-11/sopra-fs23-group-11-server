package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.Player;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import ch.uzh.ifi.hase.soprafs23.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.PlayerService;
import ch.uzh.ifi.hase.soprafs23.service.ShipPlayerService;
import org.springframework.web.bind.annotation.RestController;

import ch.uzh.ifi.hase.soprafs23.rest.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ShipPlayerController {
    private final ShipPlayerService shipPlayerService;
    private final PlayerService playerService;

    ShipPlayerController(ShipPlayerService shipPlayerService,
                         PlayerService playerService) {
        this.shipPlayerService = shipPlayerService;
        this.playerService = playerService;
    }

    @PostMapping("/submit/ships")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void submitShips(@RequestBody ShipPlayerPostDTO shipPlayerPostDTO){
        //ShipPlayer ship = DTOMapper.INSTANCE.convertShipPlayerPostDTOtoEntity(shipPlayerPostDTO);
        shipPlayerService.placeShip(shipPlayerPostDTO.getShipPlayerPlayerId(), shipPlayerPostDTO.getShipPlayerShipId(),
                shipPlayerPostDTO.getStartPosition(), shipPlayerPostDTO.getEndPosition(), shipPlayerPostDTO.getGameId());
    }
    @GetMapping("/shipPlayer/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ShipPlayerGetDTO> getAllShipsPosition(@PathVariable("playerId") long playerId) {
        List<ShipPlayer> shipPlayers = shipPlayerService.getPlayersShip(playerId);
        List<ShipPlayerGetDTO> shipPlayerGetDTOS = new ArrayList<>();
        for (ShipPlayer ship : shipPlayers){
            shipPlayerGetDTOS.add(DTOMapper.INSTANCE.convertEntityToShipPlayerGetDTO(ship));
        }
        return shipPlayerGetDTOS;
    }




    //ToDo fix it
    @GetMapping("/ships/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PlayerGetDTO playerShips(@PathVariable("id") long id){
        Player player = shipPlayerService.getPlayerById(id);
        return DTOMapper.INSTANCE.convertEntityToPlayerGetDTO(player);
    }
}

