package ch.uzh.ifi.hase.soprafs23.entity.ships;
import ch.uzh.ifi.hase.soprafs23.entity.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "ship_players")
public class ShipPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ship_id")
    private Ship ship;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "player_id")
    private Player player;

    @Column
    private String startPosition;

    @Column
    private String endPosition;


    @Column(nullable = false)
    private boolean isSunk;

    @Column
    private int hitParts=0;

    @Column
    Long shipId;

    @Column
    Long playerId;

    public void setPlayerId(Long playerId){
        this.playerId = playerId;
    }

    public Long getPlayerId(){
        return playerId;
    }

    public void setShipId(Long shipId){
        this.shipId = shipId;
    }

    public Long getShipId(){
        return shipId;
    }

    public ShipPlayer(){}

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

    public String getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }

    public String getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(String endPosition) {
        this.endPosition = endPosition;
    }

    public boolean isSunk() {
        return isSunk;
    }

    public void setSunk(boolean sunk) {
        isSunk = sunk;
    }

    public int getHitParts() {
        return hitParts;
    }

    public void setHitParts(int hitParts) {
        this.hitParts = hitParts;
    }
}
