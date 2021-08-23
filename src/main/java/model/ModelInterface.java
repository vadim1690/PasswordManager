package model;

import exceptions.*;
import view.UserInterface;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface ModelInterface {

    void registerController(UserInterface controller);
    void addApplication(String officialName,String information) throws ApplicationAlreadyExistException, IllegalApplicationNameException, ApplicationDoesNotExistException;
    void addUserToApplication(String officialName,String userName,String password,String information) throws ApplicationDoesNotExistException, UserNameAlreadyExistException, IllegalApplicationNameException, IllegalUserNameException, IllegalPasswordException;
    void removeApplication(String officialName) throws ApplicationDoesNotExistException, IllegalApplicationNameException;
    void removeUserForApplication(String officialName,String userName,String password) throws ApplicationDoesNotExistException, UserNameDoesNotExistException, UserPasswordAuthenticationException, IllegalApplicationNameException, IllegalUserNameException, IllegalPasswordException;
    void editApplicationInformation(String officialName,String information) throws ApplicationAlreadyExistException, ApplicationDoesNotExistException, IllegalApplicationNameException;
    void editUserInformation(String officialName,String userName,String information) throws ApplicationDoesNotExistException, UserNameDoesNotExistException, IllegalApplicationNameException, IllegalUserNameException;
    void modifyPassword(String officialName,String userName,String oldPassword,String newPassword) throws ApplicationDoesNotExistException, UserNameDoesNotExistException, UserPasswordAuthenticationException, IllegalApplicationNameException, IllegalUserNameException, IllegalPasswordException;
    List<String> getAllApplicationsForUserName(String userName) throws IllegalUserNameException, UserNameDoesNotExistException;
    Map<String,String> getUserNameAndPasswordForApplication(String officialName) throws ApplicationDoesNotExistException, IllegalApplicationNameException;
    Collection<ApplicationRecord> getAllApplications();

}
