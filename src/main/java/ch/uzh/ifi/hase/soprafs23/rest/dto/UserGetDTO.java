package ch.uzh.ifi.hase.soprafs23.rest.dto;
import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;

public class UserGetDTO {

    private long id;
    private String username;
    private UserStatus status;
    private String token;
    private int totalWins;
    private String avatar;

    public void setTotalWins(int totalWins){this.totalWins = totalWins;}
    public int getTotalWins(){return totalWins;}

    public void setToken(String token){this.token = token;}

    public String getToken(){return token;}

    public long getId() {
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

    public void setId(long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
