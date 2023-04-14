package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.rest.dto.TextMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatroomController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public TextMessageDTO receiveMessage(@Payload TextMessageDTO textMessageDTO) {
        // receive message from client
        return textMessageDTO;
    }

//    @MessageMapping("/private-message")
//    public TextMessageDTO recMessage(@Payload TextMessageDTO textMessageDTO) {
//       messagingTemplate.convertAndSendToUser(textMessageDTO.getReceiverName(), "/private", textMessageDTO);
//               System.out.println(textMessageDTO.toString());
//               return textMessageDTO;
//    }
}
