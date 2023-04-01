package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.JoinLobbyPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.LobbyPostDTO;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public String createLobby(@RequestBody LobbyPostDTO lobbyPostDTO) {
        long hostId=lobbyPostDTO.getHostId();
        Lobby lobby= lobbyService.createLobby(hostId);
        return lobby.getLobbyCode();
    }
   // ToDO change to put
    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public void joinLobby(@RequestBody JoinLobbyPostDTO joinLobby ) {
        lobbyService.joinLobby(joinLobby.getLobbyCode(), joinLobby.getUserId());
    }




}
