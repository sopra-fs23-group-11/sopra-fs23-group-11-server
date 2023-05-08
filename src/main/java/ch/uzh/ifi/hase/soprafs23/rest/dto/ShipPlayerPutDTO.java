package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class ShipPlayerPutDTO {
    private Long shipPlayerId;
    private String gameId;

    public Long getShipPlayerId() {
        return shipPlayerId;
    }

    public void setShipPlayerId(Long shipPlayerId) {
        this.shipPlayerId = shipPlayerId;
    }

    public String  getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
