package database;



import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * Creates database tables
 */
public class DBCreate {
    
    public static void createTables() {
        Connection conn = DBConnection.getConnection();
        
        if (conn == null) {
            System.err.println("Cannot create tables - no database connection");
            return;
        }

        try (Statement stmt = conn.createStatement()) {
            
            // Create Competitors table
            String createCompetitorsTable = "CREATE TABLE IF NOT EXISTS Competitors (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "first_name VARCHAR(50) NOT NULL, " +
                    "last_name VARCHAR(50) NOT NULL, " +
                    "level VARCHAR(20) NOT NULL, " +
                    "country VARCHAR(50) NOT NULL, " +
                    "score1 INT DEFAULT 0, " +
                    "score2 INT DEFAULT 0, " +
                    "score3 INT DEFAULT 0, " +
                    "score4 INT DEFAULT 0, " +
                    "score5 INT DEFAULT 0" +
                    ")";
            
            stmt.executeUpdate(createCompetitorsTable);
            System.out.println("Competitors table created successfully!");

            // Create Questions table
            String createQuestionsTable = "CREATE TABLE IF NOT EXISTS Questions (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "question_text TEXT NOT NULL, " +
                    "option_a VARCHAR(255) NOT NULL, " +
                    "option_b VARCHAR(255) NOT NULL, " +
                    "option_c VARCHAR(255) NOT NULL, " +
                    "option_d VARCHAR(255) NOT NULL, " +
                    "correct_answer VARCHAR(1) NOT NULL, " +
                    "level VARCHAR(20) NOT NULL" +
                    ")";
            
            stmt.executeUpdate(createQuestionsTable);
            System.out.println("Questions table created successfully!");

        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        createTables();
        DBConnection.closeConnection();
    }
}
