package backend;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages a list of competitors and provides reporting functionality
 * @author Anmol Acharya
 * @version 1.0
 */
public class CompetitorList {
    private ArrayList<Competitor> competitors;

    /**
     * Constructor initializes the competitor list
     */
    public CompetitorList() {
        competitors = new ArrayList<>();
    }

    /**
     * Adds a competitor to the list
     * @param competitor The competitor to add
     */
    public void addCompetitor(Competitor competitor) {
        competitors.add(competitor);
    }

    /**
     * Removes a competitor from the list
     * @param competitorID The ID of the competitor to remove
     * @return true if removed, false otherwise
     */
    public boolean removeCompetitor(int competitorID) {
        return competitors.removeIf(c -> c.getCompetitorID() == competitorID);
    }

    /**
     * Finds a competitor by ID
     * @param competitorID The ID to search for
     * @return The competitor if found, null otherwise
     */
    public Competitor findCompetitor(int competitorID) {
        for (Competitor c : competitors) {
            if (c.getCompetitorID() == competitorID) {
                return c;
            }
        }
        return null;
    }

    /**
     * Gets all competitors
     * @return ArrayList of all competitors
     */
    public ArrayList<Competitor> getAllCompetitors() {
        return competitors;
    }

    /**
     * Finds the competitor with the highest overall score
     * @return The top competitor
     */
    public Competitor getTopCompetitor() {
        if (competitors.isEmpty()) {
            return null;
        }

        Competitor top = competitors.get(0);
        for (Competitor c : competitors) {
            if (c.getOverallScore() > top.getOverallScore()) {
                top = c;
            }
        }
        return top;
    }

    /**
     * Generates a frequency report of all individual scores
     * @return Map with score as key and frequency as value
     */
    public Map<Integer, Integer> getScoreFrequency() {
        Map<Integer, Integer> frequency = new HashMap<>();

        for (Competitor c : competitors) {
            int[] scores = c.getScores();
            if (scores != null) {
                for (int score : scores) {
                    frequency.put(score, frequency.getOrDefault(score, 0) + 1);
                }
            }
        }

        return frequency;
    }

    /**
     * Generates a full report of all competitors
     * @return String containing the complete report
     */
    public String getFullReport() {
        StringBuilder report = new StringBuilder();
        
        report.append("====== COMPETITOR REPORT ======\n\n");
        
        // Table header
        report.append(String.format("%-15s %-25s %-15s %-20s %-10s%n", 
            "Competitor ID", "Name", "Level", "Scores", "Overall"));
        report.append("-".repeat(90)).append("\n");

        // Table rows
        for (Competitor c : competitors) {
            String scoresStr = "";
            if (c.getScores() != null) {
                StringBuilder sb = new StringBuilder();
                for (int score : c.getScores()) {
                    sb.append(score).append(" ");
                }
                scoresStr = sb.toString().trim();
            }

            report.append(String.format("%-15d %-25s %-15s %-20s %-10.2f%n",
                c.getCompetitorID(),
                c.getName().toString(),
                c.getLevel().toString(),
                scoresStr,
                c.getOverallScore()));
        }

        report.append("\n");

        // Full details of first competitor (if exists)
        if (!competitors.isEmpty()) {
            report.append("Full Details for CompetitorID ").append(competitors.get(0).getCompetitorID()).append(":\n");
            report.append(competitors.get(0).getFullDetails()).append("\n\n");
        }

        // Short details of second competitor (if exists)
        if (competitors.size() > 1) {
            report.append("Short Details for CompetitorID ").append(competitors.get(1).getCompetitorID()).append(":\n");
            report.append(competitors.get(1).getShortDetails()).append("\n\n");
        }

        // Statistical Summary
        report.append("====== STATISTICAL SUMMARY ======\n");
        report.append("Total number of competitors: ").append(competitors.size()).append("\n");

        Competitor top = getTopCompetitor();
        if (top != null) {
            report.append("Competitor with the highest score: ").append(top.getName().toString())
                  .append(" with an overall score of ").append(String.format("%.2f", top.getOverallScore())).append("\n");
        }

        // Score frequency
        Map<Integer, Integer> freq = getScoreFrequency();
        if (!freq.isEmpty()) {
            report.append("\nFrequency of individual scores:\n");
            report.append("Score:     ");
            freq.keySet().stream().sorted().forEach(score -> report.append(String.format("%-5d", score)));
            report.append("\nFrequency: ");
            freq.keySet().stream().sorted().forEach(score -> report.append(String.format("%-5d", freq.get(score))));
            report.append("\n");
        }

        return report.toString();
    }
}
