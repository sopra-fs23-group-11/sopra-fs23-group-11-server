package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class ShotPostDTO {
    private long attackerId;
    private long defenderId;
    private String posOfShot;

    public long getAttackerId() {
        return attackerId;
    }

    public void setAttackerId(long attackerId) {
        this.attackerId = attackerId;
    }

    public long getDefenderId() {
        return defenderId;
    }

    public void setDefenderId(long defenderId) {
        this.defenderId = defenderId;
    }

    public String getPosOfShot() {
        return posOfShot;
    }

    public void setPosOfShot(String posOfShot) {
        this.posOfShot = posOfShot;
    }
}
