package ch.uzh.ifi.hase.soprafs23.entity.ships;

import ch.uzh.ifi.hase.soprafs23.entity.Player;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;

@Entity
@Table(name = "ships")
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int length;
    @Column(nullable = false, unique = true)
    private String type;
    private boolean isSunk;

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
