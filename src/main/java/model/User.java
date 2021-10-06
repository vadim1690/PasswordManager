package model;

import utilities.PasswordRegex;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class User {
    private static final  String DATE_PATTERN = "dd/MM/YYYY";

    private final String userName;
    private String password;
    private String information;
    private String passwordLastModified;
    private PasswordStrength passwordStrength;




    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        setPasswordStrength();
        passwordLastModified =  DateTimeFormatter.ofPattern(DATE_PATTERN).format(LocalDate.now());
    }



    /**
     * get the userName of the User object.
     * @return String of the userName
     * */
    public String getUserName() {
        return userName;
    }



    private void setPasswordStrength(){
        passwordStrength = PasswordRegex.computePassword(password);
    }
    public PasswordStrength getPasswordStrength(){
        return passwordStrength;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordLastModified() {
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
        passwordLastModified = DateTimeFormatter.ofPattern(DATE_PATTERN).format(LocalDate.now());
        setPasswordStrength();
    }

    public String getInformation() {
        return information;
    }
}
