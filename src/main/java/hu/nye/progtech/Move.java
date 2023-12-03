// Standalone Move class
package hu.nye.progtech;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import hu.nye.progtech.MapState;

public class Move {

    public static void moveHero(char direction, char[][] map, Hero hero) {
        int newRow = hero.getRow();
        int newCol = hero.getCol();

        switch (direction) {
            case 'W':
                newRow--;
                break;
            case 'A':
                newCol--;
                break;
            case 'S':
                newRow++;
                break;
            case 'D':
                newCol++;
                break;
            case 'I':
                hero.setFacingNorth(true);
                hero.setFacingWest(false);
                hero.setFacingSouth(false);
                hero.setFacingEast(false);
                break;
            case 'J':
                hero.setFacingNorth(false);
                hero.setFacingWest(true);
                hero.setFacingSouth(false);
                hero.setFacingEast(false);
                break;
            case 'K':
                hero.setFacingNorth(false);
                hero.setFacingWest(false);
                hero.setFacingSouth(true);
                hero.setFacingEast(false);
                break;
            case 'L':
                hero.setFacingNorth(false);
                hero.setFacingWest(false);
                hero.setFacingSouth(false);
                hero.setFacingEast(true);
                break;
            case 'Q':
                System.out.println("---Thanks for playing!---");
                System.exit(0);
            case 'M':
                // Create a MapState object
                saveMapToXmlFile(map, hero, "C:\\Users\\Gergo\\IdeaProjects\\wump-master\\saved");
                System.out.println("---Map Saved---");
                break;
            case 'F':
                if (hero.getArrows() > 0) {
                    System.out.println("-----------------------------");
                    hero.decreaseArrows();

                    // Check if there is a Wumpus in the facing direction
                    if (checkWumpusInFacingDirection(hero, map)) {
                        System.out.println("You hit the Wumpus!");
                        System.out.println("*screeeeeam*");

                        // Increment the Wumpus shot count for the player
                        hero.getPlayer().incrementWumpusShot();

                        // Remove the Wumpus from the map
                        removeWumpusInFacingDirection(hero, map);
                    } else {
                        System.out.println("You missed the Wumpus!");
                    }
                } else {
                    System.out.println("-----------------------------");
                    System.out.println("You have no arrows left!");
                }
                break;
            default:
                System.out.println("Invalid direction.");
                return;
        }

        if (hero.isValidMove(newRow, newCol, map)) {
            System.out.println("-----------------------------");
            hero.printFacingDirection();
            // Check if the next position is a pit ('P')
            if (map[newRow][newCol] == 'P') {
                System.out.println("You stepped into a pit room!");
                System.out.println("1 arrow lost");
                hero.decreaseArrows();
                return; // Do not proceed with the move
            }

            if (map[newRow][newCol] != 'W') {
                System.out.println("-----------------------------");
                System.out.println("Arrows: " + hero.getArrows() + ", Gold: " + hero.getGold());

                if (map[newRow][newCol] == 'U') {
                    System.out.println("-----------------------------");
                    System.out.println("---GAME OVER---");
                    System.out.println("You stepped on a Wumpus");
                    System.out.println("Do you want to play again? (Y/N): ");
                    char playAgainChoice = WumpusGame.getUserInput();

                    if (playAgainChoice == 'Y' || playAgainChoice == 'y') {
                        // Reset the flag and exit the program
                        System.out.println("-----------------------------");
                        System.out.println("New game");
                        System.out.println("-----------------------------");
                        WumpusGame.fileRead = false;
                        Main.main(null);
                    } else {
                        // Reset the flag and exit the program
                        WumpusGame.fileRead = false;
                        System.out.println("---Thanks for playing!---");
                        System.exit(0);
                    }

                    return;
                } else if (map[newRow][newCol] == 'G') {
                    System.out.println("You found GOLD!");
                    System.out.print("Press 'G' to pick up the gold: ");
                    WumpusGame.fileRead = false;
                    Scanner scanner = new Scanner(System.in);
                    String input = scanner.nextLine();

                    if ("G".equalsIgnoreCase(input.trim())) {
                        hero.collectGold();
                        System.out.println("-----------------------------");
                        System.out.println("Gold picked up!");
                        map[newRow][newCol] = '*'; // Remove the gold from the map
                        System.out.println("Arrows: " + hero.getArrows() + ", Gold: " + hero.getGold());
                    } else {
                        System.out.println("Gold not picked up.");
                        map[newRow][newCol] = 'G';
                    }
                }

                if (map[hero.getRow()][hero.getCol()] != 'P' && map[hero.getRow()][hero.getCol()] != 'G') {
                    map[hero.getRow()][hero.getCol()] = 'x';
                }

                hero.setRow(newRow);
                hero.setCol(newCol);

                map[hero.getRow()][hero.getCol()] = 'H';

                // Print the amount of gold and arrows after every move
            } else {
                System.out.println("Invalid move. Cannot move onto 'W'.");
            }
        } else {
            System.out.println("Invalid move. Out of bounds.");
        }
    }

    private static void saveMapToXmlFile(char[][] map, Hero hero, String directoryPath) {
        try {
            int fileNumber = 1;
            String filePath;
            do {
                filePath = directoryPath + "\\saved_map" + fileNumber + ".xml";
                fileNumber++;
            } while (Files.exists(Paths.get(filePath)));

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                MapState mapState = new MapState(map, hero);
                mapState.toXmlFile(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static boolean checkWumpusInFacingDirection(Hero hero, char[][] map) {
        int row = hero.getRow();
        int col = hero.getCol();

        if (hero.isFacingNorth()) {
            return row > 0 && map[row - 1][col] == 'U';
        } else if (hero.isFacingWest()) {
            return col > 0 && map[row][col - 1] == 'U';
        } else if (hero.isFacingSouth()) {
            return row < map.length - 1 && map[row + 1][col] == 'U';
        } else if (hero.isFacingEast()) {
            return col < map[0].length - 1 && map[row][col + 1] == 'U';
        }

        return false;
    }

    static void removeWumpusInFacingDirection(Hero hero, char[][] map) {
        int row = hero.getRow();
        int col = hero.getCol();

        if (hero.isFacingNorth()) {
            map[row - 1][col] = '*';
        } else if (hero.isFacingWest()) {
            map[row][col - 1] = '*';
        } else if (hero.isFacingSouth()) {
            map[row + 1][col] = '*';
        } else if (hero.isFacingEast()) {
            map[row][col + 1] = '*';
        }
    }
}
