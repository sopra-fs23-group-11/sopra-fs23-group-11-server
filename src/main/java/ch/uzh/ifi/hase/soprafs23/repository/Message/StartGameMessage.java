package ch.uzh.ifi.hase.soprafs23.repository.Message;

public class StartGameMessage { // after having joiner and host in a lobby
    private String type;
    private String lobbyCode;
    private long attackerId;
    private long defenderId;
    private String attackerName;
    private String defenderName;

    public StartGameMessage(String type, String lobbyCode, long attackerId, long defenderId, String attackerName, String defenderName) {
        this.type = type;
        this.lobbyCode = lobbyCode;
        this.attackerId = attackerId;
        this.defenderId = defenderId;
        this.attackerName = attackerName;
        this.defenderName = defenderName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLobbyCode() {
        return lobbyCode;
    }

    public void setLobbyCode(String lobbyCode) {
        this.lobbyCode = lobbyCode;
    }

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

    public String getAttackerName() {
        return attackerName;
    }

    public void setAttackerName(String attackerName) {
        this.attackerName = attackerName;
    }

    public String getDefenderName() {
        return defenderName;
    }

    public void setDefenderName(String defenderName) {
        this.defenderName = defenderName;
    }
}
