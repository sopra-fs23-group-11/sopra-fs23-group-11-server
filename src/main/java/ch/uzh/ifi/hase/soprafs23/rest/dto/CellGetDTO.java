package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;

public class CellGetDTO {
    private Long ownerId;
    private String id;
    private boolean isShotAt;
    private Ship isOccupied;

    public void setOwnerId(Long ownerId){this.ownerId = ownerId;}
    public Long getOwnerId(){return this.ownerId;}

    public void setIsShotAt(boolean isShotAt){this.isShotAt = isShotAt;}
    public Boolean getIsShotAt(){return isShotAt;}

    public void setIsOccupied(Ship isOccupied){this.isOccupied = isOccupied;}
    public Ship getIsOccupied(){return isOccupied;}

    public void setId(String id){this.id = id;}
    public String getId(){return id;}
}
