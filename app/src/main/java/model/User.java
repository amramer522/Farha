package model;


public class User
{
    private String username, id, imageURl;

    public User() {

    }

    public User(String username, String id, String imageURl)
    {
        this.username = username;
        this.id = id;
        this.imageURl = imageURl;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageURl() {
        return imageURl;
    }

    public void setImageURl(String imageURl) {
        this.imageURl = imageURl;
    }
}