package model;

import java.time.LocalDate;

public class User {
    private String userName;
    private String password;
    private String information;
    private LocalDate passwordLastModified;


    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        passwordLastModified = LocalDate.now();
    }


    /**
     * get the userName of the User object.
     * @return String of the userName
     * */
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getPasswordLastModified() {
        return passwordLastModified;
    }

    /**
     * Set the information of the specific application.
     * @param information is information about the application that will be set.
     * */
    public void setInformation(String information){
        this.information = information;
    }


    /**
     * Change the password for specific user
     *
     * @param newPassword is the new password of the user to be set.
     * */
    public void changePassword(String newPassword){
        this.password = newPassword;
        passwordLastModified = LocalDate.now();
    }

    public String getInformation() {
        return information;
    }
}
