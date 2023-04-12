package ch.uzh.ifi.hase.soprafs23.entity.ships;

import javax.persistence.*;
import java.util.List;

/*To avoid redundancy we will join the information of player & ships using ShipPlayer
 * The initial values of the ships will be created using data.sql*/
@Entity
@Table(name = "ships")
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int length;
    @Column(nullable = false)
    private String type;

    @OneToMany(mappedBy = "ship")
    private List<ShipPlayer> shipPlayers;

    public Ship(Long id, int length, String type) {
        this.id = id;
        this.length = length;
        this.type = type;

    }

/*
    private boolean isSunk;

    @ManyToOne
    public Player player;


    public boolean isSunk() {
        return isSunk;
    }

    public void setSunk(boolean sunk) {
        isSunk = sunk;
    }

    } */

    public Long getId() {
        return id;
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

    public List<ShipPlayer> getShipPlayers() {
        return shipPlayers;
    }

    public void setShipPlayers(List<ShipPlayer> shipPlayers) {
        this.shipPlayers = shipPlayers;
    }
    /*
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean hit(){
        return false;
    }
    public boolean IsSunk(){
        return false;
    }*/

    public void decrementSize() {
        this.length -= 1;
    }

    /*
    public Position[] getPosition() {
        return position;
    }

    public void setPosition(Position[] position) {
        this.position = position;
    }

     */
}
