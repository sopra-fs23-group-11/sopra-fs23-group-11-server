package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.WebSockets.Message.JoinMessage;
import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.Player;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import ch.uzh.ifi.hase.soprafs23.exceptions.EntityNotFoundExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.PlayerExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.PositionExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.UserExcep;
import ch.uzh.ifi.hase.soprafs23.rest.dto.*;
import ch.uzh.ifi.hase.soprafs23.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LobbyControllerTest {
    WebSocketStompClient stompClient;
    @Value("${local.server.port}")
    private int port;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LobbyService lobbyService;
    @MockBean
    private SimpMessagingTemplate simpMessagingTemplate;

    @BeforeEach
    void setup() {
        stompClient = new WebSocketStompClient(new SockJsClient(
                Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient()))));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

    }
    @Test
    public void hostValid() throws Exception {
        Lobby lobby = new Lobby();
        lobby.setLobbyCode("**");
        User host = new User();
        lobby.setHost(host);
        host.setId(1L);
        host.setLobbyForHost(lobby);
        host.setTotalWins(0);
        host.setPassword("mind your own business");
        host.setToken("TryToFindItIfInterested");
        host.setStatus(UserStatus.valueOf("ONLINE"));
        host.setUsername("Sara");
        lobby.setHost(host);

        LobbyPostDTO lobbyPostDTO = new LobbyPostDTO();
        lobbyPostDTO.setHostId(1L);

        given(lobbyService.createLobby(Mockito.anyLong())).willReturn(lobby);
        MockHttpServletRequestBuilder postRequest = post("/lobbies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(lobbyPostDTO));

        mockMvc.perform(postRequest)
                .andExpect(status().isCreated());
    }

    @Test
    public void inValidHost() throws Exception {
        Lobby lobby = new Lobby();
        lobby.setLobbyCode("**");
        User host = new User();
        lobby.setHost(host);
        host.setId(1L);
        host.setLobbyForHost(lobby);
        host.setTotalWins(0);
        host.setPassword("mind your own business");
        host.setToken("TryToFindItIfInterested");
        host.setStatus(UserStatus.valueOf("ONLINE"));
        host.setUsername("Sara");
        lobby.setHost(host);

        LobbyPostDTO lobbyPostDTO = new LobbyPostDTO();
        lobbyPostDTO.setHostId(1L);

        given(lobbyService.createLobby(Mockito.anyLong())).willThrow(new EntityNotFoundExcep(
                "Don't look for the entity. The test works", ""));
        MockHttpServletRequestBuilder postRequest = post("/lobbies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(lobbyPostDTO));

        mockMvc.perform(postRequest).andExpect(status().isNotFound());
    }

    @Test
    public void validJoin() throws Exception {
        Lobby lobby = new Lobby();
        lobby.setLobbyCode("**");
        User joiner = new User();
        User host = new User();
        lobby.setJoiner(joiner);
        lobby.setHost(host);
        host.setId(1L);
        joiner.setId(2L);
        joiner.setLobbyForJoiner(lobby);
        joiner.setTotalWins(0);
        joiner.setPassword("mind your own business");
        joiner.setToken("TryToFindItIfInterested");
        joiner.setStatus(UserStatus.valueOf("ONLINE"));
        joiner.setUsername("Sara");


        LobbyPutDTO lobbyPostDTO = new LobbyPutDTO();
        lobbyPostDTO.setLobbyCode("**");
        lobbyPostDTO.setJoinerId(1L);

        given(lobbyService.joinLobby(Mockito.anyString(), Mockito.anyLong())).willReturn(lobby);
        MockHttpServletRequestBuilder putRequest = MockMvcRequestBuilders.put("/lobbies")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(lobbyPostDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void validJoin2() throws Exception {
        BlockingQueue<JoinMessage> bq = new LinkedBlockingDeque<>();
        Lobby lobby = new Lobby();
        lobby.setLobbyCode("**");
        User joiner = new User();
        User host = new User();
        lobby.setJoiner(joiner);
        lobby.setHost(host);
        host.setId(1L);
        joiner.setId(2L);
        joiner.setLobbyForJoiner(lobby);
        joiner.setTotalWins(0);
        joiner.setPassword("mind your own business");
        joiner.setToken("TryToFindItIfInterested");
        joiner.setStatus(UserStatus.valueOf("ONLINE"));
        joiner.setUsername("Sara");


        StompSession session = stompClient
                .connect("ws://localhost:" + port + "/ws", new StompSessionHandlerAdapter() {
                })
                .get(10, SECONDS);
        System.out.println(port);
        session.subscribe("/join/" +lobby.getLobbyCode(), new StompFrameHandler() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                System.out.println("...............");

                return JoinMessage.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("::::::::::::");
                Assertions.assertNotNull(payload);
                System.out.println(payload);
            }
        });

        LobbyPutDTO lobbyPostDTO = new LobbyPutDTO();
        lobbyPostDTO.setLobbyCode("**");
        lobbyPostDTO.setJoinerId(1L);

        JoinMessage response = bq.poll(2, SECONDS);
        System.out.println(response);
        given(lobbyService.joinLobby(Mockito.anyString(), Mockito.anyLong())).willReturn(lobby);
        MockHttpServletRequestBuilder putRequest = MockMvcRequestBuilders.put("/lobbies")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(lobbyPostDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void inValidJoinEntityNotFound() throws Exception {
        Lobby lobby = new Lobby();
        lobby.setLobbyCode("**");
        User joiner = new User();
        User host = new User();
        lobby.setJoiner(joiner);
        lobby.setHost(host);
        host.setId(1L);
        joiner.setId(2L);
        joiner.setLobbyForJoiner(lobby);
        joiner.setTotalWins(0);
        joiner.setPassword("mind your own business");
        joiner.setToken("TryToFindItIfInterested");
        joiner.setStatus(UserStatus.valueOf("ONLINE"));
        joiner.setUsername("Sara");

        LobbyPutDTO lobbyPostDTO = new LobbyPutDTO();
        lobbyPostDTO.setLobbyCode("**");
        lobbyPostDTO.setJoinerId(1L);

        given(lobbyService.joinLobby(Mockito.anyString(), Mockito.anyLong())).willThrow(
                new EntityNotFoundExcep("Entity Not Found", ""));
        MockHttpServletRequestBuilder putRequest = MockMvcRequestBuilders.put("/lobbies")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(lobbyPostDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isNotFound());
    }

    @Test
    public void inValidJoinPlayerExc() throws Exception {
        Lobby lobby = new Lobby();
        lobby.setLobbyCode("**");
        User joiner = new User();
        User host = new User();
        lobby.setJoiner(joiner);
        lobby.setHost(host);
        host.setId(1L);
        joiner.setId(2L);
        joiner.setLobbyForJoiner(lobby);
        joiner.setTotalWins(0);
        joiner.setPassword("mind your own business");
        joiner.setToken("TryToFindItIfInterested");
        joiner.setStatus(UserStatus.valueOf("ONLINE"));
        joiner.setUsername("Sara");

        LobbyPutDTO lobbyPostDTO = new LobbyPutDTO();
        lobbyPostDTO.setLobbyCode("**");
        lobbyPostDTO.setJoinerId(1L);

        given(lobbyService.joinLobby(Mockito.anyString(), Mockito.anyLong())).willThrow(
                new PlayerExcep("Entity Not Found", ""));
        MockHttpServletRequestBuilder putRequest = MockMvcRequestBuilders.put("/lobbies")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(lobbyPostDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void inValidJoinUserExc() throws Exception {
        Lobby lobby = new Lobby();
        lobby.setLobbyCode("**");
        User joiner = new User();
        User host = new User();
        lobby.setJoiner(joiner);
        lobby.setHost(host);
        host.setId(1L);
        joiner.setId(2L);
        joiner.setLobbyForJoiner(lobby);
        joiner.setTotalWins(0);
        joiner.setPassword("mind your own business");
        joiner.setToken("TryToFindItIfInterested");
        joiner.setStatus(UserStatus.valueOf("ONLINE"));
        joiner.setUsername("Sara");

        LobbyPutDTO lobbyPostDTO = new LobbyPutDTO();
        lobbyPostDTO.setLobbyCode("**");
        lobbyPostDTO.setJoinerId(1L);

        given(lobbyService.joinLobby(Mockito.anyString(), Mockito.anyLong())).willThrow(
                new UserExcep("Entity Not Found"));
        MockHttpServletRequestBuilder putRequest = MockMvcRequestBuilders.put("/lobbies")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(lobbyPostDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isConflict());
    }

    @Test
    public void getLobbyTest() throws Exception {
        Lobby lobby = new Lobby();
        lobby.setLobbyCode("**");
        User host = new User();
        lobby.setHost(host);
        host.setId(1L);

        LobbyGetDTO lobbyGetDTO = new LobbyGetDTO();
        lobbyGetDTO.setHostId(1L);
        lobbyGetDTO.setLobbyCode("**");

        given(lobbyService.findByLobbyCode(Mockito.anyString())).willReturn(lobby);
        MockHttpServletRequestBuilder getRequest = get("/lobbies/" + lobby.getLobbyCode());


        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lobbyCode", is(lobbyGetDTO.getLobbyCode())));

    }

    @Test
    public void getLobbyNotFoundTest() throws Exception {
        Lobby lobby = new Lobby();
        lobby.setLobbyCode("**");
        User host = new User();
        lobby.setHost(host);
        host.setId(1L);

        LobbyGetDTO lobbyGetDTO = new LobbyGetDTO();
        lobbyGetDTO.setHostId(1L);
        lobbyGetDTO.setLobbyCode("**");

        given(lobbyService.findByLobbyCode(Mockito.anyString())).willThrow(new EntityNotFoundExcep("not found", "*"));
        MockHttpServletRequestBuilder getRequest = get("/lobbies/" + lobby.getLobbyCode());
        mockMvc.perform(getRequest)
                .andExpect(status().isNotFound());

    }


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
