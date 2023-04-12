package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.Player;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;
import ch.uzh.ifi.hase.soprafs23.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import ch.uzh.ifi.hase.soprafs23.entity.Helper;

@Service
@Transactional
public class PlayerService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final PlayerRepository playerRepository;

    private  final Helper helper;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, Helper helper) {
        this.playerRepository = playerRepository;
        this.helper = helper;
    }

    public List<Player> getPlayers() {
        return this.playerRepository.findAll();
    }
/*
    public Player createPlayer(Player newPlayer) {
        newPlayer.setToken(UUID.randomUUID().toString());
        checkIfPlayerExistsRegister(newPlayer);
        newPlayer = playerRepository.save(newPlayer);
        playerRepository.flush();
        log.debug("Created Information for User: {}", newPlayer);
        return newPlayer;
    }

    public Player loginPlayer(Player player){
        checkIfPlayerExistsLogin(player);
        Player logUser = playerRepository.findByName(player.getName());
        logUser.setToken(UUID.randomUUID().toString());
        playerRepository.flush();
        return logUser;
    }

    private void checkIfPlayerExistsRegister(Player userToBeCreated) {
        Player playerByName = playerRepository.findByName(userToBeCreated.getName());
        String baseErrorMessage = "The %s provided %s not unique. Therefore, the user could not be created!";
        if (playerByName != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage, "name", "is"));
        }
    }

    private void checkIfPlayerExistsLogin(Player userToBeCreated) {
        Player playerByName = playerRepository.findByName(userToBeCreated.getName());
        String baseErrorMessage = "Login failed: %s";
        if (playerByName == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format(baseErrorMessage, "Username does not exist"));
        } else if (!Objects.equals(playerByName.getPassword(), userToBeCreated.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format(baseErrorMessage, "Wrong Password"));
        }
    }

 */

    //ToDo: needed to be modified so that inheritance works
/*
   public void validateInput(Player player){
        List<Ship> ships = player.getShips();
        String baseErrorMessage = "Submit failed: %s";
        //TODO: adjust null values to proper method input
        for(Ship ship : ships) {
            if (!helper.isValidLengthForShip(ship)) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, String.format(baseErrorMessage, "Illegal Ship length"));
            }
            if (!helper.shipIsWithinBoundary(ship)) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, String.format(baseErrorMessage, "Illegal Ship position"));
            }
        }
    }


 */

    public String convertInputToUsableString(List<Ship> input){
        //TODO: convert input to usable String that works with helper methods
        return null;
    }

    public Player getField(long id){
        Player playerById = playerRepository.getOne(id);
        return playerById;
    }
}
