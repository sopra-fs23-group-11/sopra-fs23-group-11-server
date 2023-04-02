package ch.uzh.ifi.hase.soprafs23.service;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;


@Service
@Transactional
public class LobbyService {
    private final Logger log = LoggerFactory.getLogger(LobbyService.class);
    private final LobbyRepository lobbyRepository;
    private final UserRepository userRepository;

    @Autowired
    public LobbyService(LobbyRepository lobbyRepository, UserRepository userRepository) {
        this.lobbyRepository = lobbyRepository;
        this.userRepository = userRepository;
    }

    public Lobby createLobby(long hostId){
        Optional <User> optionalUser= userRepository.findById(hostId);
        if (optionalUser.isPresent()){
            User host= optionalUser.get();
            Lobby newLobby= new Lobby();
            newLobby.setHost(host);
            newLobby.setLobbyCode(UUID.randomUUID().toString());
            Lobby lobby= lobbyRepository.save(newLobby);
            host.setLobbyForHost(lobby);
            userRepository.save(host);
            return lobby;
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User doesn't exist"));
        }

    }

    public User joinLobby(String lobbyCode, long userId) {
        Lobby lobby= lobbyRepository.findByLobbyCode(lobbyCode);
        Optional <User> optionalUser= userRepository.findById(userId);
        if (lobby != null && optionalUser.isPresent()){
            User user= optionalUser.get();
            user.setLobbyForJoiner(lobby);
            User newuser= userRepository.save(user);
            lobby.setJoiner(newuser);
            lobbyRepository.save(lobby);
            return newuser;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("lobby or user not found"));
        }
    }

    public Lobby findByLobbyCode(String lobbyCode){
        return lobbyRepository.findByLobbyCode(lobbyCode);
    }
}
