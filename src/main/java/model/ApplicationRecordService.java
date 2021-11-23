package model;

import database.ApplicationRecordDAO;
import exceptions.ApplicationAlreadyExistException;


import java.sql.SQLException;
import java.util.Collection;
import java.util.List;


public class ApplicationRecordService {
    private ApplicationRecordDAO applicationRecordDAO;

    public ApplicationRecordService(ApplicationRecordDAO applicationRecordDAO) {
        this.applicationRecordDAO =applicationRecordDAO;
    }

    public void saveApplicationRecord(ApplicationRecord applicationRecord) throws SQLException, ApplicationAlreadyExistException {
        if (checkIfApplicationExist(applicationRecord.getOfficialName()))
            throw new ApplicationAlreadyExistException(applicationRecord.getOfficialName());
        applicationRecordDAO.saveApplicationRecord(applicationRecord);
    }

    public  boolean checkIfApplicationExist(String officialName) throws SQLException {
        if (applicationRecordDAO.readApplicationByOfficialName(officialName) != null) {
            return true;
        }
        return false;

    }

    public List<ApplicationRecord> readAllApplications() throws SQLException {
        return applicationRecordDAO.readAllApplications();
    }

    public  String readApplicationInformation(String officialName) throws  SQLException {
        return applicationRecordDAO.readApplicationByOfficialName(officialName).getInformation();
    }

    public  void deleteApplication(String officialName) throws SQLException {
        applicationRecordDAO.deleteApplication(officialName);
    }
}
