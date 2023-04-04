package ch.uzh.ifi.hase.soprafs23.entity;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;
import javax.persistence.*;
import java.util.List;

// We may not need all getter & setter. We also may not need to save all the attributes in the DB
//
@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String token;

    private int shipsRemaining;
    public boolean isAlive;
    @Column
    public int nrTotalWins;
    /*
    @OneToMany(mappedBy = "player")
    public Ship[] ships;*/

    @Column
    private List<Ship> ships;

    public List<Ship> getShips(){
        return ships;
    }

    public void setShips(List<Ship> ships){
        this.ships = ships;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getNrTotalWins() {
        return nrTotalWins;
    }

    public void setNrTotalWins(int nrTotalWins) {
        this.nrTotalWins = nrTotalWins;
    }
/*
    public Ship[] getShips() {
        return ships;
    }

    public void setShips(Ship[] ships) {
        this.ships = ships;
    } */

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

    public int getShipsRemaining() {
        return shipsRemaining;
    }

    public void setShipsRemaining(int shipsRemaining) {
        this.shipsRemaining = shipsRemaining;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
