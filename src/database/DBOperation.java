package database;

import java.sql.*;

/**
 * General database operations
 */
public class DBOperation {

    /**
     * Deletes a question from database
     * @param questionId The ID of the question to delete
     * @return true if successful
     */
    public static boolean deleteQuestion(int questionId) {
        Connection conn = DBConnection.getConnection();
        String query = "DELETE FROM Questions WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, questionId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting question: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates a question in database
     * @param question The question with updated data
     * @return true if successful
     */
    public static boolean updateQuestion(backend.Question question) {
        Connection conn = DBConnection.getConnection();
        
        String query = "UPDATE Questions SET question_text=?, option_a=?, option_b=?, option_c=?, " +
                      "option_d=?, correct_answer=?, level=? WHERE id=?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, question.getQuestionText());
            pstmt.setString(2, question.getOptionA());
            pstmt.setString(3, question.getOptionB());
            pstmt.setString(4, question.getOptionC());
            pstmt.setString(5, question.getOptionD());
            pstmt.setString(6, question.getCorrectAnswer());
            pstmt.setString(7, question.getLevel().toString());
            pstmt.setInt(8, question.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error updating question: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets total count of questions by level
     */
    public static int getQuestionCount(backend.Level level) {
        Connection conn = DBConnection.getConnection();
        String query = "SELECT COUNT(*) as count FROM Questions WHERE level = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, level.toString());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            System.err.println("Error counting questions: " + e.getMessage());
        }
        
        return 0;
    }
}
