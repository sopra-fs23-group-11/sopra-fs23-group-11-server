package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class LobbyGetDTO {
    private String lobbyCode;
    private String hostName;

    public String getLobbyCode() {
        return lobbyCode;
    }

    public void setLobbyCode(String lobbyCode) {
        this.lobbyCode = lobbyCode;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
}
