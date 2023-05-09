package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.WebSockets.Message.ShotMessage;
import ch.uzh.ifi.hase.soprafs23.entity.*;
import ch.uzh.ifi.hase.soprafs23.exceptions.EntityNotFoundExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.PlayerExcep;
import ch.uzh.ifi.hase.soprafs23.rest.dto.GamePostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.ShotPostDTO;
import ch.uzh.ifi.hase.soprafs23.service.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;


    @MockBean
    private SimpMessagingTemplate simpMessagingTemplate;
    @Test
    public void testStartGame() throws Exception {
        Game game = new Game();
        game.setId("**");
        Player player1 = new Player();
        player1.setId(1L);
        User user1=new User();
        user1.setUsername("Sara");
        player1.setUser(user1);
        game.setPlayer1(player1);

        Player player2 = new Player();
        User user2=new User();
        user2.setUsername("Sara2");
        player2.setUser(user2);
        player2.setId(2L);
        game.setPlayer2(player2);

        GamePostDTO gamePostDTO = new GamePostDTO();
        gamePostDTO.setHostId(1L);
        gamePostDTO.setLobbyCode("**");


        given(gameService.startGame(Mockito.anyLong(), Mockito.anyString())).willReturn(game);
        MockHttpServletRequestBuilder postRequest = post("/startgame")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(gamePostDTO));

        mockMvc.perform(postRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void testStartGameEntityNotFound() throws Exception {

        GamePostDTO gamePostDTO = new GamePostDTO();
        gamePostDTO.setHostId(1L);
        gamePostDTO.setLobbyCode("**");

        given(gameService.startGame(Mockito.anyLong(), Mockito.anyString())).willThrow(new EntityNotFoundExcep("An Entity is missed", "ID"));
        MockHttpServletRequestBuilder postRequest = post("/startgame")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(gamePostDTO));

        mockMvc.perform(postRequest)
                .andExpect(status().isNotFound());
    }

    @Test
    public void testStartGamePlayerExcep() throws Exception {
        GamePostDTO gamePostDTO = new GamePostDTO();
        gamePostDTO.setHostId(1L);
        gamePostDTO.setLobbyCode("**");

        given(gameService.startGame(Mockito.anyLong(), Mockito.anyString())).willThrow(new PlayerExcep("host has to start", "ID"));
        MockHttpServletRequestBuilder postRequest = post("/startgame")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(gamePostDTO));

        mockMvc.perform(postRequest)
                .andExpect(status().isNotAcceptable());
    }
/*
    @Test
    public void attackTestValidInput() throws Exception{
        ShotPostDTO shotPostDTO = new ShotPostDTO();
        shotPostDTO.setAttackerId(1L);
        shotPostDTO.setAttackerId(2L);
        shotPostDTO.setPosOfShot("A1");
        shotPostDTO.setGameId("***");

        Shot shot = new Shot();
        Player attacker= new Player();
        attacker.setId(1L);
        shot.setAttacker(attacker);
        Player defender = new Player();
        defender.setId(2L);
        shot.setDefender(defender);
        shot.setPosition("A1");

        ShotMessage expectedShotMessage = new ShotMessage();
        expectedShotMessage.setAttackerId(1L);
        expectedShotMessage.setDefenderId(2L);
        expectedShotMessage.setPosOfShot("A1");


    }

 */


    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The request body could not be created.%s", e.toString()));
        }
    }
}


