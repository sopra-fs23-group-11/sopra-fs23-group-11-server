package ch.uzh.ifi.hase.soprafs23.Grid;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Position;


public class Grid {
    public Cell[][] grid;
    private final int size;


    public Grid(int size) {
        this.size = size;
        this.grid = new Cell[size][size];
        initializeCells();
    }

    public void initializeCells(){
        for(int i=0; i < size; i++){
            for(int j =0; j < size; j++){
                grid[i][j] = new Cell();
            }
        }
    }

    public Cell getCell(Position position){
        int x = position.getX();
        int y = position.getY();
        return grid[x][y];
    }

    public void setCellStatus(Position position, boolean isHit, boolean isDestroyed){
        Cell cell = this.getCell(position);
        cell.isHit = isHit;
        cell.isDestroyed = isDestroyed;
    }

    public void setCellOccupied(Position position, Occupied occupied){
        Cell cell = this.getCell(position);
        cell.setIsOccupied(occupied);
    }

}
