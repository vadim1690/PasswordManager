package controller;

import exceptions.ApplicationAlreadyExistException;
import exceptions.ApplicationDoesNotExistException;
import exceptions.UserNameAlreadyExistException;
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
    public void registerController(UserInterface controller) {
        model.registerController(controller);
    }

    @Override
    public void addApplication(String officialName, String information) throws ApplicationAlreadyExistException {

    }

    @Override
    public void addUserToApplication(String officialName, String userName, String password, String information) throws ApplicationDoesNotExistException, UserNameAlreadyExistException {

    }

    @Override
    public void removeApplication(String officialName) {

    }

    @Override
    public void removeUserForApplication(String officialName, String userName, String password) {

    }

    @Override
    public void editApplicationInformation(String officialName, String information) throws ApplicationAlreadyExistException, ApplicationDoesNotExistException {

    }

    @Override
    public void editUserInformation(String officialName, String userName, String information) {

    }

    @Override
    public void modifyPassword(String officialName, String userName, String oldPassword, String newPassword) {

    }

    @Override
    public List<String> getAllApplicationForUserName(String userName) {
        return null;
    }

    @Override
    public Map<String, String> getUserNameAndPasswordForApplication(String officialName) {
        return null;
    }

    @Override
    public Collection<ApplicationRecord> getAllApplications() {
        return null;
    }

    @Override
    public void registerController(ModelInterface controller) {
        view.registerController(controller);
    }
}
