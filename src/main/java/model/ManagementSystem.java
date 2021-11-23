package model;

import database.ApplicationRecordDAO;
import database.UserDAO;
import exceptions.*;

import java.sql.SQLException;
import java.util.*;

public class ManagementSystem {

    private static ManagementSystem managementSystem;
    private ApplicationRecordService applicationRecordService;
    private UserService userService;
    private final String GENERAL_INFORMATION = "Hello and welcome to Vadim Lazarevich's Password Manager software for saving users and passwords for websites / applications.\n\n" +
            "You will enjoy a convenient interface for saving users and quick access to them, the passwords and users are stored in an encoded manner in the database.\n\n" +
            "You can get an indication of the strength of your passwords and thus change it accordingly. In order to receive information about the password strength cataloging, click on the \"Password strength\" button in the side menu.\n\n" +
            "The software was created by Vadim Lazarevich to meet his basic need to save passwords more conveniently for the user.";


    private ManagementSystem() {
        applicationRecordService = new ApplicationRecordService(new ApplicationRecordDAO());
        userService = new UserService(new UserDAO());
    }

    public static ManagementSystem getInstance() {
        if (managementSystem == null)
            managementSystem = new ManagementSystem();
        return managementSystem;
    }


    public void addApplication(String officialName, String information) throws ApplicationAlreadyExistException, IllegalApplicationNameException, ApplicationDoesNotExistException, SQLException {
        applicationRecordService.saveApplicationRecord(new ApplicationRecord(officialName,information));
    }

    public User addUser(String officialName, String userName, String password, String information) throws ApplicationDoesNotExistException, UserNameAlreadyExistException, SQLException {
        if (!applicationRecordService.checkIfApplicationExist(officialName))
            throw new ApplicationDoesNotExistException(officialName);
        return userService.saveUser( userName,password,information,officialName);

    }

    public void removeApplication(String officialName) throws SQLException {
        applicationRecordService.deleteApplication(officialName);
    }

    public void removeUserForApplication(String officialName, String userName) throws SQLException {
        userService.deleteUser(userName,officialName);
    }

    public void editApplicationInformation(String officialName, String information) throws ApplicationDoesNotExistException, IllegalApplicationNameException {
        //will be used in the future
    }


    public void editUserInformation(String officialName, String userName, String information) throws ApplicationDoesNotExistException, UserNameDoesNotExistException, IllegalApplicationNameException, IllegalUserNameException {
        //will be used in the future
    }


    public void modifyPassword(String officialName, String userName, String oldPassword, String newPassword) throws ApplicationDoesNotExistException, UserNameDoesNotExistException, UserPasswordAuthenticationException, IllegalApplicationNameException, IllegalUserNameException, IllegalPasswordException, SQLException {
        if(!applicationRecordService.checkIfApplicationExist(officialName))
            throw new ApplicationDoesNotExistException(officialName);
        userService.modifyPassword(officialName,userName,oldPassword,newPassword);
    }

    //not used yet...
    public List<String> getAllApplicationsForUserName(String userName) throws IllegalUserNameException, UserNameDoesNotExistException {
        return null;
    }

    public List<User> getAllUsersForApplication(String officialName) throws ApplicationDoesNotExistException, IllegalApplicationNameException, SQLException {
        if (!applicationRecordService.checkIfApplicationExist(officialName))
            throw new ApplicationDoesNotExistException(officialName);
        return userService.readAllUsersForApplication(officialName);
    }

    public List<ApplicationRecord> getAllApplications() throws SQLException {
        return applicationRecordService.readAllApplications();
    }

    public String getApplicationInformation(String officialName) throws ApplicationDoesNotExistException, SQLException {
       return applicationRecordService.readApplicationInformation(officialName);

    }
    public String getGeneralInformation() {
        return GENERAL_INFORMATION;
    }
}
