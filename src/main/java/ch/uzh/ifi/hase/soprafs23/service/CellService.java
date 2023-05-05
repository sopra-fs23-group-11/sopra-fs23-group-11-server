package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.Cell;
import ch.uzh.ifi.hase.soprafs23.entity.Player;
import ch.uzh.ifi.hase.soprafs23.entity.Shot;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import ch.uzh.ifi.hase.soprafs23.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to
 * the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
 */
@Service
@Transactional
public class CellService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final CellRepository cellRepository;
    private final ShipPlayerRepository shipPlayerRepository;
    private final ShotRepository shotRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public CellService(@Qualifier("cellRepository") CellRepository cellRepository, ShipPlayerRepository shipPlayerRepository, ShotRepository shotRepository, PlayerRepository playerRepository) {
        this.cellRepository = cellRepository;
        this.shipPlayerRepository = shipPlayerRepository;
        this.shotRepository = shotRepository;
        this.playerRepository = playerRepository;
    }

    public List<Cell> getCells() {
        return this.cellRepository.findAll();
    }

    public void updateCells(Long ownerId) {
        List<Cell> cells = cellRepository.findAllByOwnerId(ownerId);
        for(Cell cell:cells){
            System.out.println(cell.getOccupyingShip());
            System.out.println(cell.getIsShotAt());
        }
        Player player = playerRepository.getOne(ownerId);
        List<ShipPlayer> ships = shipPlayerRepository.findAllByPlayer(player);
        Hashtable<String, Ship> positions= new Hashtable<>();
        for (ShipPlayer ship:ships){
            String startpos = ship.getStartPosition();
            String endpos = ship.getEndPosition();
            int starty = getIntFromString(startpos.charAt(0));
            int startx = Integer.parseInt(startpos.substring(1));
            int endy = getIntFromString(endpos.charAt(0));
            int endx = Integer.parseInt(endpos.substring(1));
            if(starty == endy){
                for (int i = startx; i <= endx; i++){
                    String pos = getCharForNumber(starty+1)+Integer.toString(i);
                    positions.put(pos, ship.getShip());
                }

            }else{
                for(int i = starty; i <= endy; i++){
                    String pos = getCharForNumber(i+1)+Integer.toString(startx);
                    positions.put(pos, ship.getShip());

                }
            }
        }

        List<Shot> shots = shotRepository.findAllByDefender(player);
        List<String> shotpos = new ArrayList<>();
        if(!shots.isEmpty()){
            for(Shot shot:shots){
                shotpos.add(shot.getPosition());
            }
        }

        for(Cell cell : cells){
            if (positions.containsKey(cell.getId())){
                cell.setOccupyingShip(positions.get(cell.getId()));
            }

            if (shotpos.contains(cell.getId())){
                cell.setIsShotAt(true);
            }

            cellRepository.flush();
        }
    }

    public String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char)(i + 'A' - 1)) : null;
    }

    public int getIntFromString(char ch){
        int pos = ch - 'A';
        return pos;
    }
}