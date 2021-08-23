package tests;

import exceptions.*;
import model.ApplicationRecord;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;



public class ApplicationRecordTest {
    public static final String USERNAME = "Vadim";
    public static final String PASSWORD = "15672";

    private ApplicationRecord applicationRecord;

    @Before
    public void init() {
        applicationRecord = new ApplicationRecord("Bank Discount");
    }

    @Test
    public void setAndGetInformation() {
        assertNull(applicationRecord.getInformation());
        applicationRecord.setInformation("Bank application user");
        assertEquals("Bank application user", applicationRecord.getInformation());

    }


    @Test
    public void addUser() throws UserNameAlreadyExistException, IllegalUserNameException, IllegalPasswordException {
        applicationRecord.addUser(USERNAME, "Tqcv1234", null);
        assertEquals(1, applicationRecord.getNumberOfUsers());
        applicationRecord.addUser(USERNAME +"1", PASSWORD, null);
        assertEquals(2, applicationRecord.getNumberOfUsers());
        applicationRecord.addUser(USERNAME +"2", PASSWORD, null);
        assertEquals(3, applicationRecord.getNumberOfUsers());
    }
    @Test(expected = IllegalUserNameException.class)
    public void addUserIllegalUserNameTest() throws UserNameAlreadyExistException, IllegalUserNameException, IllegalPasswordException {
        applicationRecord.addUser(null, PASSWORD, null);

    }
    @Test(expected = IllegalPasswordException.class)
    public void addUserIllegalPasswordTest() throws UserNameAlreadyExistException, IllegalUserNameException, IllegalPasswordException {
        applicationRecord.addUser(USERNAME, null, null);

    }


    @Test(expected = UserNameAlreadyExistException.class)
    public void addUserExceptionThrown() throws UserNameAlreadyExistException, IllegalUserNameException, IllegalPasswordException {
        applicationRecord.addUser(USERNAME, PASSWORD, null);
        applicationRecord.addUser(USERNAME, PASSWORD, null);
    }

    @Test
    public void removeUser() throws UserNameAlreadyExistException, UserPasswordAuthenticationException, UserNameDoesNotExistException, IllegalUserNameException, IllegalPasswordException {
        applicationRecord.addUser(USERNAME, PASSWORD, null);
        applicationRecord.addUser(USERNAME +"1", PASSWORD, null);
        applicationRecord.addUser(USERNAME +"2", PASSWORD, null);

        applicationRecord.removeUser(USERNAME, PASSWORD);
        assertEquals(2, applicationRecord.getNumberOfUsers());


        applicationRecord.removeUser(USERNAME +"1", PASSWORD);
        assertEquals(1, applicationRecord.getNumberOfUsers());


        applicationRecord.removeUser(USERNAME +"2", PASSWORD);
        assertEquals(0, applicationRecord.getNumberOfUsers());

    }

    @Test(expected = UserNameDoesNotExistException.class)
    public void removeUserDoesNotExistException() throws UserNameAlreadyExistException, UserPasswordAuthenticationException, UserNameDoesNotExistException, IllegalUserNameException, IllegalPasswordException {
        applicationRecord.addUser(USERNAME, PASSWORD, null);
        applicationRecord.addUser(USERNAME +"1", PASSWORD, null);
        applicationRecord.addUser(USERNAME +"2", PASSWORD, null);

        applicationRecord.removeUser(USERNAME +"1111", PASSWORD);

    }

    @Test(expected = UserPasswordAuthenticationException.class)
    public void removeUserPasswordAuthenticationException() throws UserNameAlreadyExistException, UserPasswordAuthenticationException, UserNameDoesNotExistException, IllegalUserNameException, IllegalPasswordException {
        applicationRecord.addUser(USERNAME, PASSWORD, null);
        applicationRecord.addUser(USERNAME +"1", PASSWORD, null);
        applicationRecord.addUser(USERNAME +"2", PASSWORD,null);

        applicationRecord.removeUser(USERNAME, PASSWORD +"1111111");

    }


    @Test
    public void modifyPasswordForUser() throws UserNameAlreadyExistException, UserNameDoesNotExistException, UserPasswordAuthenticationException, IllegalUserNameException, IllegalPasswordException {
        applicationRecord.addUser(USERNAME, PASSWORD, null);
        applicationRecord.addUser(USERNAME +"1", PASSWORD, null);

        applicationRecord.modifyPasswordForUser(USERNAME, PASSWORD, "asddTqcv1234");
        applicationRecord.modifyPasswordForUser(USERNAME +"1", PASSWORD,"2221234");

        assertEquals("asddTqcv1234", applicationRecord.getUserByUserName(USERNAME).getPassword());
        assertEquals("2221234", applicationRecord.getUserByUserName(USERNAME +"1").getPassword());
    }

    @Test
    public void getUserByUserNameTest() throws IllegalUserNameException, IllegalPasswordException, UserNameAlreadyExistException, UserNameDoesNotExistException {
        applicationRecord.addUser(USERNAME, PASSWORD, null);
        assertNotNull(applicationRecord.getUserByUserName(USERNAME));
        assertEquals(USERNAME,applicationRecord.getUserByUserName(USERNAME).getUserName());
        assertEquals(PASSWORD,applicationRecord.getUserByUserName(USERNAME).getPassword());

    }

    @Test(expected = UserNameDoesNotExistException.class)
    public void getUserByUserNameTestException() throws IllegalUserNameException, UserNameDoesNotExistException {
        applicationRecord.getUserByUserName(USERNAME);
    }




}