package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class UserPutDTO {
    private String name;
    private String username;
    private String token;
    private Long id;
    private String birthDate;

    public String getName() {
        return name;
    }
    public String getUsername() {
        return username;
    }
    public String getToken(){return token;}
    public Long getId(){return id;}
    public String getBirthDate(){return birthDate;}
    public void setUsername(String username){this.username = username;}
    public void setBirthDate(String birthDate){this.birthDate = birthDate;}
    public void setId(Long id){this.id = id;}
}
