package ch.uzh.ifi.hase.soprafs23.WebSockets.Message;

public class FinishMsg {
    private String type = "Finished";
    private long winnerId;
    private long looserId;

    public FinishMsg(long winnerId, long looserId) {
        this.winnerId = winnerId;
        this.looserId = looserId;
    }

    public long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(long winnerId) {
        this.winnerId = winnerId;
    }

    public long getLooserId() {
        return looserId;
    }

    public void setLooserId(long looserId) {
        this.looserId = looserId;
    }
}
