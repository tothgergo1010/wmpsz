
package hu.nye.progtech;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CaveMap {
    private char[][] map;
    private Hero hero;

    public CaveMap(String filePath, Player player) {
        loadMapFromFile(filePath, player);
    }

    private void loadMapFromFile(String filePath, Player player) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Read the first line to get the size, hero's initial position, and direction
            String[] firstLine = br.readLine().split(" ");
            int size = Integer.parseInt(firstLine[0]);

            int heroRow = 0;
            int heroCol = 0;

            // Assuming the hero's initial position is given as a character followed by a number
            if (firstLine.length >= 3) {
                char heroChar = firstLine[1].charAt(0);
                heroCol = heroChar - 'A';
                heroRow = Integer.parseInt(firstLine[2]) - 1; // Adjusted to 0-based index
            } else {
                // Handle the case where there are not enough values for the hero's initial position
                System.out.println("Invalid input format for hero's initial position");
                // You may choose to throw an exception or handle it differently based on your requirements
            }

            // Create the map with the given size
            map = new char[size][size];

            // Initialize the map and count the number of 'U' characters
            int wumpusCount = 0;
            for (int i = 0; i < size; i++) {
                String line = br.readLine();
                for (int j = 0; j < size; j++) {
                    map[i][j] = line.charAt(j);

                    // Count the number of 'U' characters
                    if (map[i][j] == 'U') {
                        wumpusCount++;
                    }

                    // Check if the current position is where the hero should be
                    if (i == heroRow && j == heroCol) {
                        // Place the hero on the map
                        map[i][j] = 'H';
                    }
                }
            }

            // Create the hero with initial position, direction, and arrows
            hero = new Hero(heroRow, heroCol);
            hero.setPlayer(player);
            hero.setArrows(wumpusCount); // Set the initial number of arrows based on 'U' count

            char heroDirection = firstLine[3].charAt(0);

            if (heroDirection == 'I') {
                hero.setFacingNorth(true);
                hero.setFacingWest(false);
                hero.setFacingSouth(false);
                hero.setFacingEast(false);
            } else if (heroDirection == 'J') {
                hero.setFacingNorth(false);
                hero.setFacingWest(true);
                hero.setFacingSouth(false);
                hero.setFacingEast(false);
            } else if (heroDirection == 'K') {
                hero.setFacingNorth(false);
                hero.setFacingWest(false);
                hero.setFacingSouth(true);
                hero.setFacingEast(false);
            } else if (heroDirection == 'L') {
                hero.setFacingNorth(false);
                hero.setFacingWest(false);
                hero.setFacingSouth(false);
                hero.setFacingEast(true);
            }

        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // Handle exceptions (e.g., file not found, invalid format)
            e.printStackTrace();
        }
    }

    public String generateGameStateString() {
        StringBuilder gameState = new StringBuilder();

        // Append the map
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                gameState.append(map[i][j]).append(" ");
            }
            gameState.append("\n");
        }

        // Append wumpusShot information
        gameState.append("Wumpus Shot: ").append(hero.getPlayer().getWumpusShot()).append("\n");

        return gameState.toString();
    }

    public void printMap() {
        // Implement the logic to print the map
        hero.printFacingDirection();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void updateHeroPosition(char action) {
        hero.move(action, map);
    }

    public Hero getHero() {
        return hero;
    }

    public char[][] getMap() {
        return map;
    }

    public boolean isGameWon() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'U' || map[i][j] == 'G') {
                    return false; // Still 'U' or 'G' left on the map
                }
            }
        }
        return true; // No 'U' or 'G' found on the map, game won
    }
}
