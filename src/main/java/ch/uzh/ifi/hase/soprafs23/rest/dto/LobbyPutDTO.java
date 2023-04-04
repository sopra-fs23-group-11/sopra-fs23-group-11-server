package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.entity.User;

public class LobbyPutDTO {
    private String lobbyCode;
    private User joiner;


    public String getLobbyCode() {
        return lobbyCode;
    }

    public void setLobbyCode(String lobbyCode) {
        this.lobbyCode = lobbyCode;
    }

    public User getJoiner() {
        return joiner;
    }

    public void setJoiner(User joiner) {
        this.joiner = joiner;
    }
}
