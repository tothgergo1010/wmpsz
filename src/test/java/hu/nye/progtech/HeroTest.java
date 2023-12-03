package hu.nye.progtech;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class HeroTest {

    @Test
    public void testConstructorAndGetterMethods() {
        Hero hero = new Hero(1, 2);

        assertEquals(1, hero.getRow());
        assertEquals(2, hero.getCol());
        assertEquals(1, hero.getArrows());
        assertEquals(0, hero.getGold());

        // Additional tests for other getter methods
        assertFalse(hero.isFacingNorth());
        assertFalse(hero.isFacingWest());
        assertFalse(hero.isFacingSouth());
        assertFalse(hero.isFacingEast());
    }

    @Test
    public void testMoveMethod() {
        char[][] map = {
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };

        Hero hero = new Hero(1, 1);

        // Test moving north
        hero.move('W', map);
        assertEquals(0, hero.getRow());
        assertEquals(1, hero.getCol());

        // Test moving west
        hero.move('A', map);
        assertEquals(0, hero.getRow());
        assertEquals(0, hero.getCol());

        // Additional tests for other directions
        hero.move('S', map);
        assertEquals(1, hero.getRow());
        assertEquals(0, hero.getCol());

        hero.move('D', map);
        assertEquals(1, hero.getRow());
        assertEquals(1, hero.getCol());
    }

    @Test
    public void testPrintFacingDirection() {
        Hero hero = new Hero(0, 0);

        // Set facing directions
        hero.setFacingNorth(true);
        hero.setFacingWest(true);
        hero.setFacingSouth(true);
        hero.setFacingEast(true);

        // Redirect System.out for testing
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Call the method to print facing direction
        hero.printFacingDirection();

        // Reset System.out
        System.setOut(System.out);

        // Normalize line endings for comparison
        String expectedOutput = "Facing Direction: North West South East" + System.lineSeparator();
        String actualOutput = outputStream.toString();

        // Ensure that the printed direction is as expected
        assertEquals(expectedOutput, actualOutput);
    }
}

