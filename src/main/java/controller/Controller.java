package controller;

import exceptions.*;
import model.ApplicationRecord;
import model.ModelInterface;
import view.UserInterface;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Controller implements UserInterface, ModelInterface {

    private ModelInterface model;
    private UserInterface view;

    public Controller(ModelInterface model, UserInterface view) {
        this.model = model;
        this.view = view;
        registerController((ModelInterface) this);
        registerController((UserInterface) this);


    }



    @Override
    public void addApplication(String officialName, String information) throws ApplicationAlreadyExistException, ApplicationDoesNotExistException, IllegalApplicationNameException {
        model.addApplication(officialName, information);
    }

    @Override
    public void addUserToApplication(String officialName, String userName, String password, String information) throws ApplicationDoesNotExistException, UserNameAlreadyExistException, IllegalUserNameException, IllegalPasswordException, IllegalApplicationNameException {
        model.addUserToApplication(officialName, userName, password, information);
    }

    @Override
    public void removeApplication(String officialName) throws ApplicationDoesNotExistException, IllegalApplicationNameException {
        model.removeApplication(officialName);
    }

    @Override
    public void removeUserForApplication(String officialName, String userName, String password) throws IllegalUserNameException, IllegalPasswordException, ApplicationDoesNotExistException, UserNameDoesNotExistException, UserPasswordAuthenticationException, IllegalApplicationNameException {
        model.removeUserForApplication(officialName, userName, password);
    }

    @Override
    public void editApplicationInformation(String officialName, String information) throws ApplicationAlreadyExistException, ApplicationDoesNotExistException, IllegalApplicationNameException {
        model.editApplicationInformation(officialName, information);
    }

    @Override
    public void editUserInformation(String officialName, String userName, String information) throws IllegalUserNameException, ApplicationDoesNotExistException, UserNameDoesNotExistException, IllegalApplicationNameException {
        model.editUserInformation(officialName, userName, information);
    }

    @Override
    public void modifyPassword(String officialName, String userName, String oldPassword, String newPassword) throws IllegalUserNameException, IllegalPasswordException, ApplicationDoesNotExistException, UserNameDoesNotExistException, UserPasswordAuthenticationException, IllegalApplicationNameException {
        model.modifyPassword(officialName, userName, oldPassword, newPassword);
    }

    //Fix this method it should not return the list, it should pass it to the according method in the view interface
    @Override
    public List<String> getAllApplicationsForUserName(String userName) throws IllegalUserNameException, UserNameDoesNotExistException {
        return model.getAllApplicationsForUserName(userName);
    }


    //Fix this method it should not return the list, it should pass it to the according method in the view interface
    @Override
    public Map<String, String> getUserNameAndPasswordForApplication(String officialName) throws ApplicationDoesNotExistException, IllegalApplicationNameException {
        return model.getUserNameAndPasswordForApplication(officialName);
    }

    @Override
    public Collection<ApplicationRecord> getAllApplications() {
        return model.getAllApplications();
    }

    @Override
    public void registerController(ModelInterface controller) {
        view.registerController(controller);
    }
    @Override
    public void registerController(UserInterface controller) {
        model.registerController(controller);
    }
}
