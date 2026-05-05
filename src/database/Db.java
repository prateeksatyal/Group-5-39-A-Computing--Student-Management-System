package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Db {

    // Open connection
    Connection openConnection() throws SQLException;

    // Close connection
    void closeConnection(Connection conn) throws SQLException;

    // Run SELECT query
    ResultSet runQuery(Connection conn, String query) throws SQLException;

    // Execute INSERT, UPDATE, DELETE
    int executeUpdate(Connection conn, String query) throws SQLException;
}