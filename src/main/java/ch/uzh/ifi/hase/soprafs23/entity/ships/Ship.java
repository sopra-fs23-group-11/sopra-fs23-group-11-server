package ch.uzh.ifi.hase.soprafs23.entity.ships;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "ships")
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int length;
    @Column(nullable = false)
    private String type;

    @OneToMany(mappedBy = "ship")
    private List<ShipPlayer> shipPlayers;

    public Ship() {
    }

    public Ship(Long id, int length, String type) {
        this.id = id;
        this.length = length;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<ShipPlayer> getShipPlayers() {
        return shipPlayers;
    }

    public void setShipPlayers(List<ShipPlayer> shipPlayers) {
        this.shipPlayers = shipPlayers;
    }


    public void decrementSize() {
        this.length -= 1;
    }


}
