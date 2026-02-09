package Test;

import backend.*;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BeginnerCompetitorTest {

    @Test
    public void testConstructorWithoutScores() {
        Name name = new Name("Anmol", "Acharya");
        BeginnerCompetitor bc = new BeginnerCompetitor(name, "Nepal");

        assertEquals(Level.BEGINNER, bc.getLevel());
        assertEquals("Nepal", bc.getCountry());
        assertEquals("Anmol", bc.getName().getFirstName());
        assertEquals("Acharya", bc.getName().getLastName());
    }

    @Test
    public void testConstructorWithScores() {
        Name name = new Name("Alice", "Green");
        int[] scores = {5, 4, 3, 5, 4};

        BeginnerCompetitor bc = new BeginnerCompetitor(1, name, "UK", scores);

        assertEquals(1, bc.getCompetitorID());
        assertEquals(Level.BEGINNER, bc.getLevel());
        assertArrayEquals(scores, bc.getScores());
    }

    @Test
    public void testOverallScoreCalculation() {
        Name name = new Name("Anmol", "Acharya");
        int[] scores = {5, 5, 5, 5, 5};

        BeginnerCompetitor bc = new BeginnerCompetitor(1, name, "Nepal", scores);

        // Beginner multiplier is 1.0, so average should be unchanged
        assertEquals(5.0, bc.getOverallScore(), 0.001);
    }

    @Test
    public void testOverallScoreWithEmptyScores() {
        Name name = new Name("Test", "User");
        BeginnerCompetitor bc = new BeginnerCompetitor(2, name, "Nepal", new int[]{});

        assertEquals(0.0, bc.getOverallScore(), 0.001);
    }
}

