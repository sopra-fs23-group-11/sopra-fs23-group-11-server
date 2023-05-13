package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.*;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import ch.uzh.ifi.hase.soprafs23.exceptions.EntityNotFoundExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.UserExcep;
import ch.uzh.ifi.hase.soprafs23.repository.*;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CellServiceTest {

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
//        List<Cell> cells = new ArrayList<>();
//        Cell cell= new Cell();
//        cell.setOwnerId(1L);
//        cell.setOccupyingShip(null);
//        cell.setIsShotAt(true);
//        cells.add(cell);

        //Mockito.when(cellRepository.save(Mockito.any())).thenReturn(cell);
    }

    @Test
    public void testGetCellsById() {

        List<Cell> cells = new ArrayList<>();
        Cell cell= new Cell();
        cell.setOwnerId(1L);
        cell.setOccupyingShip(null);
        cell.setIsShotAt(true);
        cells.add(cell);
        Mockito.when(cellRepository.findAllByOwnerId(Mockito.anyLong())).thenReturn(cells);

        List<Cell> result = cellService.getCellsById(1L);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(null, result.get(0).getOccupyingShip());
        Assertions.assertEquals(true, result.get(0).getIsShotAt());
    }
    @Test
    public void updateCellsTest(){
        User user1=new User();
        user1.setId(1L);

        Player player1 = new Player();
        player1.setId(1L);
        player1.setUser(user1);

        List<ShipPlayer> shipPlayers = new ArrayList<>();
        ShipPlayer shipPlayer =new ShipPlayer();
        shipPlayer.setStartPosition("A1");
        shipPlayer.setEndPosition("C1");
        Ship ship= new Ship();
        ship.setId(1l);
        ship.setLength(3);
        ship.setType("Destroyer");
        shipPlayer.setShip(ship);
        shipPlayers.add(shipPlayer);
        player1.setShipPlayers(shipPlayers);

        List<Cell> cells = new ArrayList<>();
        Cell cell= new Cell();cell.setId("A1");cell.setOwnerId(1L);cell.setOccupyingShip(ship);cell.setIsShotAt(false);
        Cell cell1= new Cell();cell1.setId("B1");cell1.setOwnerId(1L);cell1.setOccupyingShip(ship);cell1.setIsShotAt(false);
        Cell cell2= new Cell();cell2.setId("C1");cell2.setOwnerId(1L);cell2.setOccupyingShip(ship);cell2.setIsShotAt(false);
        Cell cell3= new Cell();cell3.setId("A2");cell3.setOwnerId(1L);cell3.setOccupyingShip(null);cell3.setIsShotAt(false);
        cells.add(cell); cells.add(cell1); cells.add(cell2);cells.add(cell3);


        List<Cell> cells2 = new ArrayList<>();
        Cell cell20= new Cell();cell20.setId("A1");cell20.setOwnerId(1L);cell20.setOccupyingShip(ship);cell20.setIsShotAt(false);
        Cell cell21= new Cell();cell21.setId("B1");cell21.setOwnerId(1L);cell21.setOccupyingShip(ship);cell21.setIsShotAt(false);
        Cell cell22= new Cell();cell22.setId("C1");cell22.setOwnerId(1L);cell22.setOccupyingShip(ship);cell22.setIsShotAt(false);
        Cell cell23= new Cell();cell23.setId("A2");cell23.setOwnerId(1L);cell23.setOccupyingShip(null);cell23.setIsShotAt(false);
        cells2.add(cell20); cells2.add(cell21); cells2.add(cell22);cells2.add(cell23);

        List<Shot> shots = new ArrayList<>();
        Shot shot = new Shot();shot.setPosition("A1");shot.setHit(true);
        shots.add(shot);
        Shot shot2 = new Shot();shot2.setPosition("A2");shot2.setHit(false);
        shots.add(shot2);

        Mockito.when(playerRepository.getOne(Mockito.anyLong())).thenReturn(player1);
        Mockito.when(cellRepository.findAllByOwnerId(Mockito.anyLong())).thenReturn(cells);
        Mockito.when(shipPlayerRepository.findAllByPlayer(Mockito.any())).thenReturn(shipPlayers);
        Mockito.when(shotRepository.findAllByDefender(Mockito.any())).thenReturn(shots);
        Mockito.when(cellService.getCellsById(Mockito.anyLong())).thenReturn(cells2);
        List<Cell> returnedCells = cellService.updateCells( player1.getId());

        Assertions.assertEquals(4, returnedCells.size());

    }

    @Test
    public void updateCellsTest_NoPlayer(){
        Player player = new Player();
        player.setId(1L);
        Mockito.when(playerRepository.getOne(Mockito.anyLong())).thenReturn(null);
        assertThrows(EntityNotFoundExcep.class, () -> cellService.updateCells(player.getId()));
    }

    @Test
    public void getCharForNumberTest() {
        assertEquals("A", cellService.getCharForNumber(1));
        assertEquals("B", cellService.getCharForNumber(2));
        assertEquals("Z", cellService.getCharForNumber(26));
        assertNull(cellService.getCharForNumber(0));
        assertNull(cellService.getCharForNumber(27));
    }

    @Test
    public void getIntFromStringTest() {
        assertEquals(0, cellService.getIntFromString('A'));
        assertEquals(1, cellService.getIntFromString('B'));
        assertEquals(25, cellService.getIntFromString('Z'));
    }


}
