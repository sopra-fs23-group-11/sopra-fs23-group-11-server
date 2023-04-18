package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.rest.dto.ShotDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.TextMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatroomController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @MessageMapping("lobby/{lobbyId}/message")
    public TextMessageDTO receiveMessage(@Payload TextMessageDTO textMessageDTO, @DestinationVariable String lobbyId) {
        messagingTemplate.convertAndSend("/chatroom/" + lobbyId, textMessageDTO);
        // receive message from client
        return textMessageDTO;
    }

    @MessageMapping("/game-simple")
    public ShotDTO receiveShot(@Payload ShotDTO shotDTO) {
        messagingTemplate.convertAndSend("/shot-simple", shotDTO);

        return shotDTO;
    }

//    @MessageMapping("/private-message")
//    public TextMessageDTO recMessage(@Payload TextMessageDTO textMessageDTO) {
//       messagingTemplate.convertAndSendToUser(textMessageDTO.getReceiverName(), "/private", textMessageDTO);
//               System.out.println(textMessageDTO.toString());
//               return textMessageDTO;
//    }
}
