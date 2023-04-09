package ch.uzh.ifi.hase.soprafs23.entity;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "player")
    private List<ShipPlayer> shipPlayers;
    @ManyToOne
    private User user;
    @OneToOne(mappedBy = "player1")
    private Game gamePlayer1;

    @OneToOne(mappedBy = "player2")
    private Game gamePlayer2;

    private int shipsRemaining; // shipsRemaining ==0 <-> isAlive == true

    /*
    @OneToMany(mappedBy = "player")
    public Ship[] ships;

    @OneToMany(mappedBy = "player") // with only @Column there was a problem caused by javax.persistence.PersistenceException
    private List<Ship> ships;

    public List<Ship> getShips(){
        return ships;
    }

    public void setShips(List<Ship> ships){
        this.ships = ships;
    } */

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public int getShipsRemaining() {
        return shipsRemaining;
    }

    public void setShipsRemaining(int shipsRemaining) {
        this.shipsRemaining = shipsRemaining;
    }
    public List<ShipPlayer> getShipPlayers() {
        return shipPlayers;
    }

    public void setShipPlayers(List<ShipPlayer> shipPlayers) {
        this.shipPlayers = shipPlayers;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGamePlayer1() {
        return gamePlayer1;
    }

    public void setGamePlayer1(Game gamePlayer1) {
        this.gamePlayer1 = gamePlayer1;
    }

    public Game getGamePlayer2() {
        return gamePlayer2;
    }

    public void setGamePlayer2(Game gamePlayer2) {
        this.gamePlayer2 = gamePlayer2;
    }
}
