package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;

public class CellGetDTO {
    private Long ownerId;
    private String id;
    private boolean isShotAt;
    private Ship occupyingShip;

    public void setOwnerId(Long ownerId){this.ownerId = ownerId;}
    public Long getOwnerId(){return this.ownerId;}

    public boolean isShotAt() {
        return isShotAt;
    }

    public void setShotAt(boolean shotAt) {
        isShotAt = shotAt;
    }

    public Ship getOccupyingShip() {
        return occupyingShip;
    }

    public void setOccupyingShip(Ship occupyingShip) {
        this.occupyingShip = occupyingShip;
    }

    public void setId(String id){this.id = id;}
    public String getId(){return id;}
}
