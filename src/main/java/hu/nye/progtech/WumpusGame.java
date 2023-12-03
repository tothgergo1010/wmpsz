//standalone WumpusGame class
package hu.nye.progtech;

import java.util.Scanner;

public class WumpusGame {

    private static final String FILE_PATH = "C:\\Users\\Gergo\\IdeaProjects\\wump-master\\caves\\wumpuszinput.txt";
    private static final String WINNERS_FILE_PATH = "C:\\Users\\Gergo\\IdeaProjects\\wump-master\\players\\";


    public static boolean fileRead = false;

    public void startGame() {
        Scanner scanner = new Scanner(System.in);

        // Get player's name and save it to file
        Player player = getPlayerInfo(scanner);

        System.out.println("Good luck, " + player.getPlayerName());

        int choice;
        CaveMap caveMap = null;

        do {
            displayMenu();
            choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    caveMap = createCaveMap(player);
                    fileRead = true;
                    System.out.println("---Map read from input file---");
                    break;
                case 2:
                    if (!fileRead) {
                        System.out.println("---You need a map to play :)---");
                        break;
                    }
                    playGame(caveMap);

                    break;
                case 3:
                    System.out.println("---Thanks for playing!---");
                    System.exit(0);
                default:
                    System.out.println("---Invalid choice.---");
            }

        } while (choice != 3);
    }

    private Player getPlayerInfo(Scanner scanner) {
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();
        return new Player(playerName);
    }

    private CaveMap createCaveMap(Player player) {
        return new CaveMap(FILE_PATH, player);
    }

    private void playGame(CaveMap caveMap) {
        // Game loop
        while (true) {
            caveMap.printMap();
            System.out.print("Move(W/A/S/D)|Turn(I/J/K/L)|F-Shoot|Q-Quit|M-Save): ");
            char direction = getUserInput();
            caveMap.updateHeroPosition(direction);

            if (caveMap.isGameWon()) {
                Player winner = caveMap.getHero().getPlayer();
                System.out.println("Congratulations! You've won the game!");
                System.out.println("-----------------------------");
                // Update player info
                winner.incrementWumpusShot();
                winner.collectGold();

                // Save winner's information to winners.txt
                winner.savePlayerInfoToFile(WINNERS_FILE_PATH, false);
                System.out.println("New game started:");
                startGame();
            }
        }
    }

    public static char getUserInput() {
        try {
            return new Scanner(System.in).nextLine().charAt(0);
        } catch (Exception e) {
            e.printStackTrace();
            return ' ';
        }
    }

    public static void displayMenu() {
        System.out.println("Menu:");
        System.out.println("1. Read from input file");
        System.out.println("2. Play");
        System.out.println("3. Exit");
    }

    private int getUserChoice(Scanner scanner) {
        int choice;
        while (true) {
            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 3) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
        return choice;
    }
}
