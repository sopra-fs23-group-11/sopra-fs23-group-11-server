package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class LobbyGetDTO {
    private String lobbyCode;

    public LobbyGetDTO(String lobbyCode) {
        this.lobbyCode = lobbyCode;
    }

    public String getLobbyCode() {
        return lobbyCode;
    }

    public void setLobbyCode(String lobbyCode) {
        this.lobbyCode = lobbyCode;
    }
}
