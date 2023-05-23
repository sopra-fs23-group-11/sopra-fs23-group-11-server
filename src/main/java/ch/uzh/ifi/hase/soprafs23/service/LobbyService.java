package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.exceptions.EntityNotFoundExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.PlayerExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.UserExcep;
import ch.uzh.ifi.hase.soprafs23.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class LobbyService {
    private final LobbyRepository lobbyRepository;
    private final UserRepository userRepository;

    @Autowired
    public LobbyService(LobbyRepository lobbyRepository, UserRepository userRepository) {
        this.lobbyRepository = lobbyRepository;
        this.userRepository = userRepository;
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
            lobbyCode = UUID.randomUUID().toString().substring(0, 5);
        } while (lobbyRepository.findByLobbyCode(lobbyCode) != null);
        newLobby.setLobbyCode(lobbyCode);
        Lobby lobby = lobbyRepository.save(newLobby);
        host.setLobbyForHost(lobby);
        userRepository.save(host);
        return lobby;

    }

    public Lobby joinLobby(String lobbyCode, long userId) {
        Lobby lobby = lobbyRepository.findByLobbyCode(lobbyCode);
        Optional<User> optionalUser = userRepository.findById(userId);
        if (lobby == null)
            throw new EntityNotFoundExcep("Lobby not found", "");
        long hostId = lobby.getHost().getId();
        if (optionalUser.isEmpty())
            throw new EntityNotFoundExcep("Joiner not found", lobbyCode);
        User user = optionalUser.get();
        if (hostId == user.getId())
            throw new PlayerExcep("A player cannot be the host and the joiner at the same time", lobbyCode);
        if (lobby.getJoiner() != null)
            throw new UserExcep("Lobby is already full");
        user.setLobbyForJoiner(lobby);
        User newuser = userRepository.save(user);
        lobby.setJoiner(newuser);
        lobbyRepository.save(lobby);
        return lobby;

    }

    public Lobby findByLobbyCode(String lobbyCode) {
        Lobby lobby= lobbyRepository.findByLobbyCode(lobbyCode);
        if (lobby == null)
            throw new EntityNotFoundExcep("Lobby not found", lobbyCode);
        return lobby;
    }

}
