// Standalone Player class
package hu.nye.progtech;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Player {
    private static int winCount = 0;
    private String playerName;
    private int wumpusShot;
    private int gold;

    public Player(String playerName) {
        this.playerName = playerName;
        this.wumpusShot = -1;
        this.gold = 2;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getWumpusShot() {
        return wumpusShot;
    }

    public int getGold() {
        return gold;
    }

    public void incrementWumpusShot() {
        wumpusShot++;
    }

    public void collectGold() {
        gold++;
    }

    public void savePlayerInfoToFile(String baseFilePath, boolean appendToExisting) {
        try (Writer writer = new BufferedWriter(new FileWriter(getFilePath(baseFilePath, appendToExisting), appendToExisting))) {
            if (!appendToExisting) {
                writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
                writer.write("<players>\n");
            }

            writer.write("  <player>\n");
            writer.write("    <name>" + playerName + "</name>\n");
            writer.write("    <wumpusShot>" + wumpusShot + "</wumpusShot>\n");
            writer.write("    <gold>" + gold + "</gold>\n");
            writer.write("  </player>\n");

            if (!appendToExisting) {
                writer.write("</players>\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing player info to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getFilePath(String baseFilePath, boolean appendToExisting) {
        winCount++;
        return baseFilePath + "winners" + winCount + (appendToExisting ? "_append" : "") + ".xml";
    }
}
