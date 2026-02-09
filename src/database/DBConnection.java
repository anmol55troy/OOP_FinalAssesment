package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages database connection
 */
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/CompetitionDB";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Change this to your MySQL password
    private static Connection connection = null;

    /**
     * Gets a database connection (singleton pattern)
     * @return Connection object
     */
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully!");
            } catch (ClassNotFoundException e) {
                System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("Connection failed: " + e.getMessage());
            }
        }
        return connection;
    }

    /**
     * Closes the database connection
     */
    public static void   closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
