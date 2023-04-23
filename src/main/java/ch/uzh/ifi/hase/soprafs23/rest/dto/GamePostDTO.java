package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class GamePostDTO {
    private String lobbyCode;
    private long hostId;

    public String getLobbyCode() {
        return lobbyCode;
    }

    public void setLobbyCode(String lobbyCode) {
        this.lobbyCode = lobbyCode;
    }

    public long getHostId() {
        return hostId;
    }

    public void setHostId(long hostId) {
        this.hostId = hostId;
    }
}
