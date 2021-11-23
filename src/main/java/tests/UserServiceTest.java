package tests;

import database.UserDAO;
import exceptions.UserNameAlreadyExistException;
import exceptions.UserPasswordAuthenticationException;
import model.User;
import model.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserServiceTest {


    UserDAO userDAO;
    UserService userService;


    @Before
    public void init() {
        userDAO = Mockito.mock(UserDAO.class);
        userService = new UserService(userDAO);
    }


    @Test(expected = UserNameAlreadyExistException.class)
    public void saveUserException() throws UserNameAlreadyExistException, SQLException {
        Mockito.when(userDAO.readUser("MyUserName1", "My App")).thenReturn(new User("MyUserName1", "MyPassword1", "MyInformation1"));
        userService.saveUser("MyUserName1", "MyPassword1", "MyInformation1", "My App");

    }

    @Test
    public void saveUser() throws UserNameAlreadyExistException, SQLException {
        Mockito.when(userDAO.readUser("MyUserName1", "My App")).thenReturn(null);
        userService.saveUser("MyUserName1", "MyPassword1", "MyInformation1", "My App");
        Mockito.verify(userDAO).readUser("MyUserName1", "My App");//verifying that readUser gets called
    }


    @Test
    public void readAllUsersForApplication() throws SQLException {
        User user1 = new User("MyUserName1", "MyPassword1", "MyInformation1");
        User user2 = new User("MyUserName2", "MyPassword2", "MyInformation2");
        List<User> users = Arrays.asList(user1, user2);
        Mockito.when(userDAO.readAllUsers("My App")).thenReturn(users);
        List<User> actualUsers = userService.readAllUsersForApplication("My App");


        assertEquals("MyUserName1", actualUsers.get(0).getUserName());
        assertEquals("MyPassword1", actualUsers.get(0).getPassword());
        assertEquals("MyInformation1", actualUsers.get(0).getInformation());

        assertEquals("MyUserName2", actualUsers.get(1).getUserName());
        assertEquals("MyPassword2", actualUsers.get(1).getPassword());
        assertEquals("MyInformation2", actualUsers.get(1).getInformation());

    }

    @Test(expected =UserPasswordAuthenticationException.class )
    public void modifyPassword() throws SQLException, UserPasswordAuthenticationException {
        User user1 = new User("MyUserName1", "MyPassword1", "MyInformation1");
        User user2 = new User("MyUserName2", "MyPassword2", "MyInformation2");
        Mockito.when(userDAO.readUser("MyUserName1","My App")).thenReturn(user1);

        userService.modifyPassword("My App","MyUserName1","Wrong Password","New Password");
    }
}