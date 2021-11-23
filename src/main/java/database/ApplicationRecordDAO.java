package database;

import model.ApplicationRecord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ApplicationRecordDAO {

    public final String TABLE_APPLICATION = "application";
    public final String QUERY_APPLICATION_RECORDS = "SELECT * FROM " + TABLE_APPLICATION;
    public final String COLUMN_APPLICATION_OFFICIAL_NAME = "official_name";
    public final String COLUMN_APPLICATION_INFORMATION = "information";
    public final String INSERT_APPLICATION = "REPLACE INTO " + TABLE_APPLICATION +
            '(' + COLUMN_APPLICATION_OFFICIAL_NAME + ", " + COLUMN_APPLICATION_INFORMATION +
            ") VALUES(?, ?)";
    public final String QUERY_APPLICATION_BY_NAME = "SELECT * FROM " + TABLE_APPLICATION + " WHERE " + COLUMN_APPLICATION_OFFICIAL_NAME + " = ";
    public final String DELETE_APPLICATION = "DELETE FROM " + TABLE_APPLICATION + " WHERE " + COLUMN_APPLICATION_OFFICIAL_NAME + " =  ";

    private final String SQL_EXCEPTION_MSG = "Failed to insert Application Record!";
    private static PreparedStatement insertIntoApplication;

    public void saveApplicationRecord(ApplicationRecord applicationRecord) throws SQLException {
        insertIntoApplication = Datasource.getInstance().getConnection().prepareStatement(INSERT_APPLICATION);
        insertIntoApplication.setString(1, applicationRecord.getOfficialName());
        insertIntoApplication.setString(2, applicationRecord.getInformation());
        int affectedRows = insertIntoApplication.executeUpdate();

        if (affectedRows != 1) {
            throw new SQLException(SQL_EXCEPTION_MSG);
        }
    }

    public  List<ApplicationRecord> readAllApplications() throws SQLException {
        List<ApplicationRecord> applicationRecords = new ArrayList<>();
        try (ResultSet resultSet = Datasource.getInstance().
                getConnection().
                createStatement().
                executeQuery(QUERY_APPLICATION_RECORDS)) {


            while (resultSet.next()) {
                applicationRecords.add(new ApplicationRecord(resultSet.getString(COLUMN_APPLICATION_OFFICIAL_NAME),
                        resultSet.getString(COLUMN_APPLICATION_INFORMATION)));
            }
        }
        return applicationRecords;
    }

    public  ApplicationRecord readApplicationByOfficialName(String officialName) throws SQLException {
        String query = QUERY_APPLICATION_BY_NAME + "'" + officialName + "'";
        try (ResultSet resultSet = Datasource.getInstance().
                getConnection().
                createStatement().
                executeQuery(query)) {
            if (resultSet.next())
                return new ApplicationRecord(resultSet.getString(COLUMN_APPLICATION_OFFICIAL_NAME), resultSet.getString(COLUMN_APPLICATION_INFORMATION));
        }
        return null;
    }


    public static void closeConnection() throws SQLException {
        if (insertIntoApplication != null)
            insertIntoApplication.close();
    }

    public void deleteApplication(String officialName) throws SQLException {
        try (Statement statement = Datasource.getInstance().getConnection().createStatement()) {
            statement.execute(DELETE_APPLICATION + " '" + officialName + "'");
        }
    }
}
