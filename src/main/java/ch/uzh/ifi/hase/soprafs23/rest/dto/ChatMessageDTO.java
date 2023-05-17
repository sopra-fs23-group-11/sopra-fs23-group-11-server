package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class ChatMessageDTO {
    private String lobbyCode;
    private String content;

    public String getLobbyCode() {
        return lobbyCode;
    }

    public void setLobbyCode(String lobbyCode) {this.lobbyCode = lobbyCode;}

    public String getContent() {return content;}

    public void setContent(String content) {this.content = content;}
}
