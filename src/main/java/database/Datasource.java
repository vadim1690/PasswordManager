package database;

import model.ApplicationRecord;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Datasource {

    private static final String TABLE_APPLICATION = "application";
    public static final String QUERY_APPLICATION_RECORDS = "SELECT * FROM " + TABLE_APPLICATION;
    private static final String COLUMN_APPLICATION_OFFICIAL_NAME = "official_name";
    private static final String COLUMN_APPLICATION_INFORMATION = "information";
    private static final String INSERT_APPLICATION = "REPLACE INTO " + TABLE_APPLICATION +
            '(' + COLUMN_APPLICATION_OFFICIAL_NAME + ", " + COLUMN_APPLICATION_INFORMATION +
            ") VALUES(?, ?)";
    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_USERNAME = "username";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_INFORMATION = "information";
    private static final String COLUMN_USER_LAST_MODIFIED = "last_modified";
    private static final String COLUMN_USER_APPLICATION_NAME = "application_name";
    public static final String QUERY_USERS_FOR_APPLICATION = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_APPLICATION_NAME + " = ";
    private static final String INSERT_USER = "REPLACE INTO " + TABLE_USER +
            '(' + COLUMN_USER_USERNAME + ", " + COLUMN_USER_PASSWORD + "," + COLUMN_USER_INFORMATION + ", "
            + COLUMN_USER_LAST_MODIFIED + "," + COLUMN_USER_APPLICATION_NAME + ") VALUES(?,?,?,?,?)";
    private static Datasource instance;
    private final String DB_HOST = "localhost";
    private final String DB_PORT = "3306";
    private final String DB_NAME = "password_manager";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "tqcv1690";
    private final String CONNECTION_STRING = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
    private Connection conn;
    private PreparedStatement insertIntoApplication;
    private PreparedStatement insertIntoUser;


    private Datasource() {

    }

    public static Datasource getInstance() {
        if (instance == null)
            instance = new Datasource();
        return instance;
    }


    public void open() throws SQLException {
        conn = DriverManager.getConnection(CONNECTION_STRING, DB_USER, DB_PASSWORD);
        insertIntoApplication = conn.prepareStatement(INSERT_APPLICATION);
        insertIntoUser = conn.prepareStatement(INSERT_USER);

    }

    public void close() throws SQLException {
        if (insertIntoApplication != null)
            insertIntoApplication.close();

        if (insertIntoUser != null)
            insertIntoUser.close();

        if (conn != null)
            conn.close();
    }

    public void saveApplicationRecord(ApplicationRecord applicationRecord) throws SQLException {
        conn.setAutoCommit(false);

        insertIntoApplication.setString(1, applicationRecord.getOfficialName());
        insertIntoApplication.setString(2, applicationRecord.getInformation());

        insertIntoApplication.executeUpdate();
        saveUsers(applicationRecord);
        conn.commit();


        conn.setAutoCommit(true);
    }

    private void saveUsers(ApplicationRecord applicationRecord) throws SQLException {
        for (User user : applicationRecord.getUsersList()) {
            insertIntoUser.setString(1, user.getUserName());
            insertIntoUser.setString(2, user.getPassword());
            insertIntoUser.setString(3, user.getInformation());
            insertIntoUser.setString(4, user.getPasswordLastModified());
            insertIntoUser.setString(5, applicationRecord.getOfficialName());
            int affectedRows = insertIntoUser.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Couldn't insert User!");
            }
        }
    }

    public List<ApplicationRecord> readApplicationRecords() throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(QUERY_APPLICATION_RECORDS);
        List<ApplicationRecord> applicationRecords = new ArrayList<>();
        while (resultSet.next()) {
            ApplicationRecord applicationRecord = new ApplicationRecord(resultSet.getString(COLUMN_APPLICATION_OFFICIAL_NAME));
            applicationRecord.setInformation(resultSet.getString(COLUMN_APPLICATION_INFORMATION));
            readAllUsersForApplication(applicationRecord);
            applicationRecords.add(applicationRecord);
        }
        return applicationRecords;
    }

    private void readAllUsersForApplication(ApplicationRecord applicationRecord) throws SQLException {
        Statement statement = conn.createStatement();
        String query = QUERY_USERS_FOR_APPLICATION + " '" + applicationRecord.getOfficialName() + "' ";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            try {
                applicationRecord.addUser(resultSet.getString(COLUMN_USER_USERNAME), resultSet.getString(COLUMN_USER_PASSWORD), resultSet.getString(COLUMN_USER_INFORMATION));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
