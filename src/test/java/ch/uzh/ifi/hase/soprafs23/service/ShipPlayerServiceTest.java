package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.*;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import ch.uzh.ifi.hase.soprafs23.exceptions.EntityNotFoundExcep;
import ch.uzh.ifi.hase.soprafs23.repository.*;
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

public class ShipPlayerServiceTest {

    @Mock
    private CellRepository cellRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PlayerRepository playerRepository;
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
        ShipPlayer shipPlayer =new ShipPlayer();
        shipPlayer.setStartPosition("A1");
        shipPlayer.setEndPosition("A2");
        Ship ship= new Ship();
        ship.setId(1l);
        ship.setLength(2);
        ship.setType("Destroyer");
        shipPlayer.setShip(ship);
        shipPlayer.setPlayer(player);
        shipPlayers.add(shipPlayer);
        player.setShipPlayers(shipPlayers);

        //Mockito.when()

    }

    @Test
    public void getPlayersShipTestNoPlayer() {
        Mockito.when(playerRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundExcep.class, () -> shipPlayerService.getPlayersShip(1L));

    }


}
