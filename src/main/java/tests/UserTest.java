package tests;

import model.PasswordStrength;
import model.User;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void getPasswordStrength() {

        String weakPassword = "WeakPassword";
        String mediumPassword = "MediumPassword1234";
        String strongPassword = "StrongPassword1234!@#";

        User user1 = new User("Username1",weakPassword);
        Assert.assertEquals(PasswordStrength.WEAK,user1.getPasswordStrength());

        User user2 = new User("Username2",mediumPassword);
        assertEquals(PasswordStrength.MEDIUM,user2.getPasswordStrength());

        User user3 = new User("Username3",strongPassword);
        assertEquals(PasswordStrength.STRONG,user3.getPasswordStrength());

    }
}