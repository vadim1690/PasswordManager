package model;

import exceptions.*;

import java.util.HashMap;
import java.util.Map;

public class ApplicationRecord {

    private String officialName;
    private Map<String, User> users;
    private String information;

    public ApplicationRecord(String officialName) {
        this.officialName = officialName;
        users = new HashMap<>();
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

    /**
     * Add User to the users list of the specific application.
     *
     * @param userName is the userName of the user to be added
     * @param password is the password of the user to be added
     */
    public void addUser(String userName, String password, String information) throws UserNameAlreadyExistException, IllegalUserNameException, IllegalPasswordException {
        checkIllegalUserName(userName);
        checkIllegalPassword(password);
        checkUserExist(userName);

        User user = new User(userName, password);
        user.setInformation(information);
        users.put(userName, user);
    }

    private void checkIllegalUserName(String userName) throws IllegalUserNameException {
        if (userName == null ||userName.isEmpty())
            throw new IllegalUserNameException();
    }

    private void checkIllegalPassword(String password) throws IllegalPasswordException {
        if (password == null ||password.isEmpty())
            throw new IllegalPasswordException();
    }

    private void checkUserExist(String userName) throws UserNameAlreadyExistException, IllegalUserNameException {
        if (checkIfValidUserName(userName)) {
            throw new UserNameAlreadyExistException(userName, this.officialName);
        }
    }

    public int getNumberOfUsers() {
        return users.size();
    }


    public void editUserInformation(String userName, String information) throws UserNameDoesNotExistException, IllegalUserNameException {
        if(!checkIfValidUserName(userName)){
            throw new UserNameDoesNotExistException(userName,officialName);
        }
        getUserByUserName(userName).setInformation(information);
    }


    /**
     * Remove User from the users list of the specific application.
     *
     * @param userName is the userName of the user to be removed
     * @param password is the password for the specific user.
     */
    public void removeUser(String userName, String password) throws UserNameDoesNotExistException, UserPasswordAuthenticationException, IllegalUserNameException, IllegalPasswordException {
        checkUserDetails(userName, password);
        users.remove(userName);
    }


    /**
     * Check if the userName and password is valid.
     *
     * @param userName is the userName of the user.
     * @param password is the password for the specific user.
     * @throws UserNameAlreadyExistException
     * @throws UserPasswordAuthenticationException
     */
    private void checkUserDetails(String userName, String password) throws UserNameDoesNotExistException, UserPasswordAuthenticationException, IllegalUserNameException, IllegalPasswordException {
        checkIllegalUserName(userName);
        checkIllegalPassword(password);

        if (!checkIfValidUserName(userName))
            throw new UserNameDoesNotExistException(userName, officialName);

        if (!checkIfValidPassword(getUserByUserName(userName).getPassword(), password))
            throw new UserPasswordAuthenticationException(userName, officialName);
    }

    public String getOfficialName() {
        return officialName;
    }


    /**
     * Check if the userName and password is valid.
     *
     * @param userPassword    is the password of the user.
     * @param passwordToCheck is the password checked if matched to the user password.
     */
    private boolean checkIfValidPassword(String userPassword, String passwordToCheck) {
        return userPassword.equals(passwordToCheck);
    }

    /**
     * Check if the userName is an existing user
     *
     * @param userName is the userName of the user to be checked.
     */
    private boolean checkIfValidUserName(String userName) throws IllegalUserNameException {
        return getUserByUserName(userName) != null;
    }


    /**
     * Change the password for specific user
     *
     * @param userName is the userName of the user that is modified.
     */
    public void modifyPasswordForUser(String userName, String oldPassword, String newPassword) throws UserNameDoesNotExistException, UserPasswordAuthenticationException, IllegalUserNameException, IllegalPasswordException {
        checkUserDetails(userName, oldPassword);
        checkIllegalPassword(newPassword);
        getUserByUserName(userName).changePassword(newPassword);
    }


    /**
     * Get user object by using userName as input
     *
     * @param userName is the userName of the user that is searched.
     */
    public User getUserByUserName(String userName) throws IllegalUserNameException {
        checkIllegalUserName(userName);
        return users.get(userName);
    }

    public Map<String, String> getUsersMap() {
        Map<String, String> usersMap = new HashMap<>();
        users.forEach((userName, user) -> usersMap.put(userName, user.getPassword()));
        return usersMap;
    }
}
