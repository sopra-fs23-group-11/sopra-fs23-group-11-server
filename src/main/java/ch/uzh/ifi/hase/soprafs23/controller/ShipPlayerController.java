package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.ShipPlayerService;
import org.springframework.web.bind.annotation.RestController;

import ch.uzh.ifi.hase.soprafs23.rest.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ShipPlayerController {
    private final ShipPlayerService shipPlayerService;

    ShipPlayerController(ShipPlayerService shipPlayerService) {//,
        this.shipPlayerService = shipPlayerService;
    }

    @PostMapping("/ships")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void submitShips(@RequestBody ShipPlayerPostDTO shipPlayerPostDTO) {
        shipPlayerService.placeShip(shipPlayerPostDTO.getShipPlayerPlayerId(), shipPlayerPostDTO.getShipPlayerShipId(),
                shipPlayerPostDTO.getStartPosition(), shipPlayerPostDTO.getEndPosition(), shipPlayerPostDTO.getGameId());
    }

    @DeleteMapping("/ships")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void deleteShip(@RequestBody ShipPlayerPutDTO shipPlayerPutDTO) {
        shipPlayerService.deleteShip(shipPlayerPutDTO.getShipPlayerId(), shipPlayerPutDTO.getGameId());
    }

    @GetMapping("/shipPlayer/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ShipPlayerGetDTO> getAllShipsPosition(@PathVariable("playerId") long playerId) {
        List<ShipPlayer> shipPlayers = shipPlayerService.getPlayersShip(playerId);
        List<ShipPlayerGetDTO> shipPlayerGetDTOS = new ArrayList<>();
        for (ShipPlayer ship : shipPlayers) {
            shipPlayerGetDTOS.add(DTOMapper.INSTANCE.convertEntityToShipPlayerGetDTO(ship));
        }
        return shipPlayerGetDTOS;
    }

}

