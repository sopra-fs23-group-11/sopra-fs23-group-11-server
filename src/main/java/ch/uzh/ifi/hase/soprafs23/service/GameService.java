package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.Helper;
import ch.uzh.ifi.hase.soprafs23.entity.Player;
import ch.uzh.ifi.hase.soprafs23.entity.Shot;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import ch.uzh.ifi.hase.soprafs23.exceptions.EntityNotFoundExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.PlayerExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.PositionExcep;
import ch.uzh.ifi.hase.soprafs23.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs23.repository.ShipPlayerRepository;
import ch.uzh.ifi.hase.soprafs23.repository.ShipRepository;
import ch.uzh.ifi.hase.soprafs23.repository.ShotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    private final PlayerRepository playerRepository;
    private final ShotRepository shotRepository;
    private final ShipPlayerRepository shipPlayerRepository;
    private final ShipRepository shipRepository;

    @Autowired
    public GameService(PlayerRepository playerRepository, ShotRepository shotRepository, ShipPlayerRepository shipPlayerRepository, ShipRepository shipRepository) {
        this.playerRepository = playerRepository;
        this.shotRepository = shotRepository;
        this.shipPlayerRepository = shipPlayerRepository;
        this.shipRepository = shipRepository;
    }

    public Shot attack(long attackerId, long defenderId, String posOfShot) { //flush
        Optional<Player> attacker = playerRepository.findById(attackerId);
        Optional<Player> defender = playerRepository.findById(defenderId);
        if (attackerId == defenderId)
            throw new PlayerExcep("players should differ");
        if (attacker.isEmpty())
            throw new EntityNotFoundExcep("attacker doesn't exist");
        if (defender.isEmpty())
            throw new EntityNotFoundExcep("defender doesn't exist");

        if (!isValidShot(posOfShot, defender.get()))
            throw new PositionExcep("not valid shot");
        ShipPlayer ship_hit = waterORship(posOfShot, defender.get());
        Shot shotPosition = new Shot();
        if (ship_hit != null) {
            shotPosition.setHit(true);
            ship_hit.setHitParts(ship_hit.getHitParts() + 1);
            if (ship_hit.getHitParts() == ship_hit.getShip().getLength()) {
                ship_hit.setSunk(true);

            }
            shipPlayerRepository.save(ship_hit);
        }
        else {
            shotPosition.setHit(false);

        }
        shotPosition.setAttacker(attacker.get());
        shotPosition.setDefender(defender.get());
        shotPosition.setPosition(posOfShot);
        shotRepository.save(shotPosition);
        return shotPosition;
    }


    private boolean isValidShot(String shotPosition, Player defender) {
        Shot shot = shotRepository.findByPositionAndDefender(shotPosition, defender);
        if (shot != null)
            throw new PositionExcep("This field was shot at before");
        if (!shotPosition.matches("[A-J][0-9]"))
            throw new PositionExcep("enter a valid position");
        return true;
    }


    private ShipPlayer waterORship(String position, Player defender) {
        for (ShipPlayer shipPlayer : defender.getShipPlayers()) {
            if (Helper.isContained(position, shipPlayer)) {
                return shipPlayer;
            }
        }
        return null;
    }

    public List<Shot> getAttackersShot(long attackerId) {
        Optional<Player> player = playerRepository.findById(attackerId);
        if (player.isEmpty())
            throw new EntityNotFoundExcep("player not found");
        Player attacker = player.get();
        return shotRepository.findAllByAttacker(attacker);
    }

    public List<Shot> getDefendersShot(long defenderId) {
        Optional<Player> player = playerRepository.findById(defenderId);
        if (player.isEmpty())
            throw new EntityNotFoundExcep("player not found");

        Player defender = player.get();
        return shotRepository.findAllByDefender(defender);
    }

}
