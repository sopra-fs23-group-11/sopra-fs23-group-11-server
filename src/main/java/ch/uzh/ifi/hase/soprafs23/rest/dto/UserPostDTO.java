package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class UserPostDTO {

  private String username;
  private String password;
  private String avatar;

  public String getAvatar(){return avatar;}
    public void setAvatar(String avatar){this.avatar = avatar;}

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
