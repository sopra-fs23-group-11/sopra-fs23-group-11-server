package ch.uzh.ifi.hase.soprafs23.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "Lobby")
public class Lobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lobbyId;

    @Column
    private String lobbyCode;

    @OneToOne (mappedBy = "lobby")
    private User host;

    @OneToOne (mappedBy = "lobby")
    private User joiner;

    public long getLobbyId() {
        return lobbyId;
    }

    public Lobby() {
        // generate random 4-digit lobby code
        Random random = new Random();
        this.lobbyCode = String.format("%04d", random.nextInt(10000));
    }

    public String getLobbyCode() {
        return lobbyCode;
    }

    public void setLobbyCode(String lobbyCode) {
        this.lobbyCode = lobbyCode;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public User getJoiner() {
        return joiner;
    }

    public void setJoiner(User joiner) {
        this.joiner = joiner;
    }
}
