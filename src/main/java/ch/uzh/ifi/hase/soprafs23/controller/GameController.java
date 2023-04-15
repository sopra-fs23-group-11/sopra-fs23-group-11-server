
package ch.uzh.ifi.hase.soprafs23.controller;


import ch.uzh.ifi.hase.soprafs23.entity.Shot;
import ch.uzh.ifi.hase.soprafs23.rest.dto.ShotGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.ShotPostDTO;
import ch.uzh.ifi.hase.soprafs23.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    public ShotGetDTO attack(ShotPostDTO shotPostDTO){
        System.out.println("confirm0");
        System.out.println(shotPostDTO.getAttackerId());
        System.out.println(shotPostDTO.getPosOfShot());
        System.out.println(shotPostDTO.getDefenderId());
        Shot shot=gameService.attack(shotPostDTO.getAttackerId(), shotPostDTO.getDefenderId(), shotPostDTO.getPosOfShot());
        ShotGetDTO newshotGet= new ShotGetDTO();
        newshotGet.setAttackerId(shot.getAttacker().getId());
        newshotGet.setDefenderId(shot.getDefender().getId());
        newshotGet.setPosOfShot(shot.getPosition());
        newshotGet.setHit(shot.isHit());
        simpMessagingTemplate.convertAndSend("/shot", newshotGet);
        System.out.println("confirm");
        return newshotGet;
    }
}


