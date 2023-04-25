package ch.uzh.ifi.hase.soprafs23.WebSockets.Message;

public class ErrorMsg {
    private String type = "Error";
    private String errorMsg;


    public ErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;

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


}
