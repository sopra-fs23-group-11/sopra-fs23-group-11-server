package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.entity.User;

public class LobbyPostDTO {
    private User host;

    public void setHost(User host){this.host = host;}
    public User getHost(){return host;}
}
