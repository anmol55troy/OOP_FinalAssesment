package backend;

public class BeginnerCompetitor extends Competitor {

    public BeginnerCompetitor(Name name, String country) {
        super(name, Level.BEGINNER, country);
    }

    public BeginnerCompetitor(int id, Name name, String country, int[] scores) {
        super(id, name, Level.BEGINNER, country, scores);
    }

    @Override
    public double getOverallScore() {
        return super.getOverallScore() * 1.0; // Beginner gets full score
    }
}
