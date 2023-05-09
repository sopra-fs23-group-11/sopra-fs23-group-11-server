package ch.uzh.ifi.hase.soprafs23.WebSockets.Message;

public class ReadyMsg { // after placing ships
    private String type ="Ready";
    private long playerId;
    private String playerName;

    private String playerBoard;

    public ReadyMsg(long playerId, String playerName, String playerBoard) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerBoard = playerBoard;
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

    public String getPlayerBoard() {
        return playerBoard;
    }

    public void setPlayerBoard(String playerBoard) {
        this.playerBoard = playerBoard;
    }
}
