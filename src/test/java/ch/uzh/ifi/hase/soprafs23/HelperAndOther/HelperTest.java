package ch.uzh.ifi.hase.soprafs23.HelperAndOther;

import ch.uzh.ifi.hase.soprafs23.entity.Helper;
import ch.uzh.ifi.hase.soprafs23.entity.Player;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Position;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelperTest {

    @Test
    void testIsValidPosition_validPositions() {
        assertTrue(Helper.isValidPosition("A1", "A2"));
        assertTrue(Helper.isValidPosition("A1", "B1"));
    }

    @Test
    void testIsValidPosition_invalidPositions() {
        assertFalse(Helper.isValidPosition("A11", "A2"));
        assertFalse(Helper.isValidPosition("A1", "A22"));

    }


    @Test
    void testIsInsideGrid() {
        assertTrue(Helper.isInsideGrid(0, 0));
        assertTrue(Helper.isInsideGrid(9, 9));
        assertFalse(Helper.isInsideGrid(10, 0));
        assertFalse(Helper.isInsideGrid(0, 10));
    }

    @Test
    void testIsValidAttackPosition_validPositions() {
        Position pos1 = new Position("A1");
        Position pos2 = new Position("B2");
        assertTrue(Helper.isValidAttackPosition(pos1));
        assertTrue(Helper.isValidAttackPosition(pos2));
    }

    @Test
    public void testIsSplittableTrue() {
        String input = "12,34";
        assertTrue(Helper.isSplittable(input));
    }

    @Test
    public void testIsSplittableFalse1() {
        String input = "1234";
        assertFalse(Helper.isSplittable(input));
    }

    @Test
    public void testIsSplittableFalse2() {
        String input = "1,234";
        assertFalse(Helper.isSplittable(input));
    }

    @Test
    public void testIsSplittableFalse3() {
        String input = "12,3,4";
        assertFalse(Helper.isSplittable(input));
    }


    @Test
    void testIsValidLengthForShip() {
        ShipPlayer player = new ShipPlayer();
        Ship ship = new Ship();
        ship.setType("Carrier");
        ship.setLength(5);
        player.setShip(ship);
        player.setStartPosition("A1");
        player.setEndPosition("A5");
        assertTrue(Helper.isValidLengthForShip(player));

    }


    @Test
    void testConvertToPos() {
        int[] expected1 = {1, 0};
        int[] expected2 = {9, 1};
        Assertions.assertArrayEquals(expected1, Helper.convertToPos("A1"));
        Assertions.assertArrayEquals(expected2, Helper.convertToPos("B9"));
    }

    @Test
    void testConvertToNum() {
        Assertions.assertEquals(0, Helper.convertToNum('A'));
        Assertions.assertEquals(1, Helper.convertToNum('B'));
    }

    @Test
    public void testIsVertical() {
        assertTrue(Helper.isVertical(new int[]{0, 0}, new int[]{1, 0}));
        assertFalse(Helper.isVertical(new int[]{0, 0}, new int[]{0, 1}));
    }


    @Test
    public void testShipsNotTouching_true() {
        Player player= new Player();
        Ship ship= new Ship();
        ship.setId(1L); ship.setType("BattleShip"); ship.setLength(4);
        List<ShipPlayer> shipPlayerList=new ArrayList<>();
        ShipPlayer shipPlayer= new ShipPlayer();
        shipPlayer.setPlayer(player);
        shipPlayer.setShip(ship);
        shipPlayer.setStartPosition("A1");
        shipPlayer.setEndPosition("A5");
        shipPlayerList.add(shipPlayer);
        player.setShipPlayers(shipPlayerList);

        boolean result = Helper.shipsNotTouching(player, "C1", "C6");
        assertTrue(result);
    }

    @Test
    public void testShipsNotTouching_false() {
        Player player= new Player();
        Ship ship= new Ship();
        ship.setId(1L); ship.setType("BattleShip"); ship.setLength(4);
        List<ShipPlayer> shipPlayerList=new ArrayList<>();
        ShipPlayer shipPlayer= new ShipPlayer();
        shipPlayer.setPlayer(player);
        shipPlayer.setShip(ship);
        shipPlayer.setStartPosition("A1");
        shipPlayer.setEndPosition("A5");
        shipPlayerList.add(shipPlayer);
        player.setShipPlayers(shipPlayerList);

        boolean result = Helper.shipsNotTouching(player, "B1", "B6");
        assertFalse(result);
    }

    @Test
    public void testIsContained() {
        ShipPlayer shipPlayer = new ShipPlayer();
        shipPlayer.setStartPosition("A1");
        shipPlayer.setEndPosition("A3");
        assertTrue(Helper.isContained("A1", shipPlayer));
        assertTrue(Helper.isContained("A2", shipPlayer));
        assertTrue(Helper.isContained("A3", shipPlayer));
        assertFalse(Helper.isContained("B2", shipPlayer));
        assertFalse(Helper.isContained("A4", shipPlayer));
    }
    @Test
    public void testAllShipsNotPlaced() {
        Player player= new Player();
        Ship ship= new Ship();
        ship.setId(1L); ship.setType("BattleShip"); ship.setLength(4);
        List<ShipPlayer> shipPlayerList=new ArrayList<>();
        ShipPlayer shipPlayer= new ShipPlayer();
        shipPlayer.setPlayer(player);
        shipPlayer.setShip(ship);
        shipPlayer.setStartPosition("A1");
        shipPlayer.setEndPosition("A5");
        shipPlayerList.add(shipPlayer);
        player.setShipPlayers(shipPlayerList);
        boolean result = Helper.allShipsPlaced(player);
        assertFalse(result);
    }

    @Test
    public void testAllShipsPlaced() {
        Player player= new Player();
        Ship ship= new Ship();
        ship.setId(1L); ship.setType("BattleShip"); ship.setLength(4);
        List<ShipPlayer> shipPlayerList=new ArrayList<>();
        ShipPlayer shipPlayer= new ShipPlayer();
        shipPlayer.setPlayer(player);
        shipPlayer.setShip(ship);
        shipPlayer.setStartPosition("A1");
        shipPlayer.setEndPosition("A5");
        for (int i=0; i<5; i++){
           shipPlayerList.add(shipPlayer);}
        player.setShipPlayers(shipPlayerList);
        boolean result = Helper.allShipsPlaced(player);
        assertTrue(result);
    }

    @Test
    public void withinBoundariesValidTest(){
        ShipPlayer shipPlayer =  new ShipPlayer();
        shipPlayer.setEndPosition("A2");
        shipPlayer.setStartPosition("A1");
        assertTrue(Helper.shipIsWithinBoundary( shipPlayer));
    }
    @Test
    public void withinBoundariesInValidTest(){
        ShipPlayer shipPlayer =  new ShipPlayer();
        shipPlayer.setEndPosition("A10");
        shipPlayer.setStartPosition("A8");
        assertTrue(Helper.shipIsWithinBoundary( shipPlayer));
    }
}



