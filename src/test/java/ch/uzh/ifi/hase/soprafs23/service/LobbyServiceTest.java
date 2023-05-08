package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.exceptions.EntityNotFoundExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.PlayerExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.UserExcep;
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
        lobbyWithJoiner = new Lobby();
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


    @Test
    public void joinLobby_validInputs_success() {

        Mockito.when(lobbyRepository.findByLobbyCode(Mockito.anyString())).thenReturn(testCreateLobby);
        Lobby createdLobby = lobbyService.joinLobby(testCreateLobby.getLobbyCode(), testJoiner.getId());

        // then
        Mockito.when(lobbyRepository.save(Mockito.any())).thenReturn(lobbyWithJoiner);

        Mockito.verify(lobbyRepository, Mockito.times(1)).save(Mockito.any());

        assertNotNull(createdLobby.getLobbyCode());
        assertEquals(testHost.getId(), createdLobby.getHost().getId());
        assertEquals(testJoiner.getId(), lobbyWithJoiner.getJoiner().getId());

    }

    @Test
    public void joinLobby_InvalidInputs_LobbyNotFound() {
        Lobby createdLobby = lobbyService.createLobby(testHost.getId());
        Mockito.when(lobbyRepository.findByLobbyCode(Mockito.anyString())).thenReturn(null);

        // then
        Mockito.verify(lobbyRepository, Mockito.times(1)).save(Mockito.any());
        assertThrows(EntityNotFoundExcep.class, () -> lobbyService.joinLobby(createdLobby.getLobbyCode(), testHost.getId()));
    }

    @Test
    public void joinLobby_InvalidInputs_JoinerNotFound() {

        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(lobbyRepository.findByLobbyCode(Mockito.anyString())).thenReturn(testCreateLobby);

        Mockito.verify(lobbyRepository, Mockito.never()).save(Mockito.any());
        //Mockito.verify(lobbyRepository, Mockito.times(1)).save(Mockito.any());
        assertThrows(EntityNotFoundExcep.class, () -> {
            lobbyService.joinLobby(testCreateLobby.getLobbyCode(), Mockito.anyLong());
        });

    }

    @Test
    public void joinLobby_lobbyHasJoiner_throwsUserExcep() {

        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(testJoiner));
        Mockito.when(lobbyRepository.findByLobbyCode(Mockito.anyString())).thenReturn(lobbyWithJoiner);


        Mockito.verify(lobbyRepository, Mockito.never()).save(Mockito.any());
        assertThrows(UserExcep.class, () -> {
            lobbyService.joinLobby(lobbyWithJoiner.getLobbyCode(), testJoiner.getId());
        });
    }

    @Test
    public void joinLobby_playersShouldDiffer_throwException() {
        testJoiner.setId(1L);
        testCreateLobby.setJoiner(null);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(testHost));
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(testJoiner));
        Mockito.when(lobbyRepository.findByLobbyCode(Mockito.anyString())).thenReturn(testCreateLobby);

        Mockito.verify(lobbyRepository, Mockito.never()).save(Mockito.any());
        assertThrows(PlayerExcep.class, () -> {
            lobbyService.joinLobby(testCreateLobby.getLobbyCode(), testJoiner.getId());
        });


        //Mockito.verify(lobbyRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void findLobbyByCode_ThrowException() {

        Mockito.when(lobbyRepository.findByLobbyCode(Mockito.anyString())).thenReturn(null);

        assertThrows(EntityNotFoundExcep.class, () -> {
            lobbyService.findByLobbyCode(Mockito.anyString());
        });
    }


    @Test
    public void testFindByLobbyCode() {

        Mockito.when(lobbyRepository.findByLobbyCode(Mockito.anyString())).thenReturn(testCreateLobby);

        Lobby foundLobby = lobbyService.findByLobbyCode("***");

        Mockito.verify(lobbyRepository, Mockito.times(1)).findByLobbyCode(Mockito.anyString());
        assertEquals(foundLobby.getLobbyCode(), testCreateLobby.getLobbyCode());
    }

}
