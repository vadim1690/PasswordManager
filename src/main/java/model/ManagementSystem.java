package model;

import database.Datasource;
import exceptions.*;

import java.sql.SQLException;
import java.util.*;

public class ManagementSystem {

    private static ManagementSystem managementSystem;
    private final String GENERAL_INFORMATION = "Hello and welcome to Vadim Lazarevich's Password Manager software for saving users and passwords for websites / applications.\n\n" +
            "You will enjoy a convenient interface for saving users and quick access to them, the passwords and users are stored in an encoded manner in the database.\n\n" +
            "You can get an indication of the strength of your passwords and thus change it accordingly. In order to receive information about the password strength cataloging, click on the \"Password strength\" button in the side menu.\n\n" +
            "The software was created by Vadim Lazarevich to meet his basic need to save passwords more conveniently for the user.";
    private Map<String, ApplicationRecord> applicationRecords;

    private ManagementSystem() {
        applicationRecords = new HashMap<>();

    }

    public static ManagementSystem getInstance() {
        if (managementSystem == null)
            managementSystem = new ManagementSystem();
        return managementSystem;
    }


    public Collection<ApplicationRecord> readFromDatabase() {
        try {
            List<ApplicationRecord> appsFromDatabase = Datasource.getInstance().readApplicationRecords();
            appsFromDatabase.forEach(app -> applicationRecords.put(app.getOfficialName(), app));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return applicationRecords.values();
    }

    public void saveToDatabase() {

        for (ApplicationRecord applicationRecord : applicationRecords.values()) {
            try {
                Datasource.getInstance().saveApplicationRecord(applicationRecord);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    public void addApplication(String officialName, String information) throws ApplicationAlreadyExistException, IllegalApplicationNameException, ApplicationDoesNotExistException {
        checkIfApplicationExist(officialName);
        ApplicationRecord applicationRecord = new ApplicationRecord(officialName);
        applicationRecords.put(officialName, applicationRecord);
        editApplicationInformation(officialName, information);

    }

    private void checkIfApplicationExist(String officialName) throws ApplicationAlreadyExistException, IllegalApplicationNameException {
        checkIfApplicationNameIsIllegal(officialName);
        if (applicationRecords.containsKey(officialName))
            throw new ApplicationAlreadyExistException(officialName);
    }


    public User addUserToApplication(String officialName, String userName, String password, String information) throws ApplicationDoesNotExistException, UserNameAlreadyExistException, IllegalApplicationNameException, IllegalUserNameException, IllegalPasswordException {
        checkIfApplicationNameIsValid(officialName);
        return applicationRecords.get(officialName).addUser(userName, password, information);

    }

    public void checkIfApplicationNameIsValid(String officialName) throws ApplicationDoesNotExistException, IllegalApplicationNameException {
        checkIfApplicationNameIsIllegal(officialName);
        if (!applicationRecords.containsKey(officialName))
            throw new ApplicationDoesNotExistException(officialName);
    }

    private void checkIfApplicationNameIsIllegal(String officialName) throws IllegalApplicationNameException {
        if (officialName == null || officialName.isEmpty())
            throw new IllegalApplicationNameException();
    }


    public void removeApplication(String officialName) throws ApplicationDoesNotExistException, IllegalApplicationNameException {
        checkIfApplicationNameIsValid(officialName);
        applicationRecords.remove(officialName);
    }


    public void removeUserForApplication(String officialName, String userName, String password) throws ApplicationDoesNotExistException, UserNameDoesNotExistException, UserPasswordAuthenticationException, IllegalApplicationNameException, IllegalUserNameException, IllegalPasswordException {
        checkIfApplicationNameIsValid(officialName);
        applicationRecords.get(officialName).removeUser(userName, password);
    }


    public void editApplicationInformation(String officialName, String information) throws ApplicationDoesNotExistException, IllegalApplicationNameException {
        checkIfApplicationNameIsValid(officialName);
        applicationRecords.get(officialName).setInformation(information);
    }


    public void editUserInformation(String officialName, String userName, String information) throws ApplicationDoesNotExistException, UserNameDoesNotExistException, IllegalApplicationNameException, IllegalUserNameException {
        checkIfApplicationNameIsValid(officialName);
        applicationRecords.get(officialName).editUserInformation(userName, information);
    }


    public void modifyPassword(String officialName, String userName, String oldPassword, String newPassword) throws ApplicationDoesNotExistException, UserNameDoesNotExistException, UserPasswordAuthenticationException, IllegalApplicationNameException, IllegalUserNameException, IllegalPasswordException {
        checkIfApplicationNameIsValid(officialName);
        getApplicationRecordByOfficialName(officialName).modifyPasswordForUser(userName, oldPassword, newPassword);
    }

    //Fix this method it should not return the list, it should pass it to the according method in the view interface

    public List<String> getAllApplicationsForUserName(String userName) throws IllegalUserNameException, UserNameDoesNotExistException {
        List<String> applicationsList = new ArrayList<>();
        for (ApplicationRecord applicationRecord : applicationRecords.values()) {
            if (applicationRecord.getUserByUserName(userName) != null)
                applicationsList.add(applicationRecord.getOfficialName());
        }
        return applicationsList;
    }

    //Fix this method it should not return the list, it should pass it to the according method in the view interface

    public Collection<User> getUserNameAndPasswordForApplication(String officialName) throws ApplicationDoesNotExistException, IllegalApplicationNameException {
        checkIfApplicationNameIsValid(officialName);
        return getApplicationRecordByOfficialName(officialName).getUsersList();
    }

    //Fix this method it should not return the list, it should pass it to the according method in the view interface

    public Collection<ApplicationRecord> getAllApplications() {
        return applicationRecords.values();
    }

    //Fix this method it should not return the list, it should pass it to the according method in the view interface
    public ApplicationRecord getApplicationRecordByOfficialName(String officialName) {
        return applicationRecords.get(officialName);
    }

    //Fix this method it should not return the list, it should pass it to the according method in the view interface
    //Application Records should not be accessible from outside the class, this method is only for test purposes.
    public Map<String, ApplicationRecord> getApplicationRecords() {
        return applicationRecords;
    }

    public String getGeneralInformation() {
        return GENERAL_INFORMATION;
    }
}
