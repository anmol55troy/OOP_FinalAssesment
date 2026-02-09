package database;

import backend.Question;
import backend.Level;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Fetches data from database
 */
public class DBFetch {

    /**
     * Fetches questions by level from database
     * @param level The difficulty level
     * @return ArrayList of questions
     */
    public static ArrayList<Question> getQuestionsByLevel(Level level) {
        ArrayList<Question> questions = new ArrayList<>();
        Connection conn = DBConnection.getConnection();

        String query = "SELECT * FROM Questions WHERE level = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, level.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Question q = new Question();
                q.setId(rs.getInt("id"));
                q.setQuestionText(rs.getString("question_text"));
                q.setOptionA(rs.getString("option_a"));
                q.setOptionB(rs.getString("option_b"));
                q.setOptionC(rs.getString("option_c"));
                q.setOptionD(rs.getString("option_d"));
                q.setCorrectAnswer(rs.getString("correct_answer"));
                q.setLevel(level);
                questions.add(q);
            }

            // Shuffle questions
            Collections.shuffle(questions);

        } catch (SQLException e) {
            System.err.println("Error fetching questions: " + e.getMessage());
        }

        return questions;
    }

    /**
     * Gets a random set of questions for a quiz
     * @param level The difficulty level
     * @param count Number of questions to fetch
     * @return ArrayList of questions
     */
    public static ArrayList<Question> getRandomQuestions(Level level, int count) {
        ArrayList<Question> allQuestions = getQuestionsByLevel(level);
        
        if (allQuestions.size() <= count) {
            return allQuestions;
        }
        
        return new ArrayList<>(allQuestions.subList(0, count));
    }
}
