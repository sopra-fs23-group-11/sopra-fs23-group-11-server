package ch.uzh.ifi.hase.soprafs23.WebSockets.Message;

public class SunkMsg {
    private String type="Sunk";
    private long defenderId;

    private long shipId;
    private String shipType;


    public SunkMsg(long defenderId, long shipId, String shipType) {
        this.defenderId = defenderId;
        this.shipId = shipId;
        this.shipType = shipType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getDefenderId() {
        return defenderId;
    }

    public void setDefenderId(long defenderId) {
        this.defenderId = defenderId;
    }

    public long getShipId() {
        return shipId;
    }

    public void setShipId(long shipId) {
        this.shipId = shipId;
    }

    public String getShipType() {
        return shipType;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }
}
