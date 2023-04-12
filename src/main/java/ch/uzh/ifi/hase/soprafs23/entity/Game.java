package ch.uzh.ifi.hase.soprafs23.entity;
import javax.persistence.*;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private Player player1;

    @OneToOne
    private Player player2;
}
