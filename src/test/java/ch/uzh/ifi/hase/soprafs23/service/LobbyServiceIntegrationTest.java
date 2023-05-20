package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */


@WebAppConfiguration
@SpringBootTest
public class LobbyServiceIntegrationTest {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Qualifier("lobbyRepository")
    @Autowired
    private LobbyRepository lobbyRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private LobbyService lobbyService;

    private User testHost;
    private User testJoiner;
    private Lobby testLobby;

    @BeforeEach
    public void setup() {

        //lobbyRepository.deleteAll();
        userRepository.deleteAll();
        testHost = new User();
        testHost.setUsername("testUsername");
        testHost.setPassword("***");
        testHost.setId(1l);
        testHost.setAvatar("Luna");
        testHost = userService.createUser(testHost);

        testJoiner = new User();
        testJoiner.setUsername("testJoiner");
        testJoiner.setId(2L);
        testJoiner.setPassword("***");
        testJoiner.setAvatar("Luna");
        testJoiner = userService.createUser(testJoiner);

        testLobby = new Lobby();
        testLobby.setLobbyCode("***");
        testLobby.setHost(testHost);
        //testLobby = lobbyRepository.save(testLobby);

    }

    @Test
    public void createLobby_validInputs_success() {
        // given
        assertNull(lobbyRepository.findByLobbyCode(""));

        // when

        Lobby createdLobby = lobbyService.createLobby(testHost.getId());

        // then
        assertNotNull(createdLobby.getLobbyCode());
        assertEquals(createdLobby.getHost().getId(), testHost.getId());

    }
    @Test
    public void joinLobby_validInputs_success() {
        assertNull(testJoiner.getLobbyForJoiner());
        assertNull(testLobby.getJoiner());

        Lobby joinedLobby = lobbyService.createLobby(testHost.getId());
        joinedLobby = lobbyService.joinLobby(joinedLobby.getLobbyCode(), testJoiner.getId());


        assertNotNull(joinedLobby.getJoiner());
        assertEquals(joinedLobby.getJoiner().getId(), testJoiner.getId());


    }

}


