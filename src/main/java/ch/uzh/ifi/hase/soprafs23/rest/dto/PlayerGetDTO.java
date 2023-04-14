package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.entity.Shot;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;

import java.util.List;

public class PlayerGetDTO {

    private Long id;
    private List<ShipPlayer> shipPlayers;
    private List<Shot> shotsAttack;
    private List<Shot> shotsDefend;
    public List<Shot> getShotsDefend(){return shotsDefend;}
    public void setShotsDefend(List<Shot> shotsDefend){this.shotsDefend= shotsDefend;}
    public List<Shot> getShotsAttack(){return shotsAttack;}
    public void setShotsAttack(List<Shot> shotsAttack){this.shotsAttack= shotsAttack;}
    public List<ShipPlayer> getShipPlayers(){return shipPlayers;}
    public void setShipPlayers(List<ShipPlayer> shipPlayers){this.shipPlayers = shipPlayers;}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
