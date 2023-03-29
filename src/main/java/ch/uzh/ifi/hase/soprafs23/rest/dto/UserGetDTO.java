package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;

import java.util.Date;

public class UserGetDTO {

    private Long id;
    private String name;
    private String username;
    private UserStatus status;
    private String birthDate;
    private String token;
    private String voldemort;

    public void setToken(String token){this.token = token;};

    public String getToken(){return token;}

    public void setVoldemort(String voldemort){this.voldemort = voldemort;}

    public String getVoldemort() {return voldemort;}

    public String getBirthDate() {return birthDate;};

    public void setBirthDate(String birthDate){this.birthDate = birthDate;};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
