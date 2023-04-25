package ch.uzh.ifi.hase.soprafs23.WebSockets.Message;

public class ReadyMsg { // after placing ships
    private String type ="Ready";
    private long playerId;
    private String playerName;

    public ReadyMsg(long playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
