package ch.uzh.ifi.hase.soprafs23.Grid;

import ch.uzh.ifi.hase.soprafs23.ships.Position;

public class Grid {
    public Cell[][] grid;
    //public int gridSize;


    public Grid() {
        //this.gridSize = gridSize;
        grid = new Cell[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    public boolean shoot (Position pos){
        return false;
    }
    public void updateGrid(){
    }
    
}
