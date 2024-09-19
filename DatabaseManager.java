package DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static Connection connection;
    private static final String DATABASE_URL = "jdbc:sqlite:db_lib.db";

    // Private constructor to prevent instantiation
    private DatabaseManager() {}

    // Method to get a shared instance of the database connection
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DATABASE_URL);
        }
        return connection;
    }
}
