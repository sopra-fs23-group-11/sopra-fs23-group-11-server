package ch.uzh.ifi.hase.soprafs23.WebSockets.Message;

public class SunkMsg {
    private String type="Sunk";
    private long defenderId;


    public SunkMsg(long defenderId) {
        this.defenderId = defenderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getDefenderId() {
        return defenderId;
    }

    public void setDefenderId(long defenderId) {
        this.defenderId = defenderId;
    }

}
