package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;

import java.util.List;

public class PlayerPutDTO {
    private String name;
    private String password;
    private String token;
    private Long id;
    private List<Ship> ships;
    public List<Ship> getShips(){return ships;}
    public void setShips(List<Ship> ships){this.ships = ships;}
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
