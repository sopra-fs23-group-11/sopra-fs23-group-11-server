package ch.uzh.ifi.hase.soprafs23.controller;
import ch.uzh.ifi.hase.soprafs23.WebSockets.Message.ChatMessage;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import ch.uzh.ifi.hase.soprafs23.rest.dto.ChatMessageDTO;


@RestController
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ChatController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/chat")
    public ChatMessage chat(@Payload ChatMessageDTO chatMessageDTO) {
        ChatMessage chatMessage = DTOMapper.INSTANCE.convertChatMessageDTOtoEntitiy(chatMessageDTO);
        simpMessagingTemplate.convertAndSend("/chatroom/" + chatMessage.getLobbyCode(), chatMessage);
        return DTOMapper.INSTANCE.convertChatMessageDTOtoEntitiy(chatMessageDTO);
    }
}
