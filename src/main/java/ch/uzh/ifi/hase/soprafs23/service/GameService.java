package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.Helper;
import ch.uzh.ifi.hase.soprafs23.entity.Player;
import ch.uzh.ifi.hase.soprafs23.entity.Shot;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import ch.uzh.ifi.hase.soprafs23.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs23.repository.ShipPlayerRepository;
import ch.uzh.ifi.hase.soprafs23.repository.ShipRepository;
import ch.uzh.ifi.hase.soprafs23.repository.ShotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        if (attacker.isPresent() && defender.isPresent()  && attacker.get()!= defender.get() && isValidShot(posOfShot, defender.get())) {
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
            //attacker.get().getShotsAttack().add(shotPosition);
            //defender.get().getShotsDefend().add(shotPosition);
            //playerRepository.save(attacker.get());
            //playerRepository.save(defender.get());
            shotRepository.save(shotPosition);
            return shotPosition;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("both players should exist and differ"));
        }
    }

    //ToDo: seperate errors


    private boolean isValidShot(String shotPosition, Player defender) {
        Shot shot = shotRepository.findByPositionAndDefender(shotPosition, defender);
        if (shot == null) {
            if (shotPosition.matches("[A-J][0-9]")) {
                System.out.println("valid");
                return true;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("enter a valid position"));
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, String.format("This field was shot at before"));
        }
    }


    private ShipPlayer waterORship(String position, Player defender) {
        for (ShipPlayer shipPlayer : defender.getShipPlayers()) {
            if (Helper.isContained(position, shipPlayer)) {
                return shipPlayer;
            }
        }
        return null;
    }

    //ToDO test in the application , Schiffe sind gesunken
}
