package Test;

import backend.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestName {

    @Test
    public void testConstructorAndGetters() {
        Name name = new Name("Anmol", "Acharya");

        assertEquals("Anmol", name.getFirstName());
        assertEquals("Acharya", name.getLastName());
    }

    @Test
    public void testToString() {
        Name name = new Name("Anmol", "Acharya");

        assertEquals("Anmol Acharya", name.toString());
    }

    @Test
    public void testDifferentNameValues() {
        Name name = new Name("Alice", "Green");

        assertEquals("Alice", name.getFirstName());
        assertEquals("Green", name.getLastName());
        assertEquals("Alice Green", name.toString());
    }
}
