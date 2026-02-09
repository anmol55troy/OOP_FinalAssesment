package backend;

import java.util.Arrays;

/**
 * Represents a competitor in the quiz competition
 * @author Anmol Acharya 
 * @version 1.0
 */
public class Competitor {

    // ---------- Instance Variables ----------
    private int id;
    private Name name;
    private Level level;      // Beginner, Intermediate, Advanced
    private String country;    // Extra attribute
    private int[] scores;      // Score array


    // ---------- Constructors ----------
    public Competitor(Name name, Level level, String country) {
        this.name = name;
        this.level = level;
        this.country = country;
    }

    public Competitor(int id, Name name, Level level, String country, int[] scores) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.country = country;
        this.scores = scores;
    }


    // ---------- Getters and Setters ----------
    public int getCompetitorID() {
        return id;
    }

    public void setCompetitorID(int id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int[] getScores() {
        return scores;
    }

    public void setScores(int[] scores) {
        this.scores = scores;
    }

    public int[] getScoreArray() {
        return scores;
    }

    /**
     * Returns the full details of the competitor
     * @return String containing all competitor details
     */
    public String getFullDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Competitor number ").append(id);
        details.append(", name ").append(name.toString());
        details.append(", country ").append(country).append(".\n");
        details.append(name.getFirstName()).append(" is a ").append(level.toString());
        details.append(" and has scores: ");
        
        if (scores != null && scores.length > 0) {
            for (int i = 0; i < scores.length; i++) {
                details.append(scores[i]);
                if (i < scores.length - 1) {
                    details.append(", ");
                }
            }
        }
        
        details.append(".\nThis gives an overall score of ").append(String.format("%.2f", getOverallScore())).append(".");
        
        return details.toString();
    }

    /**
     * Returns short details of the competitor
     * @return String with competitor number, initials and overall score
     */
    public String getShortDetails() {
        String initials = "";
        if (name != null) {
            initials = name.getFirstName().substring(0, 1).toUpperCase() + 
                      name.getLastName().substring(0, 1).toUpperCase();
        }
        return String.format("CN %d (%s) has overall score %.2f", id, initials, getOverallScore());
    }

    /**
     * Calculates the overall score based on scores array
     * @return average score
     */
    public double getOverallScore() {
        if (scores == null || scores.length == 0) {
            return 0.0;
        }
        
        double overallScore = 0;
        for (int score : scores) {
            overallScore += score;
        }
        return overallScore / scores.length;
    }

    @Override
    public String toString() {
        return "Competitor [id=" + id + ", name=" + name + ", level=" + level + ", country=" + country
                + ", scores=" + Arrays.toString(scores) + "]";
    }
}
