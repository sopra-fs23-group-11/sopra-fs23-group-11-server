package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class PlayerGetDTO {

    private Long id;
    private String name;
    private String password;
    private String token;

    public void setToken(String token){this.token = token;};

    public String getToken(){return token;}

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
