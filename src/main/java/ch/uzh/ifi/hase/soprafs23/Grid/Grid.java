package ch.uzh.ifi.hase.soprafs23.Grid;

import ch.uzh.ifi.hase.soprafs23.ships.Position;

public class Grid {
    public final Cell[][] grid;
    public int gridSize;


    public Grid() {
        grid = new Cell[gridSize][gridSize];
    }

    public boolean shoot (Position pos){
        return false;
    }
    
}
