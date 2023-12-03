package hu.nye.progtech;

public class Hero {
    private int row;
    private int col;
    private int arrows;
    private int gold;
    private boolean facingNorth;
    private boolean facingWest;
    private boolean facingSouth;
    private boolean facingEast;
    private Player player;


    public Hero(int initialRow, int initialCol) {

        this.row = initialRow;
        this.col = initialCol;
        this.arrows = 1; // Set the initial number of arrows
        this.gold = 0;   // Set the initial amount of gold
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getArrows() {
        return arrows;
    }

    public int getGold() {
        return gold;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void move(char direction, char[][] map) {
        Move.moveHero(direction, map, this);
    }

    // Change the access modifier to package-private
    boolean isValidMove(int newRow, int newCol, char[][] map) {
        return newRow >= 0 && newRow < map.length && newCol >= 0 && newCol < map[0].length;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public void setArrows(int arrows) {
        this.arrows = arrows;
    }


    public void decreaseArrows() {
        if (arrows > 0) {
            arrows--;
        }
    }

    public void collectGold() {
        gold++;
    }
    public boolean isFacingNorth() {
        return facingNorth;
    }

    public void setFacingNorth(boolean facingNorth) {
        this.facingNorth = facingNorth;
    }

    public boolean isFacingWest() {
        return facingWest;
    }

    public void setFacingWest(boolean facingWest) {
        this.facingWest = facingWest;
    }

    public boolean isFacingSouth() {
        return facingSouth;
    }

    public void setFacingSouth(boolean facingSouth) {
        this.facingSouth = facingSouth;
    }

    public boolean isFacingEast() {
        return facingEast;
    }

    public void setFacingEast(boolean facingEast) {
        this.facingEast = facingEast;
    }
    public void printFacingDirection() {
        System.out.println("Facing Direction: " +
                (facingNorth ? "North " : "") +
                (facingWest ? "West " : "") +
                (facingSouth ? "South " : "") +
                (facingEast ? "East" : ""));
    }
}


