package backend;

public class IntermediateCompetitor extends Competitor {

    public IntermediateCompetitor(Name name, String country) {
        super(name, Level.INTERMEDIATE, country);
    }

    public IntermediateCompetitor(int id, Name name, String country, int[] scores) {
        super(id, name, Level.INTERMEDIATE, country, scores);
    }

    @Override
    public double getOverallScore() {
        return super.getOverallScore() * 1.1; // 10% bonus
    }
}
