package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.Player;
import ch.uzh.ifi.hase.soprafs23.entity.Helper;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import ch.uzh.ifi.hase.soprafs23.exceptions.EntityNotFoundExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.PositionExcep;
import ch.uzh.ifi.hase.soprafs23.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs23.repository.ShipPlayerRepository;
import ch.uzh.ifi.hase.soprafs23.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ShipPlayerService {

    private final ShipPlayerRepository shipPlayerRepository;
    private final ShipRepository shipRepository;
    private final PlayerRepository playerRepository;
    private final Helper helper;


    @Autowired
    public ShipPlayerService(ShipPlayerRepository shipPlayerRepository, ShipRepository shipRepository, PlayerRepository playerRepository, Helper helper) {
        this.shipPlayerRepository = shipPlayerRepository;
        this.shipRepository = shipRepository;
        this.playerRepository = playerRepository;
        this.helper = helper;
    }


    public ShipPlayer placeShip(long playerId, long shipId, String startPosition, String endPosition, String gameId) {
        Optional<Ship> shipOptional = shipRepository.findById(shipId);
        Optional<Player> playerOptional = playerRepository.findById(playerId);
        if (shipOptional.isEmpty())
            throw new EntityNotFoundExcep("Ship doesn't exist", gameId);
        if (playerOptional.isEmpty())
            throw new EntityNotFoundExcep("Player doesn't exist", gameId);
        if (!Helper.shipsNotOverlapping(playerOptional.get(), startPosition, endPosition))
            throw new PositionExcep("Ships are overlapping", gameId);

        Ship ship = shipOptional.get();
        Player player = playerOptional.get();
        ShipPlayer shipPlayer = new ShipPlayer();
        shipPlayer.setStartPosition(startPosition);
        shipPlayer.setEndPosition(endPosition);
        shipPlayer.setShip(ship);
        shipPlayer.setPlayer(player);
        player.getShipPlayers().add(shipPlayer);
        playerRepository.save(player);
        //ShipPlayer shipPlayerToSave = shipPlayerRepository.save(shipPlayer);
        shipPlayerRepository.save(shipPlayer);
        shipPlayerRepository.flush();
        playerRepository.flush();
        //return shipPlayerToSave;
        return shipPlayer;
    }

    public void deleteShip(long shipPlayerId, String gameId) {
        Optional<ShipPlayer> shipPlayerOptional = shipPlayerRepository.findById(shipPlayerId);
        if (shipPlayerOptional.isEmpty())
            throw new EntityNotFoundExcep("Ship-player doesn't exist", gameId);

        ShipPlayer shipPlayer = shipPlayerOptional.get();
        Player player = shipPlayer.getPlayer();

        player.getShipPlayers().remove(shipPlayer);
        playerRepository.save(player);
        shipPlayerRepository.delete(shipPlayer);
        shipPlayerRepository.flush();
        playerRepository.flush();
    }

    public List<ShipPlayer> getPlayersShip(long playerId) {
        Optional<Player> playerOptional = playerRepository.findById(playerId);
        if (playerOptional.isEmpty())
            throw new EntityNotFoundExcep("Player not found", "ID");

        Player player = playerOptional.get();
        List<ShipPlayer> shipPlayer = shipPlayerRepository.findAllByPlayer(player);
        return shipPlayer;
    }


//    public Player getPlayerById(Long id) {
//        return playerRepository.getOne(id);
//    }
}

