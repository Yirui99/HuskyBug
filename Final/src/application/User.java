package application;

public class User {
    private int userID;
    private String username;
    private String password; 
    private String email;
    private String phone;

    public User(int userID, String username, String password, String email, String phone) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() { 
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

}
