package Tests;

import model.ApplicationRecord;
import model.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    User user;

    @Before
    public void init() {
       user  = new User("Vadim1690","Tqcv1234");
    }

    @Test
    public void getUserName() {
        assertEquals("Vadim1690",user.getUserName());
    }

    @Test
    public void getPassword() {
        assertEquals("Tqcv1234",user.getPassword());

    }

    @Test
    public void setInformation() {
        user.setInformation("Test check!");
        assertEquals("Test check!",user.getInformation());
    }

    @Test
    public void changePassword() {
        user.changePassword("Tqcv1690");
        assertEquals("Tqcv1690",user.getPassword());

    }
}