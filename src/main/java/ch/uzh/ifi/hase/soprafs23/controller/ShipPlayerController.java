package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.service.ShipPlayerService;
import org.springframework.web.bind.annotation.RestController;

import ch.uzh.ifi.hase.soprafs23.rest.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShipPlayerController {
    private final ShipPlayerService shipPlayerService;

    ShipPlayerController(ShipPlayerService shipPlayerService) {
        this.shipPlayerService = shipPlayerService;
    }

    @PostMapping("/submit/ships")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void submitShips(@RequestBody ShipPlayerPostDTO shipPlayerPostDTO){
        //ShipPlayer ship = DTOMapper.INSTANCE.convertShipPlayerPostDTOtoEntity(shipPlayerPostDTO);
        shipPlayerService.placeShip(shipPlayerPostDTO.getShipPlayerPlayerId(), shipPlayerPostDTO.getShipPlayerShipId(), shipPlayerPostDTO.getStartPosition(), shipPlayerPostDTO.getEndPosition());
    }
}

