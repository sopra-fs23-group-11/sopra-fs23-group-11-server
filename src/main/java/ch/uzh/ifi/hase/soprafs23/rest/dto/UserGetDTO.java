package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;

import java.util.Date;

public class UserGetDTO {

    private Long id;
    private String username;
    private UserStatus status;
    private String token;
    private int totalWins;

    public void setTotalWins(int totalWins){this.totalWins = totalWins;}
    public int getTotalWins(){return totalWins;}

    public void setToken(String token){this.token = token;};

    public String getToken(){return token;}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
