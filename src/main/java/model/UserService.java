package model;

import database.UserDAO;
import exceptions.UserNameAlreadyExistException;
import exceptions.UserPasswordAuthenticationException;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class UserService {

    private UserDAO userDAO;

    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }


    public  User saveUser(String username,String password,String information, String officialName) throws SQLException, UserNameAlreadyExistException {
        if (checkIfUserAlreadyExist(username, officialName))
            throw new UserNameAlreadyExistException(username, officialName);
        User user = new User(username,password,information);
        userDAO.saveUser(user, officialName);
        return user;
    }

    private  boolean checkIfUserAlreadyExist(String userName, String officialName) throws SQLException {
        if (userDAO.readUser(userName, officialName) != null)
            return true;
        return false;
    }

    public List<User> readAllUsersForApplication(String officialName) throws SQLException {
        return userDAO.readAllUsers(officialName);
    }

    public  void deleteUser(String userName, String officialName) throws SQLException {
        userDAO.deleteUser(userName, officialName);
    }

    public  void modifyPassword(String officialName, String userName, String oldPassword, String newPassword) throws UserPasswordAuthenticationException, SQLException {
        if (!passwordAuthentication(officialName, userName, oldPassword))
            throw new UserPasswordAuthenticationException(userName, officialName);


        userDAO.updatePassword(officialName,userName,newPassword);
    }

    private  boolean passwordAuthentication(String officialName, String userName, String oldPassword) throws SQLException {
        if (userDAO.readUser(userName, officialName).getPassword().equals(oldPassword))
            return true;

        else
            return false;
    }
}
