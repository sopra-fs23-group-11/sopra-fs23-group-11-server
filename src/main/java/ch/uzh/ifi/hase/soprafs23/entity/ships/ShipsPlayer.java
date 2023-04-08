package ch.uzh.ifi.hase.soprafs23.entity.ships;
import ch.uzh.ifi.hase.soprafs23.entity.Player;

import javax.persistence.*;

@Entity
@Table(name = "ship_players")
public class ShipsPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ship_id")
    private Ship ship;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Column
    private Position[] positionOfShips;

    @Column(nullable = false)
    private boolean isSunk;

    public Long getId() {
        return id;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Position[] getPositionOfShips() {
        return positionOfShips;
    }

    public void setPositionOfShips(Position[] positionOfShips) {
        this.positionOfShips = positionOfShips;
    }

    public boolean isSunk() {
        return isSunk;
    }

    public void setSunk(boolean sunk) {
        isSunk = sunk;
    }
}
