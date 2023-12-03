package hu.nye.progtech;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.beans.XMLEncoder;

public class SaveManager {

    public static void saveMap(MapState mapState, String directoryPath) {
        try {
            int fileNumber = 1;
            String filePath;
            do {
                filePath = directoryPath + "\\saved_map" + fileNumber + ".xml";
                fileNumber++;
            } while (Files.exists(Paths.get(filePath)));

            try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filePath)))) {
                encoder.writeObject(mapState);
                System.out.println("Map saved to '" + filePath + "'");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
