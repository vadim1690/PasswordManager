package model;

import exceptions.*;
import view.UserInterface;

import java.util.*;

public class ManagementSystem implements ModelInterface {

    private static ManagementSystem managementSystem;

    private UserInterface controller;
    private Map<String, ApplicationRecord> applicationRecords;

    private ManagementSystem() {
        applicationRecords = new HashMap<>();
    }

    public static ManagementSystem getInstance(){
        if (managementSystem==null)
            managementSystem = new ManagementSystem();
        return managementSystem;
    }


    @Override
    public void registerController(UserInterface controller) {
        this.controller = controller;
    }

    @Override
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

    @Override
    public void addUserToApplication(String officialName, String userName, String password, String information) throws ApplicationDoesNotExistException, UserNameAlreadyExistException, IllegalApplicationNameException, IllegalUserNameException, IllegalPasswordException {
        checkIfApplicationNameIsValid(officialName);
        applicationRecords.get(officialName).addUser(userName, password, information);

    }

    public void checkIfApplicationNameIsValid(String officialName) throws ApplicationDoesNotExistException, IllegalApplicationNameException {
        checkIfApplicationNameIsIllegal(officialName);
        if (!applicationRecords.containsKey(officialName))
            throw new ApplicationDoesNotExistException(officialName);
    }
    private void checkIfApplicationNameIsIllegal(String officialName) throws IllegalApplicationNameException {
        if(officialName == null || officialName.isEmpty())
            throw new IllegalApplicationNameException();
    }

    @Override
    public void removeApplication(String officialName) throws ApplicationDoesNotExistException, IllegalApplicationNameException {
        checkIfApplicationNameIsValid(officialName);
        applicationRecords.remove(officialName);
    }

    @Override
    public void removeUserForApplication(String officialName, String userName, String password) throws ApplicationDoesNotExistException, UserNameDoesNotExistException, UserPasswordAuthenticationException, IllegalApplicationNameException, IllegalUserNameException, IllegalPasswordException {
        checkIfApplicationNameIsValid(officialName);
        applicationRecords.get(officialName).removeUser(userName, password);
    }

    @Override
    public void editApplicationInformation(String officialName, String information) throws ApplicationDoesNotExistException, IllegalApplicationNameException {
        checkIfApplicationNameIsValid(officialName);
        applicationRecords.get(officialName).setInformation(information);
    }

    @Override
    public void editUserInformation(String officialName, String userName, String information) throws ApplicationDoesNotExistException, UserNameDoesNotExistException, IllegalApplicationNameException, IllegalUserNameException {
        checkIfApplicationNameIsValid(officialName);
        applicationRecords.get(officialName).editUserInformation(userName,information);
    }

    @Override
    public void modifyPassword(String officialName, String userName, String oldPassword, String newPassword) throws ApplicationDoesNotExistException, UserNameDoesNotExistException, UserPasswordAuthenticationException, IllegalApplicationNameException, IllegalUserNameException, IllegalPasswordException {
        checkIfApplicationNameIsValid(officialName);
        getApplicationRecordByOfficialName(officialName).modifyPasswordForUser(userName,oldPassword,newPassword);
    }

    @Override
    public List<String> getAllApplicationForUserName(String userName) throws IllegalUserNameException {
        List<String> applicationsList = new ArrayList<>();
        for (ApplicationRecord applicationRecord: applicationRecords.values()) {
            if (applicationRecord.getUserByUserName(userName)!=null)
                applicationsList.add(applicationRecord.getOfficialName());
        }
        return applicationsList;
    }

    @Override
    public Map<String, String> getUserNameAndPasswordForApplication(String officialName) throws ApplicationDoesNotExistException, IllegalApplicationNameException {
        checkIfApplicationNameIsValid(officialName);
        return getApplicationRecordByOfficialName(officialName).getUsersMap();
    }

    @Override
    public Collection<ApplicationRecord> getAllApplications(){
        return applicationRecords.values();
    }

    public ApplicationRecord getApplicationRecordByOfficialName(String officialName){
        return applicationRecords.get(officialName);
    }




}
