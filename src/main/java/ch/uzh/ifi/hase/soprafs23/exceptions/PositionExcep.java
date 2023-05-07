package ch.uzh.ifi.hase.soprafs23.exceptions;

public class PositionExcep extends RuntimeException{
    private String gameId;
    public PositionExcep(String message, String gameId) {
        super(message);
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }

}
