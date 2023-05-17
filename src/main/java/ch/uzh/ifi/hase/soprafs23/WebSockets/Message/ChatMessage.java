package ch.uzh.ifi.hase.soprafs23.WebSockets.Message;

public class ChatMessage {
    private String lobbyCode;
    private String content;


    public String getLobbyCode() {
        return lobbyCode;
    }

    public void setLobbyCode(String lobbyCode) {this.lobbyCode = lobbyCode;}

    public String getContent() {return content;}

    public void setContent(String content) {this.content = content;}
}
