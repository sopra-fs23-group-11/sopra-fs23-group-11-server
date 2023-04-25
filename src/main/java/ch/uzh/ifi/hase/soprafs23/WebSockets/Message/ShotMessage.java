package ch.uzh.ifi.hase.soprafs23.WebSockets.Message;

public class ShotMessage {
    private long attackerId;
    private long defenderId;
    private String posOfShot;
    private boolean isHit;

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

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }
}
