package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class PlayerPutDTO {
    private String name;
    private String password;
    private String token;
    private Long id;
    private String ships;
    public String getShips(){return ships;}
    public void setShips(String ships){this.ships = ships;}
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public String getToken(){return token;}
    public Long getId(){return id;}
    public void setPassword(String password){this.password = password;}
    public void setId(Long id){this.id = id;}
}
