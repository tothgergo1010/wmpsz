package hu.nye.progtech;


import java.io.*;


public class MapState implements Serializable {

    private static final long serialVersionUID = 1L;
    private int mapStateID;
    private int mapSize;
    private Hero hero;
    private char[][] map;
    public int getMapSize() {
        return mapSize;
    }

    public MapState() {

    }

    public MapState(char[][] map, Hero hero) {
        this.mapSize = map.length;
        this.hero = hero;
        this.map = map;
    }
    public int getMapStateID() {
        return mapStateID;
    }
    public void setMapStateID(int mapStateID) {
        this.mapStateID = mapStateID;
    }


    public void toXmlFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            StringBuilder xmlBuilder = new StringBuilder();
            xmlBuilder.append("<mapState>\n");
            xmlBuilder.append("    <mapSize>").append(mapSize).append("</mapSize>\n");
            xmlBuilder.append("    <hero>\n");
            xmlBuilder.append("        <row>").append(hero.getRow()).append("</row>\n");
            xmlBuilder.append("        <col>").append(hero.getCol()).append("</col>\n");
            xmlBuilder.append("        <facingDirection>").append(getFacingDirectionSymbol()).append("</facingDirection>\n");
            xmlBuilder.append("    </hero>\n");
            xmlBuilder.append("    <map>\n");

            for (char[] row : map) {
                xmlBuilder.append("        <row>").append(new String(row)).append("</row>\n");
            }

            xmlBuilder.append("    </map>\n");
            xmlBuilder.append("</mapState>\n");

            writer.write(xmlBuilder.toString());

            System.out.println("Map saved to '" + filePath + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public char getFacingDirectionSymbol() {
        if (hero.isFacingNorth()) {
            return 'I';
        } else if (hero.isFacingWest()) {
            return 'J';
        } else if (hero.isFacingSouth()) {
            return 'K';
        } else if (hero.isFacingEast()) {
            return 'L';
        }
        return ' ';
    }

    public char[][] getMap() {
        return map;
    }

    public Hero getHero() {
        return hero;
    }


}
