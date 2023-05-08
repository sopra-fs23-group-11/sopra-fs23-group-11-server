
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
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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


    @MessageMapping("/game")
    public ShotMessage attack(ShotPostDTO shotPostDTO){

        Shot shot=gameService.attack(shotPostDTO.getAttackerId(), shotPostDTO.getDefenderId(), shotPostDTO.getPosOfShot(), shotPostDTO.getGameId());
        ShotMessage shotMessage= DTOMapper.INSTANCE.convertEntityToShotMessage(shot);
        simpMessagingTemplate.convertAndSend("/game/" + shotPostDTO.getGameId() + "/" + shotPostDTO.getDefenderId(), shotMessage);
        if (gameService.hasShipSunk(shotPostDTO.getPosOfShot(), shotPostDTO.getDefenderId())){
            SunkMsg sunkMsg= new SunkMsg(shotMessage.getDefenderId());
            simpMessagingTemplate.convertAndSend("/game/" + shotPostDTO.getGameId() , sunkMsg);
        }
        if (gameService.looserAlert(shotMessage.getDefenderId(), shotPostDTO.getGameId())) {
            FinishMsg finishMsg = new FinishMsg(shotMessage.getAttackerId(), shotMessage.getDefenderId());
            simpMessagingTemplate.convertAndSend("/game/" + shotPostDTO.getGameId() , finishMsg);
        }

        return shotMessage;
    }
    @MessageMapping("/ready")
    public void ready (@Payload ReadyPostDTO readyPostDTO){
        ReadyMsg readyMsg = new ReadyMsg(readyPostDTO.getPlayerId(), readyPostDTO.getPlayerName());
        simpMessagingTemplate.convertAndSend("/ready/" + readyPostDTO.getPlayerName(), readyMsg);

    }

    @PostMapping("/startgame")
    public GameGetDTO startGame(@RequestBody GamePostDTO gamePostDTO){
        System.out.println("gamePostDTO.getLobbyCode() = " + gamePostDTO.getLobbyCode());
        Game game = gameService.startGame(gamePostDTO.getHostId(), gamePostDTO.getLobbyCode());
        StartGameMessage startGameMessage= new StartGameMessage(game.getId(), game.getPlayer1().getId(),
                game.getPlayer2().getId(), game.getPlayer1().getUser().getUsername(), game.getPlayer2().getUser().getUsername());
        simpMessagingTemplate.convertAndSend("/startgame/" + game.getId(), startGameMessage);
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(game);

    }

    @MessageExceptionHandler(EntityNotFoundExcep.class)
    public void handleEntityNotFoundExcep(EntityNotFoundExcep excep){
        //ErrorDTO errorDTO= new ErrorDTO(excep.getMessage());
        ErrorMsg errorMsg= new ErrorMsg(excep.getMessage());
        simpMessagingTemplate.convertAndSend("/game/" + excep.getGameId(), errorMsg);

    }
    @MessageExceptionHandler(PositionExcep.class)
    public void handlePositionExcep(PositionExcep excep){
        //ErrorDTO errorDTO= new ErrorDTO(excep.getMessage());
        ErrorMsg errorMsg= new ErrorMsg(excep.getMessage());
        simpMessagingTemplate.convertAndSend("/game/" + excep.getGameId(), errorMsg);

    }
    @MessageExceptionHandler(PlayerExcep.class)
    public void handlePlayerExcep(PlayerExcep excep){
        ErrorMsg errorMsg= new ErrorMsg(excep.getMessage());
        simpMessagingTemplate.convertAndSend("/game/" + excep.getGameId(), errorMsg);
    }
}


