package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class NewGameDTO {



    private String lobbyCode;



    private long enemyId;

    public String getLobbyCode() {
        return lobbyCode;
    }

    public void setLobbyCode(String lobbyCode) {
        this.lobbyCode = lobbyCode;
    }

    public long getEnemyId() {
        return enemyId;
    }

    public void setEnemyId(long enemyId) {
        this.enemyId = enemyId;
    }

}
