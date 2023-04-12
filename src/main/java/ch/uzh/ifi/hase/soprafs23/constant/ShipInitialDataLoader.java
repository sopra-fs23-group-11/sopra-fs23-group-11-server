// The aim of this class is to create the Ships (ships representation) so that we have all five ships and
// the initial values saved in the DB

package ch.uzh.ifi.hase.soprafs23.constant;

import ch.uzh.ifi.hase.soprafs23.entity.Player;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;
import ch.uzh.ifi.hase.soprafs23.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs23.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ShipInitialDataLoader implements CommandLineRunner {

    @Autowired
    private ShipRepository shipRepository;

    @Override
    public void run(String... args) throws Exception {
        Ship ship1 = new Ship(1L, 2, "Destroyer");
        Ship ship2 = new Ship(2L, 3, "Submarine");
        Ship ship3 = new Ship(3L, 3, "Cruiser");
        Ship ship4 = new Ship(4L, 4, "BattleShip");
        Ship ship5 = new Ship(5L, 5, "Carrier");
        shipRepository.save(ship1);
        shipRepository.save(ship2);
        shipRepository.save(ship3);
        shipRepository.save(ship4);
        shipRepository.save(ship5);

    }
}


