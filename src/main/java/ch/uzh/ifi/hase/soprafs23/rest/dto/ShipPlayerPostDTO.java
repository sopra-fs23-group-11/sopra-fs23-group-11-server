package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class ShipPlayerPostDTO {

    private long shipPlayerShipId;
    private long shipPlayerPlayerId;
    private String startPosition;
    private String endPosition;

    public long getShipPlayerShipId() {
        return shipPlayerShipId;
    }

    public void setShipPlayerShipId(long shipPlayerShipId) {
        this.shipPlayerShipId = shipPlayerShipId;
    }

    public long getShipPlayerPlayerId() {
        return shipPlayerPlayerId;
    }

    public void setShipPlayerPlayerId(long shipPlayerPlayerId) {
        this.shipPlayerPlayerId = shipPlayerPlayerId;
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
