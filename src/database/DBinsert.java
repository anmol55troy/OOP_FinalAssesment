package database;

import backend.Question;
import java.sql.*;

/**
 * Inserts data into database
 */
public class DBinsert {

    /**
     * Inserts a question into the database
     * @param question The question to insert
     * @return true if successful, false otherwise
     */
    public static boolean insertQuestion(Question question) {
        Connection conn = DBConnection.getConnection();
        
        String query = "INSERT INTO Questions (question_text, option_a, option_b, option_c, option_d, correct_answer, level) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, question.getQuestionText());
            pstmt.setString(2, question.getOptionA());
            pstmt.setString(3, question.getOptionB());
            pstmt.setString(4, question.getOptionC());
            pstmt.setString(5, question.getOptionD());
            pstmt.setString(6, question.getCorrectAnswer());
            pstmt.setString(7, question.getLevel().toString());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error inserting question: " + e.getMessage());
            return false;
        }
    }

    /**
     * Bulk insert questions from CSV data
     * @param csvData Array of CSV lines
     */
    public static void insertQuestionsFromCSV(String[] csvData) {
        for (String line : csvData) {
            // Skip header or empty lines
            if (line.trim().isEmpty() || line.startsWith("question_text")) {
                continue;
            }

            String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Handle commas in quotes
            
            if (parts.length >= 7) {
                Question q = new Question();
                q.setQuestionText(parts[0].replace("\"", ""));
                q.setOptionA(parts[1].replace("\"", ""));
                q.setOptionB(parts[2].replace("\"", ""));
                q.setOptionC(parts[3].replace("\"", ""));
                q.setOptionD(parts[4].replace("\"", ""));
                q.setCorrectAnswer(parts[5].trim().toUpperCase());
                q.setLevel(backend.Level.fromString(parts[6].trim()));

                insertQuestion(q);
            }
        }
        System.out.println("Questions inserted from CSV successfully!");
    }
}
