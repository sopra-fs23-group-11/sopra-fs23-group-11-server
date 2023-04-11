package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.Shot;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import ch.uzh.ifi.hase.soprafs23.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs23.repository.ShipPlayerRepository;
import ch.uzh.ifi.hase.soprafs23.repository.ShipRepository;
import ch.uzh.ifi.hase.soprafs23.repository.ShotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Shot attack(long attackerId, long defenderId, String PosOfShot){
        //ToDo check 2 player exist, posOfShot valid, ckeck hitormiss, save shot
        // hitPart++, check if last field of ship--> isSunk=true,
    return null;}

    private ShipPlayer waterORship(String position, long defenderId){
        //ToDO for loop on def.Ships-->(use isContained),
    return null;}

    //ToDO test in the application , Schiffe sind gesunken
}
