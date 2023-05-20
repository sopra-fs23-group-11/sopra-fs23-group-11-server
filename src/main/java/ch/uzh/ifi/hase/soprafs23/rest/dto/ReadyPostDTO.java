package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class ReadyPostDTO {
    private String gameId;
    private long playerId;
    private String playerName;
    private String playerAvatar;

    private String playerBoard;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
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

    public String getPlayerAvatar(){return playerAvatar; }

    public void setPlayerAvatar(String playerAvatar) {
        this.playerAvatar = playerAvatar;
    }
}
