package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.*;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import ch.uzh.ifi.hase.soprafs23.exceptions.EntityNotFoundExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.PositionExcep;
import ch.uzh.ifi.hase.soprafs23.repository.*;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

public class ShipPlayerServiceTest {

    @Mock
    private CellRepository cellRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private ShipRepository shipRepository;
    @Mock
    private ShotRepository shotRepository;
    @Mock
    private ShipPlayerRepository shipPlayerRepository;



    @InjectMocks
    private CellService cellService;
    @InjectMocks
    private UserService userService;
    @InjectMocks
    private GameService gameService;
    @InjectMocks
    private ShipPlayerService shipPlayerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getPlayersShipTest() {

        Player player = new Player();
        player.setId(1l);

        List<ShipPlayer> shipPlayers = new ArrayList<>();
        ShipPlayer shipPlayer = new ShipPlayer();
        shipPlayer.setStartPosition("A1");
        shipPlayer.setEndPosition("A2");
        Ship ship = new Ship();
        ship.setId(1l);
        ship.setLength(2);
        ship.setType("Destroyer");
        shipPlayer.setShip(ship);
        shipPlayer.setPlayer(player);
        shipPlayers.add(shipPlayer);

        ShipPlayer shipPlayer2 = new ShipPlayer();
        shipPlayer2.setStartPosition("B1");
        shipPlayer2.setEndPosition("D1");
        Ship ship2 = new Ship();
        ship2.setId(2l);
        ship2.setLength(3);
        ship2.setType("Submarine");
        shipPlayer2.setShip(ship2);
        shipPlayer2.setPlayer(player);
        shipPlayers.add(shipPlayer2);

        player.setShipPlayers(shipPlayers);

        Mockito.when(playerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(player));
        Mockito.when(shipPlayerService.getPlayersShip(Mockito.anyLong())).thenReturn(shipPlayers);
        List<ShipPlayer> result = shipPlayerService.getPlayersShip(1l);
        assertEquals(2, result.size());
        assertEquals(player.getShipPlayers(), result);
        assertEquals(ship, result.get(0).getShip());
        assertEquals(ship2.getType(), result.get(1).getShip().getType());

    }

    @Test
    public void getPlayersShipTestNoPlayer() {
        Mockito.when(playerRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundExcep.class, () -> shipPlayerService.getPlayersShip(1L));

    }

    @Test
    public void placeShipTest(){
        Player player = new Player();
        player.setId(1l);

        ShipPlayer shipPlayer = new ShipPlayer();
        shipPlayer.setStartPosition("A1");
        shipPlayer.setEndPosition("A2");
        Ship ship = new Ship();
        ship.setId(1L);
        ship.setLength(2);
        ship.setType("Destroyer");
        shipPlayer.setShip(ship);
        shipPlayer.setPlayer(player);

//        shipPlayers.add(shipPlayer);
        player.setShipPlayers(new ArrayList<>());

        Mockito.when(shipRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ship));
        Mockito.when(playerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(player));
        //Mockito.when(shipRepository.save(Mockito.any())).thenReturn(ship);


        System.out.println(player.getId());
        ShipPlayer result = shipPlayerService.placeShip(1L, 1L, "A1", "A2", "***" );

        assertEquals(ship.getType(), result.getShip().getType());

    }
    @Test
    public void placeShipTestShipNotFound() {
        Mockito.when(shipRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundExcep.class, () -> shipPlayerService.placeShip(1L, 1L, "A1", "A2", "***"));
    }

    @Test
    public void placeShipTestPlayerNotFound() {
        Mockito.when(playerRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundExcep.class, () -> shipPlayerService.placeShip(1L, 1L, "A1", "A2", "***"));
    }

    @Test
    public void placeShipTestHelperIsFalse() {

        Player player = new Player();
        player.setId(1L);

        List<ShipPlayer> shipPlayers = new ArrayList<>();
        ShipPlayer shipPlayer = new ShipPlayer();
        shipPlayer.setStartPosition("A1");
        shipPlayer.setEndPosition("A2");
        Ship ship = new Ship();
        ship.setId(1L);
        ship.setLength(2);
        ship.setType("Destroyer");
        shipPlayer.setShip(ship);
        shipPlayer.setPlayer(player);
        shipPlayers.add(shipPlayer);
        player.setShipPlayers(shipPlayers);

        Mockito.when(playerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(player));
        Mockito.when(shipRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ship));

        //Mockito.when(Helper.shipsNotOverlapping(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(false);

        assertThrows(PositionExcep.class, () -> shipPlayerService.placeShip(1L, 2L, "A1", "C1", "***"));
    }

    @Test
    public void deleteShipTestNotFound() {

        Mockito.when(shipPlayerRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundExcep.class, () -> shipPlayerService.deleteShip(1L, "***"));

    }

    @Test
    public void deleteShipTest() {
        Player player = new Player();
        player.setId(1l);

        List<ShipPlayer> shipPlayers = new ArrayList<>();
        ShipPlayer shipPlayer = new ShipPlayer();
        shipPlayer.setId(1L);
        shipPlayer.setStartPosition("A1");
        shipPlayer.setEndPosition("A2");
        Ship ship = new Ship();
        ship.setId(1l);
        ship.setLength(2);
        ship.setType("Destroyer");
        shipPlayer.setShip(ship);
        shipPlayer.setPlayer(player);
        shipPlayers.add(shipPlayer);
        player.setShipPlayers(shipPlayers);

        Mockito.when(shipPlayerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(shipPlayer));
        Mockito.when(playerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(player));

        Mockito.when(playerRepository.save(Mockito.any())).thenReturn(player);

        shipPlayerService.deleteShip(shipPlayer.getId(), "***");
        assertFalse(player.getShipPlayers().contains(shipPlayer));



    }

}
