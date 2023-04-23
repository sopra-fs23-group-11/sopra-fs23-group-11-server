package ch.uzh.ifi.hase.soprafs23.entity;
import javax.persistence.*;

@Entity
@Table(name = "game")
public class Game {

    @Id
    private String id;

    @OneToOne
    private Player player1;

    @OneToOne
    private Player player2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
}
