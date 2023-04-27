package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.Player;
import ch.uzh.ifi.hase.soprafs23.entity.Shot;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import ch.uzh.ifi.hase.soprafs23.exceptions.EntityNotFoundExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.PlayerExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.UserExcep;
import ch.uzh.ifi.hase.soprafs23.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs23.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;


@Service
public class LobbyService {
    private final Logger log = LoggerFactory.getLogger(LobbyService.class);
    private final LobbyRepository lobbyRepository;
    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public LobbyService(LobbyRepository lobbyRepository, UserRepository userRepository,
                        PlayerRepository playerRepository) {
        this.lobbyRepository = lobbyRepository;
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
    }

    public Lobby createLobby(long hostId) {
        Optional<User> optionalUser = userRepository.findById(hostId);
        if (optionalUser.isEmpty())
            throw new EntityNotFoundExcep("User doesn't exist", "");

        User host = optionalUser.get();
        Lobby newLobby = new Lobby();
        newLobby.setHost(host);
        String lobbyCode;
        do {
            lobbyCode = UUID.randomUUID().toString().substring(0,5);
        } while (lobbyRepository.findByLobbyCode(lobbyCode) != null);
        newLobby.setLobbyCode(lobbyCode);
        Lobby lobby = lobbyRepository.save(newLobby);
        host.setLobbyForHost(lobby);
        userRepository.save(host);
        //createPlayerEntity(hostId);
        return lobby;

    }

    public Lobby joinLobby(String lobbyCode, long userId) {
        System.out.println("service1");
        Lobby lobby = lobbyRepository.findByLobbyCode(lobbyCode);
        System.out.println("userId = " + userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        if (lobby == null)
            throw new EntityNotFoundExcep("lobby not found", "");
        long hostId = lobby.getHost().getId();
        System.out.println("service1.2");
        if (optionalUser.isEmpty())
            throw new EntityNotFoundExcep("joiner not found", lobbyCode);
        System.out.println("service1.3");
        User user = optionalUser.get();
        if (hostId == user.getId())
            throw new PlayerExcep("players should differ", lobbyCode);
        if (lobby.getJoiner()!=null)
            throw new UserExcep("lobby has a joiner");
        user.setLobbyForJoiner(lobby);
        User newuser = userRepository.save(user);
        lobby.setJoiner(newuser);
        lobbyRepository.save(lobby);
        System.out.println("service2");
        //createPlayerEntity(userId);
        return lobby;

    }
    public Lobby findByLobbyCode(String lobbyCode) {
        return lobbyRepository.findByLobbyCode(lobbyCode);
    }
/*
    public Lobby findByLobbyCode(String lobbyCode) {
        return lobbyRepository.findByLobbyCode(lobbyCode);
    }

    private void createPlayerEntity(long userId) {
        Optional<Player> optionalPlayer = playerRepository.findById(userId);

        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            player.setShipPlayers(new ArrayList<ShipPlayer>());
            player.setShipsRemaining(5);
            player.setShotsAttack(new ArrayList<Shot>());
            player.setShotsDefend(new ArrayList<Shot>());
            playerRepository.save(player);
        }
        else {
            Player newPlayer = new Player();
            newPlayer.setShipPlayers(new ArrayList<ShipPlayer>());
            newPlayer.setShipsRemaining(5);
            newPlayer.setShotsAttack(new ArrayList<Shot>());
            newPlayer.setShotsDefend(new ArrayList<Shot>());
            playerRepository.save(newPlayer);
        }
    }

 */
}
