package ch.uzh.ifi.hase.soprafs23.constant;
import ch.uzh.ifi.hase.soprafs23.ships.Ship;

public class Player {
    private String name;
    private String password;
    private int shipsRemaining;
    public boolean isAlive;
    public int nrTotalWins;
    public Ship[] ships;

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


}
