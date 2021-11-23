package model;

import utilities.PasswordRegex;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class User {
    private final String DATE_PATTERN = "dd/MM/YYYY";

    private final String userName;
    private String password;
    private String information;
    private String passwordLastModified;
    private PasswordStrength passwordStrength;


    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        setPasswordStrength();
        passwordLastModified = DateTimeFormatter.ofPattern(DATE_PATTERN).format(LocalDate.now());
    }

    public User(String userName,String password , String information){
        this(userName, password);
        setInformation(information);
    }

    public User(String userName, String password, String information, String passwordLastModified) {
        this(userName, password,information);
        this.passwordLastModified = passwordLastModified;
    }


    /**
     * get the userName of the User object.
     *
     * @return String of the userName
     */
    public String getUserName() {
        return userName;
    }


    private void setPasswordStrength() {
        passwordStrength = PasswordRegex.computePassword(password);
    }

    public PasswordStrength getPasswordStrength() {
        return passwordStrength;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordLastModified() {
        return passwordLastModified;
    }

    public String getInformation() {
        return information;
    }

    /**
     * Set the information of the specific application.
     *
     * @param information is information about the application that will be set.
     */
    public void setInformation(String information) {
        this.information = information;
    }
}
