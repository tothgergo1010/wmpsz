// Standalone GetUserChoiceTest class
package hu.nye.progtech;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetUserChoiceTest {

    private WumpusGame wumpusGame;

    @BeforeEach
    void setUp() {
        wumpusGame = new WumpusGame();
    }

    @Test
    void testGetUserChoice() throws Exception {
        // Mock System.in to simulate user input
        String input = "2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Use reflection to access the private method
        Method getUserChoiceMethod = WumpusGame.class.getDeclaredMethod("getUserChoice", Scanner.class);
        getUserChoiceMethod.setAccessible(true);

        // Run the getUserChoice method
        int result = (int) getUserChoiceMethod.invoke(wumpusGame, new Scanner(System.in));

        // Assert that the returned choice is 2
        assertEquals(2, result);

        // Restore the original System.in
        System.setIn(System.in);
    }
}
