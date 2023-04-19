package ch.uzh.ifi.hase.soprafs23.entity;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "Lobby")
public class Lobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lobbyId;

    @Column
    private String lobbyCode;

    @OneToOne (mappedBy = "lobbyForHost")
    private User host;

    @OneToOne (mappedBy = "lobbyForJoiner")
    private User joiner;

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
