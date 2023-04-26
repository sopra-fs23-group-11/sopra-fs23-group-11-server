package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.Application;
import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.Player;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import ch.uzh.ifi.hase.soprafs23.exceptions.EntityNotFoundExcep;
import ch.uzh.ifi.hase.soprafs23.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs23.rest.dto.ShipPlayerPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPutDTO;
import ch.uzh.ifi.hase.soprafs23.service.GameService;
import ch.uzh.ifi.hase.soprafs23.service.PlayerService;
import ch.uzh.ifi.hase.soprafs23.service.ShipPlayerService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;
import ch.uzh.ifi.hase.soprafs23.exceptions.UserExcep;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserControllerTest
 * This is a WebMvcTest which allows to test the UserController i.e. GET/POST
 * request without actually sending them over the network.
 * This tests if the UserController works.
 */



@WebMvcTest(ShipPlayerController.class)

public class ShipPlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShipPlayerService shipPlayerService;

    @MockBean
    private GameService gameService;

    @MockBean
    private SimpMessagingTemplate simpMessagingTemplate;

    @MockBean
    private PlayerService playerService;

    @Ignore
    public void placeShipsValidInput_thenShipsPlaced() throws Exception{
        ShipPlayer shipPlayer = new ShipPlayer();
        shipPlayer.setStartPosition("a");
        shipPlayer.setEndPosition("b");

        ShipPlayerPostDTO shipPlayerPostDTO = new ShipPlayerPostDTO();
        shipPlayerPostDTO.setStartPosition("a");
        shipPlayerPostDTO.setEndPosition("b");
        shipPlayerPostDTO.setShipPlayerShipId(1);
        shipPlayerPostDTO.setShipPlayerPlayerId(1);

        given(shipPlayerService.placeShip(1,1,"a","b", "hello")).willReturn(shipPlayer);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/ships/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(shipPlayerPostDTO));

        // for some reason post requests are not allowed
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated());
    }

    @Ignore
    public void placeShipInvalidInput() throws Exception {
        ShipPlayerPostDTO shipPlayerPostDTO = new ShipPlayerPostDTO();
        shipPlayerPostDTO.setStartPosition("a");
        shipPlayerPostDTO.setEndPosition("b");

        given(shipPlayerService.placeShip(1,1,"a","b", "hello")).willThrow(new ResponseStatusException(HttpStatus.CONFLICT));
        MockHttpServletRequestBuilder postRequest = post("/ships/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(shipPlayerPostDTO));

        mockMvc.perform(postRequest).andExpect(status().isConflict());

    }

    @Test
    public void getShipValidInput_returnsShip() throws Exception{
        Ship ship = new Ship();
        ship.setLength(2);
        ship.setType("HandsomeBoi");
        ship.setId(1L);

        Player player = new Player();
        player.setId(1L);
        player.setShipsRemaining(1);

        ShipPlayer shipPlayer = new ShipPlayer();
        shipPlayer.setStartPosition("a");
        shipPlayer.setEndPosition("b");
        shipPlayer.setPlayer(player);
        shipPlayer.setShip(ship);


        // this mocks the UserService -> we define above what the userService should
        // return when getUsers() is called
        given(shipPlayerService.getPlayerById(1L)).willReturn(player);
        // when
        MockHttpServletRequestBuilder getRequest = get("/ships/1").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void getShipsInvalidInput() throws Exception{
        given(shipPlayerService.getPlayerById(1L)).willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        MockHttpServletRequestBuilder getRequest = get("/ships/1").contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(getRequest).andExpect(status().isNotFound());
    }




    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The request body could not be created.%s", e.toString()));
        }
    }
}
