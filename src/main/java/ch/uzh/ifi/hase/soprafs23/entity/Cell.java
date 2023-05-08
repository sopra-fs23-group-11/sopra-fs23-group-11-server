package ch.uzh.ifi.hase.soprafs23.entity;

import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "CELL")
public class Cell implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uniqueId;

    @Column
    private Long ownerId;

    @Column
    private String id;

    @Column
    private boolean isShotAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ship_id")
    private Ship occupyingShip;

    public void setOwnerId(Long ownerId){this.ownerId = ownerId;}
    public Long getOwnerId(){return this.ownerId;}

    public void setIsShotAt(boolean isShotAt){this.isShotAt = isShotAt;}
    public Boolean getIsShotAt(){return isShotAt;}

    public Ship getOccupyingShip() {
        return occupyingShip;
    }

    public void setOccupyingShip(Ship occupyingShip) {
        this.occupyingShip = occupyingShip;
    }

    public void setId(String id){this.id = id;}
    public String getId(){return id;}

    public Long getUniqueId(){return uniqueId;}
}
