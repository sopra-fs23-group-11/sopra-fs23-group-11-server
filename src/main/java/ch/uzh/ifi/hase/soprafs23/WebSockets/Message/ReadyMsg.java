package ch.uzh.ifi.hase.soprafs23.WebSockets.Message;

public class ReadyMsg { // after placing ships
    private String type ="Ready";
    private long playerId;
    private String playerName;

    private String playerBoard;



    private String playerAvatar;

    public ReadyMsg(long playerId, String playerName, String playerBoard, String playerAvatar) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerBoard = playerBoard;
        this.playerAvatar = playerAvatar;
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

    public String getPlayerAvatar() {
        return playerAvatar;
    }

    public void setPlayerAvatar(String playerAvatar) {
        this.playerAvatar = playerAvatar;
    }
}
