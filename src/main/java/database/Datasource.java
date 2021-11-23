package database;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;

public class Datasource {


    private static Datasource instance;
    private final String DB_HOST = "localhost";
    private final String DB_PORT = "3306";
    private final String DB_NAME = "password_manager";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "tqcv1690";
    private final String CONNECTION_STRING = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
    private Connection conn;




    private Datasource() {

    }

    public static Datasource getInstance() {
        if (instance == null)
            instance = new Datasource();
        return instance;
    }

    public Connection getConnection() {
        return conn;
    }


    public void open() throws SQLException {
        conn = DriverManager.getConnection(CONNECTION_STRING, DB_USER, DB_PASSWORD);
    }

    public void close() throws SQLException {
        ApplicationRecordDAO.closeConnection();
        UserDAO.closeConnection();

        if (conn != null)
            conn.close();
    }

}
