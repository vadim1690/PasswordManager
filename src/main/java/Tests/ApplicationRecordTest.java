package Tests;

import exceptions.*;
import model.ApplicationRecord;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ApplicationRecordTest {

    ApplicationRecord applicationRecord;

    @Before
    public void init() {
        applicationRecord = new ApplicationRecord("Bank Discount");
    }

    @Test
    public void setAndGetInformation() {
        ApplicationRecord applicationRecord = new ApplicationRecord("Bank Discount");
        assertNull(applicationRecord.getInformation());
        applicationRecord.setInformation("Bank application user");
        assertEquals("Bank application user", applicationRecord.getInformation());

    }


    @Test
    public void addUser() throws UserNameAlreadyExistException, IllegalUserNameException, IllegalPasswordException {
        applicationRecord.addUser("Vadim1690", "Tqcv1234", null);
        assertEquals(1, applicationRecord.getNumberOfUsers());
        applicationRecord.addUser("Vadim16901", "Tqcv1234", null);
        assertEquals(2, applicationRecord.getNumberOfUsers());
        applicationRecord.addUser("Vadim16902", "Tqcv1234", null);
        assertEquals(3, applicationRecord.getNumberOfUsers());
    }
    @Test(expected = IllegalUserNameException.class)
    public void addUserIllegalUserNameTest() throws UserNameAlreadyExistException, IllegalUserNameException, IllegalPasswordException {
        applicationRecord.addUser(null, "Tqcv1234", null);

    }
    @Test(expected = IllegalPasswordException.class)
    public void addUserIllegalPasswordTest() throws UserNameAlreadyExistException, IllegalUserNameException, IllegalPasswordException {
        applicationRecord.addUser("Vadim1690", null, null);

    }


    @Test(expected = UserNameAlreadyExistException.class)
    public void addUserExceptionThrown() throws UserNameAlreadyExistException, IllegalUserNameException, IllegalPasswordException {
        applicationRecord.addUser("Vadim1690", "Tqcv1234", null);
        applicationRecord.addUser("Vadim1690", "Tqcv1234", null);
    }

    @Test
    public void removeUser() throws UserNameAlreadyExistException, UserPasswordAuthenticationException, UserNameDoesNotExistException, IllegalUserNameException, IllegalPasswordException {
        applicationRecord.addUser("Vadim1690", "Tqcv1234", null);
        applicationRecord.addUser("Vadim16901", "Tqcv1234", null);
        applicationRecord.addUser("Vadim16902", "Tqcv1234", null);

        applicationRecord.removeUser("Vadim1690", "Tqcv1234");
        assertEquals(2, applicationRecord.getNumberOfUsers());
        assertNull(applicationRecord.getUserByUserName("vadim1690"));

        applicationRecord.removeUser("Vadim16901", "Tqcv1234");
        assertEquals(1, applicationRecord.getNumberOfUsers());
        assertNull(applicationRecord.getUserByUserName("vadim16901"));

        applicationRecord.removeUser("Vadim16902", "Tqcv1234");
        assertEquals(0, applicationRecord.getNumberOfUsers());
        assertNull(applicationRecord.getUserByUserName("vadim16902"));
    }

    @Test(expected = UserNameDoesNotExistException.class)
    public void removeUserDoesNotExistException() throws UserNameAlreadyExistException, UserPasswordAuthenticationException, UserNameDoesNotExistException, IllegalUserNameException, IllegalPasswordException {
        applicationRecord.addUser("Vadim1690", "Tqcv1234", null);
        applicationRecord.addUser("Vadim16901", "Tqcv1234", null);
        applicationRecord.addUser("Vadim16902", "Tqcv1234", null);

        applicationRecord.removeUser("Vadim169011", "Tqcv1234");

    }

    @Test(expected = UserPasswordAuthenticationException.class)
    public void removeUserPasswordAuthenticationException() throws UserNameAlreadyExistException, UserPasswordAuthenticationException, UserNameDoesNotExistException, IllegalUserNameException, IllegalPasswordException {
        applicationRecord.addUser("Vadim1690", "Tqcv1234", null);
        applicationRecord.addUser("Vadim16901", "Tqcv1234", null);
        applicationRecord.addUser("Vadim16902", "Tqcv1234", null);

        applicationRecord.removeUser("Vadim1690", "Tqcv12341111111");

    }


    @Test
    public void modifyPasswordForUser() throws UserNameAlreadyExistException, UserNameDoesNotExistException, UserPasswordAuthenticationException, IllegalUserNameException, IllegalPasswordException {
        applicationRecord.addUser("Vadim1690", "Tqcv1234", null);
        applicationRecord.addUser("Vadim16901", "Tqcv1234", null);

        applicationRecord.modifyPasswordForUser("Vadim1690","Tqcv1234", "asddTqcv1234");
        applicationRecord.modifyPasswordForUser("Vadim16901", "Tqcv1234","2221234");

        assertEquals("asddTqcv1234", applicationRecord.getUserByUserName("Vadim1690").getPassword());
        assertEquals("2221234", applicationRecord.getUserByUserName("Vadim16901").getPassword());
    }

}