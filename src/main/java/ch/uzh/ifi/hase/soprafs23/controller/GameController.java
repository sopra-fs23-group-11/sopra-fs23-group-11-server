
package ch.uzh.ifi.hase.soprafs23.controller;


import ch.uzh.ifi.hase.soprafs23.WebSockets.Message.FinishMsg;
import ch.uzh.ifi.hase.soprafs23.WebSockets.Message.ReadyMsg;
import ch.uzh.ifi.hase.soprafs23.entity.Game;
import ch.uzh.ifi.hase.soprafs23.entity.Shot;
import ch.uzh.ifi.hase.soprafs23.exceptions.EntityNotFoundExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.PlayerExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.PositionExcep;
import ch.uzh.ifi.hase.soprafs23.WebSockets.Message.ShotMessage;
import ch.uzh.ifi.hase.soprafs23.WebSockets.Message.StartGameMessage;
import ch.uzh.ifi.hase.soprafs23.rest.dto.*;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
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


    @MessageMapping("/game")
    //ToDO send message to the own game not every game /shot/gameId
    public ShotMessage attack(ShotPostDTO shotPostDTO){

        Shot shot=gameService.attack(shotPostDTO.getAttackerId(), shotPostDTO.getDefenderId(), shotPostDTO.getPosOfShot());
        ShotMessage newshotGet= new ShotMessage();
        newshotGet.setAttackerId(shot.getAttacker().getId());
        newshotGet.setDefenderId(shot.getDefender().getId());
        newshotGet.setPosOfShot(shot.getPosition());
        newshotGet.setHit(shot.isHit());
        simpMessagingTemplate.convertAndSend("/shot", newshotGet);
        if (gameService.looserAlert(newshotGet.getDefenderId())) {
            FinishMsg finishMsg = new FinishMsg(newshotGet.getAttackerId(), newshotGet.getDefenderId());
            simpMessagingTemplate.convertAndSend("/game/", finishMsg);
        }

        return newshotGet;
    }
    @PostMapping("/ready")
    public void ready (@RequestBody ReadyPostDTO readyPostDTO){
        ReadyMsg readyMsg = new ReadyMsg(readyPostDTO.getPlayerId(), readyPostDTO.getPlayerName());
        simpMessagingTemplate.convertAndSend("/game/" + readyPostDTO.getGameId(), readyMsg);

    }

    @PostMapping("/game")
    public GameGetDTO startGame(@RequestBody GamePostDTO gamePostDTO){
        System.out.println("gamePostDTO.getLobbyCode() = " + gamePostDTO.getLobbyCode());
        Game game = gameService.startGame(gamePostDTO.getHostId(), gamePostDTO.getLobbyCode());
        StartGameMessage startGameMessage= new StartGameMessage(game.getId(), game.getPlayer1().getId(),
                game.getPlayer2().getId(), game.getPlayer1().getUser().getUsername(), game.getPlayer2().getUser().getUsername());
        simpMessagingTemplate.convertAndSend("/game/" + game.getId(), startGameMessage);
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(game);

    }
    // ToDo errors in game (only 2 channels needed
    @MessageExceptionHandler(EntityNotFoundExcep.class)
    public void handleEntityNotFoundExcep(EntityNotFoundExcep excep){
        ErrorDTO errorDTO= new ErrorDTO(excep.getMessage());
        simpMessagingTemplate.convertAndSend("/errors", errorDTO);
        System.out.println("..entity");
    }
    @MessageExceptionHandler(PositionExcep.class)
    public void handlePositionExcep(PositionExcep excep){
        ErrorDTO errorDTO= new ErrorDTO(excep.getMessage());
        simpMessagingTemplate.convertAndSend("/errors", errorDTO);
        System.out.println("..pos");
    }
    @MessageExceptionHandler(PlayerExcep.class)
    public void handlePlayerExcep(PlayerExcep excep){
        ErrorDTO errorDTO= new ErrorDTO(excep.getMessage());
        simpMessagingTemplate.convertAndSend("/errors", errorDTO);
        System.out.println("..player");
    }
}


