package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.*;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import ch.uzh.ifi.hase.soprafs23.exceptions.PositionExcep;
import ch.uzh.ifi.hase.soprafs23.repository.*;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */


@WebAppConfiguration
@SpringBootTest
public class GameIntegrationTest {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Qualifier("lobbyRepository")
    @Autowired
    private LobbyRepository lobbyRepository;

    @Qualifier("cellRepository")
    @Autowired
    private CellRepository cellRepository;

    @Qualifier("playerRepository")
    @Autowired
    private PlayerRepository playerRepository;

    @Qualifier("shipPlayerRepository")
    @Autowired
    private ShipPlayerRepository shipPlayerRepository;

    @Qualifier("shipRepository")
    @Autowired
    private ShipRepository shipRepository;

    @Qualifier("shotRepository")
    @Autowired
    private ShotRepository shotRepository;

    @Qualifier("gameRepository")
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private LobbyService lobbyService;
    @Autowired
    private ShipPlayerService shipPlayerService;

    @Autowired
    private GameService gameService;

    private User testHost;private User testJoiner;
    private Lobby testLobby;
    private Lobby lobby;
    private Player testAttacker; private Player testDefender;
    private Game testGame;
    private Game game;
    private ShipPlayer testShipPlayer1;
    private ShipPlayer testShipPlayer;
    private Ship testShip1;

    private Shot testShot;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        playerRepository.deleteAll();
        gameRepository.deleteAll();
        cellRepository.deleteAll();
        lobbyRepository.deleteAll();
        shipRepository.deleteAll();
        shipPlayerRepository.deleteAll();
        shotRepository.deleteAll();

        testHost = new User();testHost.setUsername("testUsername");testHost.setPassword("***");
        testHost.setId(1l);testHost.setAvatar("Luna");testHost = userService.createUser(testHost);


        testJoiner = new User();testJoiner.setUsername("testJoiner");testJoiner.setId(2L);
        testJoiner.setPassword("***");testJoiner.setAvatar("Luna");testJoiner = userService.createUser(testJoiner);


        testLobby = new Lobby();testLobby.setLobbyCode("***");
        testLobby.setHost(testHost); testLobby=lobbyService.createLobby(testHost.getId());

        lobby= new Lobby(); lobby= lobbyService.joinLobby(testLobby.getLobbyCode(), testJoiner.getId());


        testAttacker= new Player(); testAttacker.setUser(testHost); testAttacker.setId(1L);
        testDefender= new Player(); testDefender.setUser(testJoiner); testDefender.setId(2L);

        testGame= new Game();
        game = new Game();game=gameService.startGame(lobby.getHost().getId(), lobby.getLobbyCode());

        testShip1= new Ship(); testShip1.setId(1L); testShip1.setLength(2); testShip1.setType("Destroyer");
        testShip1 = shipRepository.save(testShip1);
        testShipPlayer1= new ShipPlayer();testShipPlayer= new ShipPlayer();
        testShipPlayer= shipPlayerService.placeShip(game.getPlayer2().getId(), testShip1.getId(), "A1", "A2", game.getId());

        testShot =new Shot();

    }
    @Test
    public void startGame(){
        assertNull(testGame.getId());
        assertNull(testGame.getPlayer1());
        assertNull(testGame.getPlayer2());
        testGame=gameService.startGame(lobby.getHost().getId(), lobby.getLobbyCode());
        assertEquals(lobby.getLobbyCode(), testGame.getId());
        assertEquals(testHost.getUsername(), testGame.getPlayer1().getUser().getUsername());
        assertEquals(testJoiner.getUsername(), testGame.getPlayer2().getUser().getUsername());
        assertEquals(5, testGame.getPlayer2().getShipsRemaining());
    }

    @Test
    public void placeShipTest(){
        assertNull(testShipPlayer1.getShip());
        testShipPlayer1= shipPlayerService.placeShip(game.getPlayer2().getId(), testShip1.getId(), "A1", "A2", game.getId());
        assertEquals(2, testShipPlayer1.getShip().getLength());
        assertEquals(false, testShipPlayer1.isSunk());

    }

    @Test
    public void attackTestOneShot(){
        assertNull(testShot.getDefender());
        assertEquals(true, gameService.isValidShot("A1", game.getPlayer2()));
        testShot = gameService.attack(game.getPlayer1().getId(), game.getPlayer2().getId(), "A1", game.getId());
        assertThrows(PositionExcep.class, () -> {gameService.isValidShot(testShot.getPosition(), testShot.getDefender());});
        assertEquals( true, testShot.isHit());
        assertEquals(5, testShot.getDefender().getShipsRemaining());
    }

    @Test
    public void attackTestShotTillSunk(){
        assertNull(testShot.getDefender());
        assertEquals(true, gameService.isValidShot("A1", game.getPlayer2()));
        testShot = gameService.attack(game.getPlayer1().getId(), game.getPlayer2().getId(), "A1", game.getId());
        Shot testShot2 = gameService.attack(game.getPlayer1().getId(), game.getPlayer2().getId(), "C1", game.getId());
        Shot testShot3 = gameService.attack(game.getPlayer1().getId(), game.getPlayer2().getId(), "A2", game.getId());
        assertThrows(PositionExcep.class, () -> {gameService.isValidShot(testShot.getPosition(), testShot.getDefender());});
        assertEquals( true, testShot.isHit());
        assertEquals( false, testShot2.isHit());
        assertEquals( true, testShot3.isHit());
        assertEquals(true, testShot3.getDefender().getShipPlayers().get(0).isSunk());
        assertEquals(4, testShot3.getDefender().getShipsRemaining());
    }

    @Ignore
    public void attackTestWithLooserNotification(){
        assertNull(testShot.getDefender());
        testShot = gameService.attack(game.getPlayer1().getId(), game.getPlayer2().getId(), "A1", game.getId());
        testShot.getDefender().setShipsRemaining(1);
        shotRepository.save(testShot);
        testShot = gameService.attack(game.getPlayer1().getId(), game.getPlayer2().getId(), "A2", game.getId());

        assertEquals( true, testShot.isHit());
        assertEquals( true, testShot.getDefender().getShipPlayers().get(0).isSunk());
        assertEquals(0, testShot.getDefender().getShipsRemaining());

    }



    @Test
    public void playGameTest() {

        assertNull(lobbyRepository.findByLobbyCode("***"));
        assertNull(testJoiner.getLobbyForJoiner());
        assertNull(testLobby.getJoiner());
        Lobby joinedLobby = lobbyService.createLobby(testHost.getId());
        joinedLobby = lobbyService.joinLobby(joinedLobby.getLobbyCode(), testJoiner.getId());
        assertNotNull(joinedLobby.getJoiner());
        assertEquals(joinedLobby.getJoiner().getId(), testJoiner.getId());

        assertNull(testGame.getId());
        assertNull(testGame.getPlayer1());
        assertNull(testGame.getPlayer2());
        testGame=gameService.startGame(joinedLobby.getHost().getId(), joinedLobby.getLobbyCode());
        assertEquals(joinedLobby.getLobbyCode(), testGame.getId());
        assertEquals(testHost.getUsername(), testGame.getPlayer1().getUser().getUsername());
        assertEquals(testJoiner.getUsername(), testGame.getPlayer2().getUser().getUsername());

        assertNotNull(testShip1.getType());
        assertNull(testShipPlayer1.getShip());
        testShipPlayer1= shipPlayerService.placeShip(testGame.getPlayer2().getId(), testShip1.getId(), "A1", "A2", testGame.getId());
        assertEquals(2, testShipPlayer1.getShip().getLength());
        assertEquals(false, testShipPlayer1.isSunk());

        shipPlayerService.deleteShip(testShipPlayer1.getId(), testGame.getId());
        assertEquals(Collections.emptyList(), shipPlayerRepository.findAllByPlayer(testGame.getPlayer2()));


        testShipPlayer1= shipPlayerService.placeShip(testGame.getPlayer2().getId(), testShip1.getId(), "A1", "A2", testGame.getId());

        assertNull(testShot.getDefender());
        assertEquals(true, gameService.isValidShot("A1", testGame.getPlayer2()));
        testShot = gameService.attack(testGame.getPlayer1().getId(), testGame.getPlayer2().getId(), "A1", testGame.getId());
        Shot testShot2 = gameService.attack(testGame.getPlayer1().getId(), testGame.getPlayer2().getId(), "C1", testGame.getId());
        Shot testShot3 = gameService.attack(testGame.getPlayer1().getId(), testGame.getPlayer2().getId(), "A2", testGame.getId());
        assertThrows(PositionExcep.class, () -> {gameService.isValidShot(testShot.getPosition(), testShot.getDefender());});
        assertEquals( true, testShot.isHit());
        assertEquals( false, testShot2.isHit());
        assertEquals( true, testShot3.isHit());
        assertEquals(true, testShot3.getDefender().getShipPlayers().get(0).isSunk());
        assertEquals(4, testShot3.getDefender().getShipsRemaining());
    }

}


