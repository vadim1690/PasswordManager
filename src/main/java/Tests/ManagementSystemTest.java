package Tests;

import exceptions.*;
import model.ManagementSystem;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ManagementSystemTest {
   private  ManagementSystem managementSystem;

    @Before
    public  void init() throws ApplicationAlreadyExistException, IllegalApplicationNameException, ApplicationDoesNotExistException {
        managementSystem = ManagementSystem.getInstance();
        managementSystem.addApplication("Google", "My Google account details");
        managementSystem.addApplication("Bank Discount", "My bank details");


    }


    @Test
    public void addApplication() throws ApplicationAlreadyExistException, IllegalApplicationNameException, ApplicationDoesNotExistException {
        assertEquals(2, managementSystem.getAllApplications().size());
        managementSystem.addApplication("Leumit", "My Leumit health care details");
        assertEquals(3, managementSystem.getAllApplications().size());
    }

    @Test(expected = ApplicationDoesNotExistException.class)
    public void checkIfValidApplicationTest() throws  ApplicationDoesNotExistException, IllegalApplicationNameException {
        managementSystem.checkIfApplicationNameIsValid("Leumit");
    }

    @Test(expected = IllegalApplicationNameException.class)
    public void checkIfValidApplicationNullArgumentTest() throws  ApplicationDoesNotExistException, IllegalApplicationNameException {
        managementSystem.checkIfApplicationNameIsValid(null);
    }

    @Test(expected = ApplicationAlreadyExistException.class)
    public void addApplicationExceptionTest() throws ApplicationAlreadyExistException, IllegalApplicationNameException, ApplicationDoesNotExistException {
        managementSystem.addApplication("Google", "My Google account details");
    }

    @Test
    public void addUserToApplication() throws  ApplicationDoesNotExistException, UserNameAlreadyExistException, IllegalUserNameException, IllegalPasswordException, IllegalApplicationNameException {
        managementSystem.addUserToApplication("Google", "Vadim1690", "Tqcv1234", null);

        managementSystem.addUserToApplication("Google", "VadimBadim", "Tqcv1234", null);

        Map<String, String> map = managementSystem.getUserNameAndPasswordForApplication("Google");
        assertTrue(map.containsKey("Vadim1690"));
        assertEquals("Tqcv1234", map.get("Vadim1690"));

        assertTrue(map.containsKey("VadimBadim"));
        assertEquals("Tqcv1234", map.get("Vadim1690"));
    }

    @Test(expected = UserNameAlreadyExistException.class)
    public void addUserToApplicationUserNameExistExceptionTest() throws  ApplicationDoesNotExistException, UserNameAlreadyExistException, IllegalUserNameException, IllegalPasswordException, IllegalApplicationNameException {
        managementSystem.addUserToApplication("Google", "Vadim1690", "Tqcv1234", null);
        managementSystem.addUserToApplication("Google", "Vadim1690", "11224032", "Test");
    }

    @Test
    public void removeApplication() throws  ApplicationDoesNotExistException, IllegalApplicationNameException {
        managementSystem.removeApplication("Google");
        assertEquals(1, managementSystem.getAllApplications().size());
    }


    @Test
    public void removeUserForApplication() throws  ApplicationDoesNotExistException, UserNameAlreadyExistException, UserNameDoesNotExistException, UserPasswordAuthenticationException, IllegalUserNameException, IllegalPasswordException, IllegalApplicationNameException {
        managementSystem.addUserToApplication("Google", "Vadim1690", "Tqcv1234", null);
        assertEquals(1, managementSystem.getUserNameAndPasswordForApplication("Google").size());
        managementSystem.removeUserForApplication("Google", "Vadim1690", "Tqcv1234");
        assertEquals(0, managementSystem.getUserNameAndPasswordForApplication("Google").size());

    }

    @Test(expected = UserNameDoesNotExistException.class)
    public void removeUserForApplicationUserNameDoesNotExistExceptionTest() throws  ApplicationDoesNotExistException, UserNameAlreadyExistException, UserNameDoesNotExistException, UserPasswordAuthenticationException, IllegalUserNameException, IllegalPasswordException, IllegalApplicationNameException {
        managementSystem.addUserToApplication("Google", "Vadim1690", "Tqcv1234", null);
        managementSystem.removeUserForApplication("Google", "VadimBadim", "Tqcv1234");
    }

    @Test(expected = UserPasswordAuthenticationException.class)
    public void removeUserForApplicationUserPasswordAuthenticationExceptionTest() throws  ApplicationDoesNotExistException, UserNameAlreadyExistException, UserNameDoesNotExistException, UserPasswordAuthenticationException, IllegalUserNameException, IllegalPasswordException, IllegalApplicationNameException {
        managementSystem.addUserToApplication("Google", "Vadim1690", "Tqcv1234", null);
        managementSystem.removeUserForApplication("Google", "Vadim1690", "Tqcv123455555");

    }


    @Test
    public void editApplicationInformation() throws ApplicationDoesNotExistException, IllegalApplicationNameException {
        managementSystem.editApplicationInformation("Google", "Google Test information");
        assertEquals("Google Test information",
                managementSystem.
                        getApplicationRecordByOfficialName("Google").
                        getInformation());
    }

    @Test
    public void editUserInformation() throws ApplicationDoesNotExistException, UserNameDoesNotExistException, UserNameAlreadyExistException, IllegalUserNameException, IllegalPasswordException, IllegalApplicationNameException {
       managementSystem.addUserToApplication("Google","Vadim1690","Tqcv1234",null);
        managementSystem.editUserInformation("Google","Vadim1690","User information Test");
        assertEquals("User information Test",
                managementSystem.
                        getApplicationRecordByOfficialName("Google").
                        getUserByUserName("Vadim1690").
                        getInformation());
    }

    @Test(expected = UserNameDoesNotExistException.class)
    public void editUserInformationUserNameDoesNotExistExceptionTest() throws ApplicationDoesNotExistException, UserNameDoesNotExistException, UserNameAlreadyExistException, IllegalUserNameException, IllegalPasswordException, IllegalApplicationNameException {
        managementSystem.addUserToApplication("Google","Vadim1690","Tqcv1234",null);
        managementSystem.editUserInformation("Google","Vadim1690123124","User information Test");

    }

    @Test
    public void modifyPassword() throws ApplicationDoesNotExistException, UserNameDoesNotExistException, UserPasswordAuthenticationException, UserNameAlreadyExistException, IllegalUserNameException, IllegalPasswordException, IllegalApplicationNameException {
        managementSystem.addUserToApplication("Google","Vadim1690","Tqcv1234",null);
        managementSystem.modifyPassword("Google","Vadim1690","Tqcv1234","Tqcv1690");
        assertEquals("Tqcv1690",
                managementSystem.
                        getApplicationRecordByOfficialName("Google").
                        getUserByUserName("Vadim1690").
                        getPassword());
    }

    @Test
    public void getAllApplicationForUserName() {
    }

    @Test
    public void getUserNameAndPasswordForApplication() {
    }
}