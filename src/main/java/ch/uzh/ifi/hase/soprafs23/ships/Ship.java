package ch.uzh.ifi.hase.soprafs23.ships;

public class Ship {
    private int length;
    private String type;
    private boolean isSunk;
    private Position[] position;

    public boolean isSunk() {
        return isSunk;
    }

    public void setSunk(boolean sunk) {
        isSunk = sunk;
    }

    public Position[] getPosition() {
        return position;
    }

    public void setPosition(Position[] position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean hit(){
        return false;
    }
    public boolean IsSunk(){
        return false;
    }

}
