package Test;

import backend.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdvancedCompetitorTest {

    @Test
    void testConstructorWithoutID() {
        Name name = new Name("Alice", "Wonderland");
        AdvancedCompetitor adv = new AdvancedCompetitor(name, "USA");

        assertEquals(name, adv.getName());
        assertEquals("USA", adv.getCountry());
        assertEquals(Level.ADVANCED, adv.getLevel());
        assertNotNull(adv.getScores()); // should initialize scores array
        for (int score : adv.getScores()) {
            assertEquals(0, score); // default scores should be 0
        }
    }

    @Test
    void testConstructorWithIDAndScores() {
        int[] scores = {10, 20, 30, 40, 50};
        Name name = new Name("Bob", "Builder");
        AdvancedCompetitor adv = new AdvancedCompetitor(1, name, "UK", scores);

        assertEquals(1, adv.getCompetitorID());
        assertEquals(name, adv.getName());
        assertEquals("UK", adv.getCountry());
        assertEquals(Level.ADVANCED, adv.getLevel());
        assertArrayEquals(scores, adv.getScores());
    }

    @Test
    void testGetOverallScoreWithBonus() {
        int[] scores = {10, 20, 30, 40, 50}; // total = 150
        Name name = new Name("Charlie", "Brown");
        AdvancedCompetitor adv = new AdvancedCompetitor(1, name, "Canada", scores);

        double expected = 150 * 1.2; // 20% bonus
        assertEquals(expected, adv.getOverallScore());
    }

    @Test
    void testSetAndGetID() {
        Name name = new Name("Dora", "Explorer");
        AdvancedCompetitor adv = new AdvancedCompetitor(name, "Mexico");

        adv.setCompetitorID(99);
        assertEquals(99, adv.getCompetitorID());
    }
}
