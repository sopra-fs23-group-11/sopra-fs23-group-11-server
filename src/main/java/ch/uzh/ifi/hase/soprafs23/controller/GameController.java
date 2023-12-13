
package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.WebSockets.Message.*;
import ch.uzh.ifi.hase.soprafs23.entity.Game;
import ch.uzh.ifi.hase.soprafs23.entity.Shot;
import ch.uzh.ifi.hase.soprafs23.exceptions.EntityNotFoundExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.PlayerExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.PositionExcep;
import ch.uzh.ifi.hase.soprafs23.rest.dto.*;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final GameService gameService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public GameController(GameService gameService, SimpMessagingTemplate simpMessagingTemplate) {
        this.gameService = gameService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

//
    @MessageMapping("/game")
    public ShotMessage attack(ShotPostDTO shotPostDTO) {

        Shot shot = gameService.attack(shotPostDTO.getAttackerId(), shotPostDTO.getDefenderId(), shotPostDTO.getPosOfShot(), shotPostDTO.getGameId());
        ShotMessage shotMessage = DTOMapper.INSTANCE.convertEntityToShotMessage(shot);
        simpMessagingTemplate.convertAndSend("/game/" + shotPostDTO.getGameId() + "/" + shotPostDTO.getDefenderId(), shotMessage);

        return shotMessage;
    }

    @MessageMapping("/ready")
    public void ready(@Payload ReadyPostDTO readyPostDTO) {
        ReadyMsg readyMsg = new ReadyMsg(readyPostDTO.getPlayerId(), readyPostDTO.getPlayerName(), readyPostDTO.getPlayerBoard(), readyPostDTO.getPlayerAvatar());
        simpMessagingTemplate.convertAndSend("/ready/" + readyPostDTO.getPlayerName(), readyMsg);

    }

    @MessageMapping("/leave")
    public void leave(@Payload LeaveMsgDTO leaveMsgDTO) {
        simpMessagingTemplate.convertAndSend("/game/" + leaveMsgDTO.getLobbyCode() + "/leave", leaveMsgDTO);
    }

    @PostMapping("/startgame")
    public GameGetDTO startGame(@RequestBody GamePostDTO gamePostDTO) {
        Game game = gameService.startGame(gamePostDTO.getHostId(), gamePostDTO.getLobbyCode());
        StartGameMessage startGameMessage = new StartGameMessage(game.getId(), game.getPlayer1().getId(),
                game.getPlayer2().getId(), game.getPlayer1().getUser().getUsername(), game.getPlayer2().getUser().getUsername());
        simpMessagingTemplate.convertAndSend("/startgame/" + game.getId(), startGameMessage);
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(game);

    }

    @MessageMapping("/newgame")
    public void newGame(@Payload NewGameDTO newGameDTO) {
        simpMessagingTemplate.convertAndSend("/game/" + newGameDTO.getLobbyCode()  + "/" + newGameDTO.getEnemyId() + "/newgame", newGameDTO);
    }

    @MessageMapping("/chat")
    public ChatMessage chat(@Payload ChatMessageDTO chatMessageDTO) {
        ChatMessage chatMessage = DTOMapper.INSTANCE.convertChatMessageDTOtoEntity(chatMessageDTO);
        simpMessagingTemplate.convertAndSend("/chatroom/" + chatMessage.getLobbyCode(), chatMessage);
        return DTOMapper.INSTANCE.convertChatMessageDTOtoEntity(chatMessageDTO);
    }

    @MessageExceptionHandler(EntityNotFoundExcep.class)
    public void handleEntityNotFoundExcep(EntityNotFoundExcep excep) {
        ErrorMsg errorMsg = new ErrorMsg(excep.getMessage());
        simpMessagingTemplate.convertAndSend("/game/" + excep.getGameId(), errorMsg);

    }

    @MessageExceptionHandler(PositionExcep.class)
    public void handlePositionExcep(PositionExcep excep) {
        ErrorMsg errorMsg = new ErrorMsg(excep.getMessage());
        simpMessagingTemplate.convertAndSend("/game/" + excep.getGameId(), errorMsg);

    }

    @MessageExceptionHandler(PlayerExcep.class)
    public void handlePlayerExcep(PlayerExcep excep) {
        ErrorMsg errorMsg = new ErrorMsg(excep.getMessage());
        simpMessagingTemplate.convertAndSend("/game/" + excep.getGameId(), errorMsg);
    }
}


