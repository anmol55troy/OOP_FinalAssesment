package backend;

public class AdvancedCompetitor extends Competitor {

    public AdvancedCompetitor(Name name, String country) {
        super(name, Level.ADVANCED, country);
    }

    public AdvancedCompetitor(int id, Name name, String country, int[] scores) {
        super(id, name, Level.ADVANCED, country, scores);
    }

    @Override
    public double getOverallScore() {
        return super.getOverallScore() * 1.2; // 20% bonus
    }
}
