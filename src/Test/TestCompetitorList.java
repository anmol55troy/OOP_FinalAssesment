package Test;

import backend.*;


import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestCompetitorList {

    private CompetitorList competitorList;
    private Competitor c1;
    private Competitor c2;

    @BeforeEach
    void setUp() {
        competitorList = new CompetitorList();

        Name name1 = new Name("Anmol", "Acharya");
        Name name2 = new Name("Sita", "Sharma");

        c1 = new Competitor(1, name1, Level.BEGINNER, "Nepal",
                new int[]{5, 4, 3});
        c2 = new Competitor(2, name2, Level.ADVANCED, "India",
                new int[]{4, 4, 5});

        competitorList.addCompetitor(c1);
        competitorList.addCompetitor(c2);
    }

    // ---------- Add & Get Tests ----------

    @Test
    void testAddCompetitor() {
        assertEquals(2, competitorList.getAllCompetitors().size());
    }

    @Test
    void testGetAllCompetitors() {
        assertTrue(competitorList.getAllCompetitors().contains(c1));
        assertTrue(competitorList.getAllCompetitors().contains(c2));
    }

    // ---------- Find Competitor ----------

    @Test
    void testFindCompetitorFound() {
        Competitor found = competitorList.findCompetitor(1);
        assertNotNull(found);
        assertEquals("Anmol", found.getName().getFirstName());
    }

    @Test
    void testFindCompetitorNotFound() {
        Competitor found = competitorList.findCompetitor(99);
        assertNull(found);
    }

    // ---------- Remove Competitor ----------

    @Test
    void testRemoveCompetitorSuccess() {
        boolean removed = competitorList.removeCompetitor(1);
        assertTrue(removed);
        assertEquals(1, competitorList.getAllCompetitors().size());
    }

    @Test
    void testRemoveCompetitorFail() {
        boolean removed = competitorList.removeCompetitor(99);
        assertFalse(removed);
        assertEquals(2, competitorList.getAllCompetitors().size());
    }

    // ---------- Top Competitor ----------

    @Test
    void testGetTopCompetitor() {
        Competitor top = competitorList.getTopCompetitor();
        assertNotNull(top);
        assertEquals(2, top.getCompetitorID()); // higher average
    }

    @Test
    void testGetTopCompetitorEmptyList() {
        CompetitorList emptyList = new CompetitorList();
        assertNull(emptyList.getTopCompetitor());
    }

    // ---------- Score Frequency ----------

    @Test
    void testGetScoreFrequency() {
        Map<Integer, Integer> freq = competitorList.getScoreFrequency();

        assertEquals(2, freq.get(5)); // two 5s
        assertEquals(3, freq.get(4)); // three 4s
        assertEquals(1, freq.get(3)); // one 3
    }

    // ---------- Full Report ----------

    @Test
    void testGetFullReport() {
        String report = competitorList.getFullReport();

        assertNotNull(report);
        assertTrue(report.contains("COMPETITOR REPORT"));
        assertTrue(report.contains("STATISTICAL SUMMARY"));
        assertTrue(report.contains("Anmol"));
        assertTrue(report.contains("Sita"));
    }
}
