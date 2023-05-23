package ch.uzh.ifi.hase.soprafs23.entity;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;

import javax.persistence.*;
import java.util.List;
/* To avoid redundancy we defined the classes player and user. whenever a user enters a game he "turns into a player"
   in the player we will keep track of the needed information "during" a game e.g ships, shots. After the game the players
   information is not needed anymore and we will just update the totalWinse of him as a "User" if he won.
* */

@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue
    private Long id;
    @OneToMany(mappedBy = "player",fetch = FetchType.EAGER)
    private List<ShipPlayer> shipPlayers;
    @ManyToOne
    private User user;
    @OneToOne(mappedBy = "player1")
    private Game gamePlayer1;

    @OneToOne(mappedBy = "player2")
    private Game gamePlayer2;

    @Column
    private int shipsRemaining; // shipsRemaining ==0 <-> isAlive == true

    @OneToMany(mappedBy = "attacker")
    private List<Shot> shotsAttack;

    @OneToMany (mappedBy = "defender")
    private List<Shot> shotsDefend;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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



    public void setGamePlayer1(Game gamePlayer1) {
        this.gamePlayer1 = gamePlayer1;
    }

    public Game getGame(){
        if (gamePlayer1!=null){
            return gamePlayer1;
        }else{
            return gamePlayer2;
        }
    }
    public void setGamePlayer2(Game gamePlayer2) {
        this.gamePlayer2 = gamePlayer2;
    }

    public List<Shot> getShotsAttack() {
        return shotsAttack;
    }

//    public void setShotsAttack(List<Shot> shotsAttack) {
//        this.shotsAttack = shotsAttack;
//    }

    public List<Shot> getShotsDefend() {
        return shotsDefend;
    }

//    public void setShotsDefend(List<Shot> shotsDefend) {
//        this.shotsDefend = shotsDefend;
//    }


}
