package ch.uzh.ifi.hase.soprafs23.Grid;

public class Cell {
    public boolean isHit;
    public boolean isDestroyed;
    private Occupied isOccupied;

    public Occupied getIsOccupied() {
        return isOccupied;
    }
    public void setIsOccupied(Occupied isOccupied) {
        this.isOccupied = isOccupied;
    }



}
