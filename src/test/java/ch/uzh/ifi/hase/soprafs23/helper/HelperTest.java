package ch.uzh.ifi.hase.soprafs23.helper;

import ch.uzh.ifi.hase.soprafs23.entity.Helper;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Position;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelperTest {

    @Test
    void testIsValidPosition_validPositions() {
        Assertions.assertTrue(Helper.isValidPosition("A1", "A2"));
        Assertions.assertTrue(Helper.isValidPosition("A1", "B1"));
    }

    @Test
    void testIsValidPosition_invalidPositions() {
        Assertions.assertFalse(Helper.isValidPosition("A11", "A2"));
        Assertions.assertFalse(Helper.isValidPosition("A1", "A22"));

    }


    @Test
    void testIsInsideGrid() {
        Assertions.assertTrue(Helper.isInsideGrid(0, 0));
        Assertions.assertTrue(Helper.isInsideGrid(9, 9));
        Assertions.assertFalse(Helper.isInsideGrid(10, 0));
        Assertions.assertFalse(Helper.isInsideGrid(0, 10));
    }

    @Test
    void testIsValidAttackPosition_validPositions() {
        Position pos1 = new Position("A1");
        Position pos2 = new Position("B2");
        Assertions.assertTrue(Helper.isValidAttackPosition(pos1));
        Assertions.assertTrue(Helper.isValidAttackPosition(pos2));
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
        Assertions.assertTrue(Helper.isValidLengthForShip(player));

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
        Assertions.assertTrue(Helper.isVertical(new int[]{0, 0}, new int[]{1, 0}));
        Assertions.assertFalse(Helper.isVertical(new int[]{0, 0}, new int[]{0, 1}));
    }

}



