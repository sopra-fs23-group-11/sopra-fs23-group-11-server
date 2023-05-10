package ch.uzh.ifi.hase.soprafs23.rest.dto;


public class LobbyPutDTO {
    private String lobbyCode;
    private long joinerId;


    public String getLobbyCode() {
        return lobbyCode;
    }

    public void setLobbyCode(String lobbyCode) {
        this.lobbyCode = lobbyCode;
    }

    public long getJoinerId() {
        return joinerId;
    }

    public void setJoinerId(long joinerId) {
        this.joinerId = joinerId;
    }
}
