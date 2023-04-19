package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.entity.Player;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;

public class ShipPlayerPostDTO {
    private Long id;
    private Ship ship;
    private Player player;
    private String startPosition;
    private String endPosition;
    private Long shipId;
    private Long playerId;

    public Long getPlayerId(){return playerId;}
    public Long getShipId(){return shipId;}
    public void setPlayerId(Long playerId){this.playerId = playerId;}
    public void setShipId(Long shipId){this.shipId = shipId;}

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


}
