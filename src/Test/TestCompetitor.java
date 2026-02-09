package Test;

import backend.*;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestCompetitor {

    private Competitor competitor;
    private Name name;
    private int[] scores;

    @BeforeEach
    void setUp() {
        name = new Name("Anmol", "Acharya");
        scores = new int[]{5, 4, 3, 5, 4};
        competitor = new Competitor(1, name, Level.BEGINNER, "Nepal", scores);
    }

    // ---------- Constructor Tests ----------

    @Test
    void testConstructorWithScores() {
        assertEquals(1, competitor.getCompetitorID());
        assertEquals(name, competitor.getName());
        assertEquals(Level.BEGINNER, competitor.getLevel());
        assertEquals("Nepal", competitor.getCountry());
        assertArrayEquals(scores, competitor.getScores());
    }

    @Test
    void testConstructorWithoutScores() {
        Competitor c = new Competitor(name, Level.ADVANCED, "Nepal");
        assertEquals(name, c.getName());
        assertEquals(Level.ADVANCED, c.getLevel());
        assertEquals("Nepal", c.getCountry());
        assertNull(c.getScores());
    }

    // ---------- Getter & Setter Tests ----------

    @Test
    void testSetAndGetCompetitorID() {
        competitor.setCompetitorID(10);
        assertEquals(10, competitor.getCompetitorID());
    }

    @Test
    void testSetAndGetCountry() {
        competitor.setCountry("India");
        assertEquals("India", competitor.getCountry());
    }

    @Test
    void testSetAndGetScores() {
        int[] newScores = {2, 3, 4};
        competitor.setScores(newScores);
        assertArrayEquals(newScores, competitor.getScores());
    }

    // ---------- getOverallScore Tests ----------

    @Test
    void testGetOverallScore() {
        double expected = (5 + 4 + 3 + 5 + 4) / 5.0;
        assertEquals(expected, competitor.getOverallScore());
    }

    @Test
    void testGetOverallScoreWithNullScores() {
        competitor.setScores(null);
        assertEquals(0.0, competitor.getOverallScore());
    }

    @Test
    void testGetOverallScoreWithEmptyScores() {
        competitor.setScores(new int[]{});
        assertEquals(0.0, competitor.getOverallScore());
    }

    // ---------- getFullDetails Test ----------

    @Test
    void testGetFullDetails() {
        String details = competitor.getFullDetails();

        assertTrue(details.contains("Competitor number 1"));
        assertTrue(details.contains("Anmol"));
        assertTrue(details.contains("Nepal"));
        assertTrue(details.contains("BEGINNER"));
        assertTrue(details.contains("overall score"));
    }

    // ---------- getShortDetails Test ----------

    @Test
    void testGetShortDetails() {
        String shortDetails = competitor.getShortDetails();
        assertEquals("CN 1 (AA) has overall score 4.20", shortDetails);
    }

    // ---------- toString Test ----------

    @Test
    void testToString() {
        String text = competitor.toString();
        assertTrue(text.contains("Competitor"));
        assertTrue(text.contains("Anmol"));
        assertTrue(text.contains("Nepal"));
    }
}
