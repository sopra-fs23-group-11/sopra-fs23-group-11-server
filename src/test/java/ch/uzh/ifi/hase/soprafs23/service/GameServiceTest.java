package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.*;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import ch.uzh.ifi.hase.soprafs23.exceptions.EntityNotFoundExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.PlayerExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.PositionExcep;
import ch.uzh.ifi.hase.soprafs23.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GameServiceTest {

    @Mock
    private CellRepository cellRepository;
    @Mock
    private LobbyRepository lobbyRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private ShotRepository shotRepository;
    @Mock
    private ShipPlayerRepository shipPlayerRepository;
    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private CellService cellService;
    @InjectMocks
    private UserService userService;
    @InjectMocks
    private GameService gameService;
    @InjectMocks
    private ShipPlayerService shipPlayerService;
    @InjectMocks
    private LobbyService lobbyService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void isValidShotTest(){
        Player defender = new Player();
        Game game= new Game();
        game.setId("***");
        defender.setGamePlayer1(game);
        Mockito.when(shotRepository.findByPositionAndDefender(Mockito.anyString(), Mockito.any())).thenReturn(null);
        boolean result= gameService.isValidShot("A1", defender);
        assertEquals(true, result);
    }

    @Test
    public void isValidShotTestNotValid(){
        Player defender = new Player();
        Game game= new Game();
        game.setId("***");
        defender.setGamePlayer1(game);
        Mockito.when(shotRepository.findByPositionAndDefender(Mockito.anyString(), Mockito.any())).thenReturn(null);
        assertThrows(PositionExcep.class, () -> gameService.isValidShot("1A", defender));
    }

    @Test
    public void isValidShotTestNotValid2() {
        Shot shot = new Shot();
        shot.setPosition("A1");
        Player defender = new Player();
        shot.setDefender(defender);
        Game game= new Game();
        game.setId("***");
        defender.setGamePlayer1(game);
        Mockito.when(shotRepository.findByPositionAndDefender(Mockito.anyString(), Mockito.any())).thenReturn(shot);
        Mockito.when(gameRepository.findById(Mockito.anyString())).thenReturn(Optional.of(game));
        assertThrows(PositionExcep.class, () -> gameService.isValidShot("A1", defender));
    }
    @Test
    public void looserAlertTest() {
        Player player = new Player();
        player.setId(1L);
        player.setShipsRemaining(0);
        Mockito.when(playerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(player));
        boolean result = gameService.looserAlert(1L, "***");
        assertTrue(result);
        player.setShipsRemaining(3);
        result = gameService.looserAlert(1L, "***");
        assertFalse(result);
    }

    @Test
    public void looserAlertTestNoPlayer(){
        Mockito.when(playerRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundExcep.class, () -> gameService.looserAlert(1L, "***"));
    }
    @Test
    public void waterORShipTest() {

        Player defender = new Player();
        defender.setId(1L);
        ShipPlayer shipPlayer1 = new ShipPlayer();
        shipPlayer1.setStartPosition("A1"); shipPlayer1.setEndPosition("A2");
        ShipPlayer shipPlayer2 = new ShipPlayer();
        shipPlayer2.setStartPosition("B1"); shipPlayer2.setEndPosition("E1");
        defender.setShipPlayers(Arrays.asList(shipPlayer1, shipPlayer2));
        ShipPlayer result = gameService.waterORship("C1", defender);
        ShipPlayer result2 = gameService.waterORship("B2", defender);

        assertEquals(shipPlayer2, result);
        assertEquals(null, result2);
    }

    @Test
    public void getAttackerShotTest() {
        Player attacker = new Player();
        attacker.setId(1L);
        List<Shot> shots = new ArrayList<>();
        Shot shot = new Shot();
        shot.setHit(false);
        Shot shot2 = new Shot();
        shot2.setHit(true);
        shot.setAttacker(attacker);
        shot2.setAttacker(attacker);
        shots.add(shot);
        shots.add(shot2);

        Mockito.when(playerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(attacker));
        Mockito.when(shotRepository.findAllByAttacker(attacker)).thenReturn(shots);

        List<Shot> result = gameService.getAttackersShot(attacker.getId(), "***");

        assertEquals(2, result.size());
        assertEquals(shots, result);
        assertEquals(attacker, result.get(0).getAttacker());
        assertEquals(true, result.get(1).isHit());

    }

    @Test
    public void getDefenderShotTestNoDefender() {
        Mockito.when(playerRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundExcep.class, () -> gameService.getDefendersShot(1L, "***"));
    }

    @Test
    public void getDefenderShotTest() {
        Player defender = new Player();
        defender.setId(1L);
        List<Shot> shots = new ArrayList<>();
        Shot shot = new Shot();
        shot.setHit(false);
        Shot shot2 = new Shot();
        shot2.setHit(true);
        shot.setDefender(defender);
        shot2.setDefender(defender);
        shots.add(shot);
        shots.add(shot2);

        Mockito.when(playerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(defender));
        Mockito.when(shotRepository.findAllByDefender(defender)).thenReturn(shots);

        List<Shot> result = gameService.getDefendersShot(defender.getId(), "test");

        assertEquals(2, result.size());
        assertEquals(shots, result);
        assertEquals(defender, result.get(0).getDefender());
        assertEquals(true, result.get(1).isHit());
    }

    @Test
    public void getAttackerShotTestNoDefender() {
        Mockito.when(playerRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundExcep.class, () -> gameService.getAttackersShot(1L, "***"));
    }

    @Test
    public void startGameTestValid() {
        User host = new User();
        host.setId(1L);
        User joiner = new User();
        joiner.setId(2L);
        Lobby lobby = new Lobby();
        lobby.setLobbyCode("testCode");
        lobby.setHost(host);
        lobby.setJoiner(joiner);

        Mockito.when(lobbyRepository.findByLobbyCode(Mockito.anyString())).thenReturn(lobby);
        Game result = gameService.startGame(host.getId(), "testCode");

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getPlayer1());
        assertNotNull(result.getPlayer2());
        assertEquals(host, result.getPlayer1().getUser());
        assertEquals(joiner, result.getPlayer2().getUser());
        assertEquals(5, result.getPlayer1().getShipsRemaining());
        assertEquals(5, result.getPlayer2().getShipsRemaining());
        verify(playerRepository, times(2)).save(any(Player.class));
    }

    @Test
    public void startGameTestNoLobby() {
        Mockito.when(lobbyRepository.findByLobbyCode(Mockito.anyString())).thenReturn(null);
        assertThrows(EntityNotFoundExcep.class, () -> gameService.startGame(1L, "***"));
    }

    @Test
    public void startGameTestNoHost() {
        Lobby lobby = new Lobby();
        lobby.setLobbyCode("testCode");
        Mockito.when(lobbyRepository.findByLobbyCode(Mockito.anyString())).thenReturn(lobby);
        assertThrows(EntityNotFoundExcep.class, () -> gameService.startGame(1L, "testCode"));
    }

    @Test
    public void startGameTestNoJoiner() {
        Lobby lobby = new Lobby();
        lobby.setLobbyCode("testCode");
        User user = new User();
        user.setId(1L);
        lobby.setHost(user);
        Mockito.when(lobbyRepository.findByLobbyCode(Mockito.anyString())).thenReturn(lobby);
        assertThrows(EntityNotFoundExcep.class, () -> gameService.startGame(1L, "testCode"));
    }

    @Test
    public void startGameTestSameHostAndJoiner() {
        User user = new User();
        user.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        Lobby lobby = new Lobby();
        lobby.setLobbyCode("testCode");
        lobby.setHost(user);
        lobby.setJoiner(user2);
        Mockito.when(lobbyRepository.findByLobbyCode(Mockito.anyString())).thenReturn(lobby);
        assertThrows(PlayerExcep.class, () -> gameService.startGame(lobby.getJoiner().getId(), "testCode"));
    }

    @Test
    public void createCellsTest() {
        Long ownerId = 1L;
        gameService.createCells(ownerId);
        verify(cellRepository, times(100)).save(any(Cell.class));
    }

    @Test
    public void testGetCharForNumber() {
        assertEquals("A", gameService.getCharForNumber(1));
        assertEquals("B", gameService.getCharForNumber(2));
        assertEquals("Z", gameService.getCharForNumber(26));
        assertNull(gameService.getCharForNumber(0));
        assertNull(gameService.getCharForNumber(27));
    }

}
