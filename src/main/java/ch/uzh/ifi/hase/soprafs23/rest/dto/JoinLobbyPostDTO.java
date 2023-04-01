package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class JoinLobbyPostDTO {
    private String lobbyCode;
    private long userId;

    public String getLobbyCode() {
        return lobbyCode;
    }

    public void setLobbyCode(String lobbyCode) {
        this.lobbyCode = lobbyCode;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
