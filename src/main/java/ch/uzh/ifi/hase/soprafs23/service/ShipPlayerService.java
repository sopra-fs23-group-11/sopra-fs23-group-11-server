package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.Player;
import ch.uzh.ifi.hase.soprafs23.entity.Helper;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Position;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import ch.uzh.ifi.hase.soprafs23.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs23.repository.ShipPlayerRepository;
import ch.uzh.ifi.hase.soprafs23.repository.ShipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ShipPlayerService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final ShipPlayerRepository shipPlayerRepository;
    private final ShipRepository shipRepository;
    private final PlayerRepository playerRepository;
    private  final Helper helper;


    @Autowired
    public ShipPlayerService(ShipPlayerRepository shipPlayerRepository, ShipRepository shipRepository, PlayerRepository playerRepository, Helper helper) {
        this.shipPlayerRepository = shipPlayerRepository;
        this.shipRepository = shipRepository;
        this.playerRepository = playerRepository;
        this.helper = helper;
    }


    //ToDo: add all functions to validate a position first
    //ToDO: check abs(start-end)=length
    public ShipPlayer placeShip(long playerId, long shipId, String startPosition, String endPosition) {
        String baseErrorMessage = "Ship can't be placed: %s";
        Optional<Ship> shipOptional = shipRepository.findById(shipId);
        Optional<Player> playerOptional = playerRepository.findById(playerId);
        if (shipOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("ship doesn't exist"));
        if (playerOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("player doesn't exist"));
        if(helper.shipsNotTouching(playerOptional.get()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("ships are touching"));

        Ship ship = shipOptional.get();
        Player player = playerOptional.get();
        ShipPlayer shipPlayer= new ShipPlayer();
        shipPlayer.setStartPosition(startPosition);
        shipPlayer.setEndPosition(endPosition);
        shipPlayer.setShip(ship);
        shipPlayer.setPlayer(player);
        player.getShipPlayers().add(shipPlayer);
        playerRepository.save(player);
        ShipPlayer shipPlayerToSave = shipPlayerRepository.save(shipPlayer);
        shipPlayerRepository.flush();
        playerRepository.flush();
        return shipPlayerToSave;
    }


    public List<ShipPlayer> getPlayersShip (long playerId){
        Optional<Player> playerOptional= playerRepository.findById(playerId);
        if (playerOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("player not found"));

        Player player = playerOptional.get();
        List<ShipPlayer> shipPlayer = shipPlayerRepository.findAllByPlayer(player);
        return shipPlayer;
    }
}
