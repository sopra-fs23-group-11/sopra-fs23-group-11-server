package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.WebSockets.Message.JoinMessage;
import ch.uzh.ifi.hase.soprafs23.rest.dto.LobbyGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.LobbyPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.LobbyPutDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class LobbyController {
    private final LobbyService lobbyService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    LobbyController(LobbyService lobbyService, SimpMessagingTemplate simpMessagingTemplate) {
        this.lobbyService = lobbyService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    //Create lobby

    @PostMapping("/lobbies")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public LobbyGetDTO host(@RequestBody LobbyPostDTO lobbyPostDTO) {
        Lobby createdLobby = lobbyService.createLobby(lobbyPostDTO.getHostId());
        return DTOMapper.INSTANCE.convertEntityToLobbyGetDTO(createdLobby);
    }

    @PutMapping("/lobbies")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public LobbyGetDTO join(@RequestBody LobbyPutDTO lobbyPutDTO) {
        Lobby createdLobby = lobbyService.joinLobby(lobbyPutDTO.getLobbyCode(), lobbyPutDTO.getJoinerId());
        JoinMessage joinMessage = new JoinMessage(createdLobby.getLobbyCode(), createdLobby.getJoiner().getId(), createdLobby.getJoiner().getUsername());
        simpMessagingTemplate.convertAndSend("/join/" + createdLobby.getLobbyCode(), joinMessage);
        return DTOMapper.INSTANCE.convertEntityToLobbyGetDTO(createdLobby);
    }

    @GetMapping("/lobbies/{lobbyCode}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public LobbyGetDTO getLobby(@PathVariable("lobbyCode") String lobbyCode) {
        Lobby foundLobby = lobbyService.findByLobbyCode(lobbyCode);
        return DTOMapper.INSTANCE.convertEntityToLobbyGetDTO(foundLobby);
    }
}
