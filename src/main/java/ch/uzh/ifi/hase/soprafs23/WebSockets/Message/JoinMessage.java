package ch.uzh.ifi.hase.soprafs23.WebSockets.Message;

public class JoinMessage {
    private String type="Join";
    private String lobbyCode;
    private long joinerId;
    private String joinerName;

    public JoinMessage( String lobbyCode, long joinerId, String joinerName) {
        this.lobbyCode = lobbyCode;
        this.joinerId = joinerId;
        this.joinerName = joinerName;
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

    public long getJoinerId() {
        return joinerId;
    }

    public void setJoinerId(long joinerId) {
        this.joinerId = joinerId;
    }

    public String getJoinerName() {
        return joinerName;
    }

    public void setJoinerName(String joinerName) {
        this.joinerName = joinerName;
    }
}
