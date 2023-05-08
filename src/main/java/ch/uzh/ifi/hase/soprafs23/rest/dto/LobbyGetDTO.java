package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class LobbyGetDTO {
    private String lobbyCode;
    private String hostName;
    private String joinerName;
    private Long hostId;
    private Long joinerId;

    public String getLobbyCode() {
        return lobbyCode;
    }

    public void setLobbyCode(String lobbyCode) {
        this.lobbyCode = lobbyCode;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public long getHostId() {
        return hostId;
    }

    public void setHostId(long hostId) {
        this.hostId = hostId;
    }
    public Long getJoinerId(){return joinerId;}
    public void setJoinerId(Long joinerId) {this.joinerId = joinerId;}

    public String getJoinerName() {
        return joinerName;
    }

    public void setJoinerName(String joinerName) {
        this.joinerName = joinerName;
    }
}
