package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.exceptions.EntityNotFoundExcep;
import ch.uzh.ifi.hase.soprafs23.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LobbyServiceTest {

    @Mock
    private LobbyRepository lobbyRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LobbyService lobbyService;
    private Lobby testCreateLobby;
    private Lobby lobbyWithJoiner;
    private User testHost;
    private User testJoiner;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // given
        testCreateLobby = new Lobby();
        lobbyWithJoiner= new Lobby();
        testHost = new User();
        testJoiner = new User();
        testHost.setId(1L);
        testHost.setPassword("**");
        testHost.setStatus(UserStatus.ONLINE);
        testHost.setUsername("Ahmad");
        testJoiner.setId(2L);
        testCreateLobby.setLobbyCode("***");
        lobbyWithJoiner.setLobbyCode("***");
        testCreateLobby.setHost(testHost);
        lobbyWithJoiner.setHost(testHost);
        lobbyWithJoiner.setJoiner(testJoiner);
        //testCreateLobby.setJoiner(testJoiner);
        //testUser.setStatus(UserStatus.ONLINE);

        // when -> any object is being save in the userRepository -> return the dummy
        // testUser
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(testHost));
        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.ofNullable(testJoiner));
        Mockito.when(lobbyRepository.save(Mockito.any())).thenReturn(testCreateLobby);

    }

    @Test
    public void createLobby_validInputs_success() {

        Lobby createdLobby = lobbyService.createLobby(testHost.getId());

        // then
        Mockito.verify(lobbyRepository, Mockito.times(1)).save(Mockito.any());

        assertNotNull(createdLobby.getLobbyCode());
        assertEquals(testHost.getId(), createdLobby.getHost().getId());

    }

    @Test
    public void createLobby_InvalidInputs() {

        Lobby createdLobby = lobbyService.createLobby(testHost.getId());
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // then
        Mockito.verify(lobbyRepository, Mockito.times(1)).save(Mockito.any());
        assertThrows(EntityNotFoundExcep.class, () -> lobbyService.createLobby(testHost.getId()));

    }

    @Ignore
    public void joinLobby_validInputs_success() {
        System.out.println("sha3'lee");
        if (testCreateLobby==null)
            System.out.println("sha3'leh");

        Lobby createdLobby = lobbyService.joinLobby(testCreateLobby.getLobbyCode(), testJoiner.getId());
        Mockito.when(lobbyRepository.findByLobbyCode(Mockito.anyString())).thenReturn(testCreateLobby);

        // then
        Mockito.when(lobbyRepository.save(Mockito.any())).thenReturn(lobbyWithJoiner);

        Mockito.verify(lobbyRepository, Mockito.times(1)).save(Mockito.any());


        assertNotNull(createdLobby.getLobbyCode());
        assertEquals(testHost.getId(), createdLobby.getHost().getId());
        assertEquals(testJoiner.getId(), lobbyWithJoiner.getJoiner().getId());

    }
}
