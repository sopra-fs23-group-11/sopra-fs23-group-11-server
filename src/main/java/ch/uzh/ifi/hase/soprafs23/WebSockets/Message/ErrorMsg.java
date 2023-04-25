package ch.uzh.ifi.hase.soprafs23.WebSockets.Message;

public class ErrorMsg {
    private String type = "Error";
    private String errorMsg;
    private String playerName;

    public ErrorMsg(String errorMsg, String playerName) {
        this.errorMsg = errorMsg;
        this.playerName = playerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
