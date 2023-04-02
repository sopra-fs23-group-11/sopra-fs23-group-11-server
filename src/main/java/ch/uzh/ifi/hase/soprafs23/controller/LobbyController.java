package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.JoinLobbyPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.LobbyGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.LobbyPostDTO;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class LobbyController {
    private final LobbyService lobbyService;


    LobbyController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
    }

    //Create lobby
    // ToDo getDTO LobbyCode
    @PostMapping("/host")
    @ResponseStatus(HttpStatus.CREATED)
    public LobbyGetDTO createLobby(@RequestBody LobbyPostDTO lobbyPostDTO) {
        long hostId=lobbyPostDTO.getHostId();
        Lobby lobby= lobbyService.createLobby(hostId);
        return new LobbyGetDTO(lobby.getLobbyCode());
    }
   // ToDO change to put
    @PutMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public void joinLobby(@RequestBody JoinLobbyPostDTO joinLobby){
        String lobbyCode= joinLobby.getLobbyCode();
        Lobby lobby= lobbyService.findByLobbyCode(lobbyCode);
        if (lobby.getJoiner() != null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Lobby already has a joiner");
        }
        long userId= joinLobby.getUserId();
        if ((lobby.getHost().getId() == userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, String.format("Host and Joiner cannot be the same"));
        }

        lobbyService.joinLobby(joinLobby.getLobbyCode(), joinLobby.getUserId());
    }




}
