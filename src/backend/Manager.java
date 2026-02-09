package backend;

import database.DBConnection;
import java.sql.*;

/**
 * Manages database operations and competitor data
 */
public class Manager {
    private CompetitorList competitorList;
    private Connection connection;

    public Manager() {
        competitorList = new CompetitorList();
        connection = DBConnection.getConnection();
    }

    /**
     * Loads all competitors from database
     */
    public void loadCompetitorsFromDatabase() {
        String query = "SELECT * FROM Competitors";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String levelStr = rs.getString("level");
                String country = rs.getString("country");
                
                // Get scores
                int[] scores = new int[5];
                scores[0] = rs.getInt("score1");
                scores[1] = rs.getInt("score2");
                scores[2] = rs.getInt("score3");
                scores[3] = rs.getInt("score4");
                scores[4] = rs.getInt("score5");

                Name name = new Name(firstName, lastName);
                Level level = Level.fromString(levelStr);

                Competitor competitor;
                switch (level) {
                    case BEGINNER:
                        competitor = new BeginnerCompetitor(id, name, country, scores);
                        break;
                    case INTERMEDIATE:
                        competitor = new IntermediateCompetitor(id, name, country, scores);
                        break;
                    case ADVANCED:
                        competitor = new AdvancedCompetitor(id, name, country, scores);
                        break;
                    default:
                        competitor = new Competitor(id, name, level, country, scores);
                }

                competitorList.addCompetitor(competitor);
            }

        } catch (SQLException e) {
            System.err.println("Error loading competitors: " + e.getMessage());
        }
    }

    /**
     * Adds a competitor to database
     */
    public boolean addCompetitorToDatabase(Competitor competitor) {
        String query = "INSERT INTO Competitors (first_name, last_name, level, country, score1, score2, score3, score4, score5) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, competitor.getName().getFirstName());
            pstmt.setString(2, competitor.getName().getLastName());
            pstmt.setString(3, competitor.getLevel().toString());
            pstmt.setString(4, competitor.getCountry());

            int[] scores = competitor.getScores();
            if (scores != null && scores.length >= 5) {
                for (int i = 0; i < 5; i++) {
                    pstmt.setInt(5 + i, scores[i]);
                }
            } else {
                for (int i = 0; i < 5; i++) {
                    pstmt.setInt(5 + i, 0);
                }
            }

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        competitor.setCompetitorID(generatedKeys.getInt(1));
                    }
                }
                competitorList.addCompetitor(competitor);
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error adding competitor: " + e.getMessage());
        }

        return false;
    }

    /**
     * Updates competitor scores in database
     */
    public boolean updateCompetitorScores(int competitorID, int[] scores) {
        String query = "UPDATE Competitors SET score1=?, score2=?, score3=?, score4=?, score5=? WHERE id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            for (int i = 0; i < 5 && i < scores.length; i++) {
                pstmt.setInt(i + 1, scores[i]);
            }
            pstmt.setInt(6, competitorID);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error updating scores: " + e.getMessage());
        }

        return false;
    }

    public CompetitorList getCompetitorList() {
        return competitorList;
    }

    public String generateReport() {
        return competitorList.getFullReport();
    }

    public Competitor findCompetitor(int id) {
        return competitorList.findCompetitor(id);
    }

    /**
     * Checks if a competitor with the same name already exists
     */
    public boolean isNameExists(String firstName, String lastName) {
        String query = "SELECT COUNT(*) FROM Competitors WHERE first_name = ? AND last_name = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error checking name: " + e.getMessage());
        }

        return false;
    }
}