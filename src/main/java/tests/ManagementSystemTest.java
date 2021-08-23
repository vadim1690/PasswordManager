package tests;

import exceptions.*;
import model.ManagementSystem;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ManagementSystemTest {
   private  ManagementSystem managementSystem;
    public static final String FIRST_USERNAME = "Vadim";
    public static final String FIRST_PASSWORD = "1122";
    public static final String SECOND_USERNAME = "Badim";
    public static final String SECOND_PASSWORD = "3344";
    public static final String GOOGLE_APPLICATION = "Google";
    public static final String BANK_APPLICATION = "Bank Discount";
    public static final String LEUMIT_APPLICATION = "LEUMIT";


    @Before
    public  void init() throws ApplicationAlreadyExistException, IllegalApplicationNameException, ApplicationDoesNotExistException {
        managementSystem = ManagementSystem.getInstance();
        managementSystem.getApplicationRecords().clear();
        managementSystem.addApplication(GOOGLE_APPLICATION, "My Google account details");
        managementSystem.addApplication(BANK_APPLICATION, "My bank details");


    }


    @Test
    public void addApplication() throws ApplicationAlreadyExistException, IllegalApplicationNameException, ApplicationDoesNotExistException {
        assertEquals(2, managementSystem.getAllApplications().size());
        managementSystem.addApplication(LEUMIT_APPLICATION, "My Leumit health care details");
        assertEquals(3, managementSystem.getAllApplications().size());
    }

    @Test(expected = ApplicationDoesNotExistException.class)
    public void checkIfValidApplicationTest() throws  ApplicationDoesNotExistException, IllegalApplicationNameException {
        managementSystem.checkIfApplicationNameIsValid(LEUMIT_APPLICATION);
    }

    @Test(expected = IllegalApplicationNameException.class)
    public void checkIfValidApplicationNullArgumentTest() throws  ApplicationDoesNotExistException, IllegalApplicationNameException {
        managementSystem.checkIfApplicationNameIsValid(null);
    }

    @Test(expected = ApplicationAlreadyExistException.class)
    public void addApplicationExceptionTest() throws ApplicationAlreadyExistException, IllegalApplicationNameException, ApplicationDoesNotExistException {
        managementSystem.addApplication(GOOGLE_APPLICATION, "My Google account details");
    }

    @Test
    public void addUserToApplication() throws  ApplicationDoesNotExistException, UserNameAlreadyExistException, IllegalUserNameException, IllegalPasswordException, IllegalApplicationNameException {
        managementSystem.addUserToApplication(GOOGLE_APPLICATION, FIRST_USERNAME, FIRST_PASSWORD, null);

        managementSystem.addUserToApplication(GOOGLE_APPLICATION, SECOND_USERNAME, SECOND_PASSWORD, null);

        Map<String, String> map = managementSystem.getUserNameAndPasswordForApplication(GOOGLE_APPLICATION);
        assertTrue(map.containsKey(FIRST_USERNAME));
        assertEquals(FIRST_PASSWORD, map.get(FIRST_USERNAME));

        assertTrue(map.containsKey(SECOND_USERNAME));
        assertEquals(SECOND_PASSWORD, map.get(SECOND_USERNAME));
    }

    @Test(expected = UserNameAlreadyExistException.class)
    public void addUserToApplicationUserNameExistExceptionTest() throws  ApplicationDoesNotExistException, UserNameAlreadyExistException, IllegalUserNameException, IllegalPasswordException, IllegalApplicationNameException {
        managementSystem.addUserToApplication(GOOGLE_APPLICATION, FIRST_USERNAME, FIRST_PASSWORD, null);
        managementSystem.addUserToApplication(GOOGLE_APPLICATION, FIRST_USERNAME, FIRST_PASSWORD, "Test");
    }

    @Test
    public void removeApplication() throws  ApplicationDoesNotExistException, IllegalApplicationNameException {
        managementSystem.removeApplication(GOOGLE_APPLICATION);
        assertEquals(1, managementSystem.getAllApplications().size());
    }


    @Test
    public void removeUserForApplication() throws  ApplicationDoesNotExistException, UserNameAlreadyExistException, UserNameDoesNotExistException, UserPasswordAuthenticationException, IllegalUserNameException, IllegalPasswordException, IllegalApplicationNameException {
        managementSystem.addUserToApplication(GOOGLE_APPLICATION, FIRST_USERNAME, FIRST_PASSWORD, null);
        assertEquals(1, managementSystem.getUserNameAndPasswordForApplication(GOOGLE_APPLICATION).size());
        managementSystem.removeUserForApplication(GOOGLE_APPLICATION, FIRST_USERNAME, FIRST_PASSWORD);
        assertEquals(0, managementSystem.getUserNameAndPasswordForApplication(GOOGLE_APPLICATION).size());

    }

    @Test(expected = UserNameDoesNotExistException.class)
    public void removeUserForApplicationUserNameDoesNotExistExceptionTest() throws  ApplicationDoesNotExistException, UserNameAlreadyExistException, UserNameDoesNotExistException, UserPasswordAuthenticationException, IllegalUserNameException, IllegalPasswordException, IllegalApplicationNameException {
        managementSystem.addUserToApplication(GOOGLE_APPLICATION, FIRST_USERNAME, FIRST_PASSWORD, null);
        managementSystem.removeUserForApplication(GOOGLE_APPLICATION, SECOND_USERNAME, SECOND_PASSWORD);
    }

    @Test(expected = UserPasswordAuthenticationException.class)
    public void removeUserForApplicationUserPasswordAuthenticationExceptionTest() throws  ApplicationDoesNotExistException, UserNameAlreadyExistException, UserNameDoesNotExistException, UserPasswordAuthenticationException, IllegalUserNameException, IllegalPasswordException, IllegalApplicationNameException {
        managementSystem.addUserToApplication(GOOGLE_APPLICATION, FIRST_USERNAME, FIRST_PASSWORD, null);
        managementSystem.removeUserForApplication(GOOGLE_APPLICATION, FIRST_USERNAME, FIRST_PASSWORD+"55555");

    }


    @Test
    public void editApplicationInformation() throws ApplicationDoesNotExistException, IllegalApplicationNameException {
        managementSystem.editApplicationInformation(GOOGLE_APPLICATION, "Google Test information");
        assertEquals("Google Test information",
                managementSystem.
                        getApplicationRecordByOfficialName(GOOGLE_APPLICATION).
                        getInformation());
    }

    @Test
    public void editUserInformation() throws ApplicationDoesNotExistException, UserNameDoesNotExistException, UserNameAlreadyExistException, IllegalUserNameException, IllegalPasswordException, IllegalApplicationNameException {
       managementSystem.addUserToApplication(GOOGLE_APPLICATION,FIRST_USERNAME,FIRST_PASSWORD,null);
        managementSystem.editUserInformation(GOOGLE_APPLICATION,FIRST_USERNAME,"User information Test");
        assertEquals("User information Test",
                managementSystem.
                        getApplicationRecordByOfficialName(GOOGLE_APPLICATION).
                        getUserByUserName(FIRST_USERNAME).
                        getInformation());
    }

    @Test(expected = UserNameDoesNotExistException.class)
    public void editUserInformationUserNameDoesNotExistExceptionTest() throws ApplicationDoesNotExistException, UserNameDoesNotExistException, UserNameAlreadyExistException, IllegalUserNameException, IllegalPasswordException, IllegalApplicationNameException {
        managementSystem.addUserToApplication(GOOGLE_APPLICATION,FIRST_USERNAME,FIRST_PASSWORD,null);
        managementSystem.editUserInformation(GOOGLE_APPLICATION,FIRST_USERNAME+"123124","User information Test");

    }

    @Test
    public void modifyPassword() throws ApplicationDoesNotExistException, UserNameDoesNotExistException, UserPasswordAuthenticationException, UserNameAlreadyExistException, IllegalUserNameException, IllegalPasswordException, IllegalApplicationNameException {
        managementSystem.addUserToApplication(GOOGLE_APPLICATION,FIRST_USERNAME,FIRST_PASSWORD,null);
        managementSystem.modifyPassword(GOOGLE_APPLICATION,FIRST_USERNAME,FIRST_PASSWORD,SECOND_PASSWORD);
        assertEquals(SECOND_PASSWORD,
                managementSystem.
                        getApplicationRecordByOfficialName(GOOGLE_APPLICATION).
                        getUserByUserName(FIRST_USERNAME).
                        getPassword());
    }

    @Test
    public void getAllApplicationsForUserName() throws IllegalUserNameException, IllegalPasswordException, ApplicationDoesNotExistException, UserNameAlreadyExistException, IllegalApplicationNameException, UserNameDoesNotExistException {
    managementSystem.addUserToApplication(GOOGLE_APPLICATION,FIRST_USERNAME,FIRST_PASSWORD,null);
    managementSystem.addUserToApplication(BANK_APPLICATION,FIRST_USERNAME,FIRST_PASSWORD,null);
    List<String> names =  managementSystem.getAllApplicationsForUserName(FIRST_USERNAME);
    assertEquals(2,names.size());
    assertTrue(names.contains(GOOGLE_APPLICATION));
    assertTrue(names.contains(BANK_APPLICATION));

    }

    @Test(expected = UserNameDoesNotExistException.class)
    public void getAllApplicationsForUserNameExceptionTest() throws IllegalUserNameException, IllegalPasswordException, ApplicationDoesNotExistException, UserNameAlreadyExistException, IllegalApplicationNameException, UserNameDoesNotExistException {
        managementSystem.addUserToApplication(GOOGLE_APPLICATION,FIRST_USERNAME,FIRST_PASSWORD,null);
        managementSystem.addUserToApplication(BANK_APPLICATION,FIRST_USERNAME,FIRST_PASSWORD,null);
        managementSystem.getAllApplicationsForUserName("Chambalolo");


    }

    @Test
    public void getUserNameAndPasswordForApplication() throws IllegalUserNameException, IllegalPasswordException, ApplicationDoesNotExistException, UserNameAlreadyExistException, IllegalApplicationNameException {
        managementSystem.addUserToApplication(GOOGLE_APPLICATION,FIRST_USERNAME,FIRST_PASSWORD,null);
        managementSystem.addUserToApplication(GOOGLE_APPLICATION,SECOND_USERNAME,SECOND_PASSWORD,null);
        managementSystem.addUserToApplication(GOOGLE_APPLICATION,"TqcVadim","Vadim1234",null);

        Map<String,String> users = managementSystem.getUserNameAndPasswordForApplication(GOOGLE_APPLICATION);
        assertEquals(3,users.size());
        assertTrue(users.containsKey(FIRST_USERNAME));
        assertTrue(users.containsKey(SECOND_USERNAME));
        assertTrue(users.containsKey("TqcVadim"));

        assertEquals(FIRST_PASSWORD,users.get(FIRST_USERNAME));
        assertEquals(SECOND_PASSWORD,users.get(SECOND_USERNAME));
        assertEquals("Vadim1234",users.get("TqcVadim"));


    }

}