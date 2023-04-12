package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.service.ShipPlayerService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShipPlayerController {
    private final ShipPlayerService shipPlayerService;

    ShipPlayerController(ShipPlayerService shipPlayerService) {
        this.shipPlayerService = shipPlayerService;
    }
}
