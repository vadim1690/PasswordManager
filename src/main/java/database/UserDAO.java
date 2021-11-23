package database;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDAO {
    private static PreparedStatement insertIntoUser;
    public final String TABLE_USER = "user";
    public final String COLUMN_USER_USERNAME = "username";
    public final String COLUMN_USER_PASSWORD = "password";
    public final String COLUMN_USER_INFORMATION = "information";
    public final String COLUMN_USER_LAST_MODIFIED = "last_modified";
    public final String COLUMN_USER_APPLICATION_NAME = "application_name";
    public final String WHERE_USER_APPLICATION_STATEMENT = " WHERE (" + COLUMN_USER_USERNAME + " = ? AND " + COLUMN_USER_APPLICATION_NAME + " = ?)";
    public final String UPDATE_PASSWORD = "UPDATE " + TABLE_USER + " SET " + COLUMN_USER_PASSWORD + " = ? " +
            WHERE_USER_APPLICATION_STATEMENT;
    public final String QUERY_USER = "SELECT * FROM " + TABLE_USER +
            WHERE_USER_APPLICATION_STATEMENT;
    public final String DELETE_USER = "DELETE FROM " + TABLE_USER +
            WHERE_USER_APPLICATION_STATEMENT;
    public final String QUERY_USERS_FOR_APPLICATION = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_APPLICATION_NAME + " = ";
    public final String INSERT_USER = "REPLACE INTO " + TABLE_USER +
            '(' + COLUMN_USER_USERNAME + ", " + COLUMN_USER_PASSWORD + "," + COLUMN_USER_INFORMATION + ", "
            + COLUMN_USER_LAST_MODIFIED + "," + COLUMN_USER_APPLICATION_NAME + ") VALUES(?,?,?,?,?)";
    private final String SQL_EXCEPTION_MSG = "Couldn't insert User!";

    public static void closeConnection() throws SQLException {
        if (insertIntoUser != null)
            insertIntoUser.close();

    }

    public void saveUser(User user, String applicationOfficialName) throws SQLException {
        insertIntoUser = Datasource.getInstance().getConnection().prepareStatement(INSERT_USER);
        insertIntoUser.setString(1, user.getUserName());
        insertIntoUser.setString(2, user.getPassword());
        insertIntoUser.setString(3, user.getInformation());
        insertIntoUser.setString(4, user.getPasswordLastModified());
        insertIntoUser.setString(5, applicationOfficialName);
        int affectedRows = insertIntoUser.executeUpdate();

        if (affectedRows != 1) {
            throw new SQLException(SQL_EXCEPTION_MSG);
        }

    }

    public List<User> readAllUsers(String applicationOfficialName) throws SQLException {
        String query = QUERY_USERS_FOR_APPLICATION + " '" + applicationOfficialName + "' ";
        List<User> users = new ArrayList<>();
        try (ResultSet resultSet = Datasource.getInstance().
                getConnection().
                createStatement().
                executeQuery(query)) {

            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getString(COLUMN_USER_USERNAME),
                        resultSet.getString(COLUMN_USER_PASSWORD),
                        resultSet.getString(COLUMN_USER_INFORMATION),
                        resultSet.getString(COLUMN_USER_LAST_MODIFIED)));
            }
        }
        return users;
    }

    public User readUser(String userName, String officialName) throws SQLException {
        try (PreparedStatement preparedQuery = Datasource.getInstance().getConnection().prepareStatement(QUERY_USER)) {
            preparedQuery.setString(1, userName);
            preparedQuery.setString(2, officialName);
            try (ResultSet resultSet = preparedQuery.executeQuery()) {
                if (resultSet.next())
                    return new User(
                            resultSet.getString(COLUMN_USER_USERNAME),
                            resultSet.getString(COLUMN_USER_PASSWORD),
                            resultSet.getString(COLUMN_USER_INFORMATION),
                            resultSet.getString(COLUMN_USER_LAST_MODIFIED));
            }
        }
        return null;
    }

    public void deleteUser(String userName, String officialName) throws SQLException {
        try (PreparedStatement preparedStatement = Datasource.getInstance().getConnection().prepareStatement(DELETE_USER)) {
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, officialName);
            preparedStatement.execute();

        }
    }

    public void updatePassword(String officialName, String userName, String newPassword) throws SQLException {
        try (PreparedStatement preparedStatement = Datasource.getInstance().getConnection().prepareStatement(UPDATE_PASSWORD)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, officialName);
            preparedStatement.execute();
        }

    }
}
