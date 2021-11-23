package tests;

import database.ApplicationRecordDAO;
import database.UserDAO;
import exceptions.ApplicationAlreadyExistException;
import model.ApplicationRecord;
import model.ApplicationRecordService;
import model.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ApplicationRecordServiceTest {


    ApplicationRecordDAO applicationRecordDAO;
    ApplicationRecordService applicationRecordService;


    @Before
    public void init() {
        applicationRecordDAO = Mockito.mock(ApplicationRecordDAO.class);
        applicationRecordService = new ApplicationRecordService(applicationRecordDAO);
    }

    @Test(expected = ApplicationAlreadyExistException.class)
    public void saveApplicationRecord() throws SQLException, ApplicationAlreadyExistException {
        ApplicationRecord applicationRecord = new ApplicationRecord("My App");
        Mockito.when(applicationRecordDAO.readApplicationByOfficialName("My App")).thenReturn(applicationRecord);

        applicationRecordService.saveApplicationRecord(new ApplicationRecord("My App"));

    }

    @Test
    public void readAllApplications() throws SQLException {
        ApplicationRecord applicationRecord1 = new ApplicationRecord("My App1");
        ApplicationRecord applicationRecord2 = new ApplicationRecord("My App2");
        ApplicationRecord applicationRecord3 = new ApplicationRecord("My App3");

        Mockito.when(applicationRecordDAO.readAllApplications()).thenReturn(Arrays.asList(applicationRecord1,applicationRecord2,applicationRecord3));
        List<ApplicationRecord> applications = applicationRecordService.readAllApplications();
        assertEquals(3,applications.size());
        assertEquals(applicationRecord1,applications.get(0));
        assertEquals(applicationRecord2,applications.get(1));
        assertEquals(applicationRecord3,applications.get(2));

    }

    @Test
    public void readApplicationInformation() throws SQLException {
        ApplicationRecord applicationRecord1 = new ApplicationRecord("My App1");
        applicationRecord1.setInformation("Information 1");
        Mockito.when(applicationRecordDAO.readApplicationByOfficialName("My App1")).thenReturn(applicationRecord1);

        String information = applicationRecordService.readApplicationInformation("My App1");
        assertEquals(applicationRecord1.getInformation(),information);

    }


}