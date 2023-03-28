package ch.uzh.ifi.hase.soprafs23.entity.ships;

import ch.uzh.ifi.hase.soprafs23.entity.Player;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.ManyToOne;

public class Ship {
    private int length;
    private String type;
    private boolean isSunk;
    // It is evtl. easier to define start and end position. Pay attention we need to change
    //the getter and setter accordingly
    private Position[] position;
    @ManyToOne
    public Player player;

    public Ship(String type, int length) {
        this.type = type;
        this.length=length;
    }

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
