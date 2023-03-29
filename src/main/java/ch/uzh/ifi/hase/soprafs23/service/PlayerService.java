package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.Player;
import ch.uzh.ifi.hase.soprafs23.entity.User;
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

@Service
@Transactional
public class PlayerService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayers() {
        return this.playerRepository.findAll();
    }

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
}
