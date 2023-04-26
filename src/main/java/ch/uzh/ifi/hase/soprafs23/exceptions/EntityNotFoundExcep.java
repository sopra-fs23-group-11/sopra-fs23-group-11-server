package ch.uzh.ifi.hase.soprafs23.exceptions;

public class EntityNotFoundExcep extends RuntimeException{
    private String gameId;
    public EntityNotFoundExcep (String message, String gameId){
        super(message);
        this.gameId= gameId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
