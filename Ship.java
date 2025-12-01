/**
 * Ship class holds information about each ship:
 * Name, length, hit counter, and symbol used on the board
 */

public class Ship {

    // Attributes
    private String name;
    private int length;
    private int hits;
    private boolean isSunk;
    private char symbol;

    /**
     * Ship Constructor:
     * Creates a ship with a name, length, and symbol
     */
    public Ship(String name, int length) {
        this.name = name;
        this.length = length;
        this.hits = 0;
        this.isSunk = false;
        this.symbol = name.charAt(0);
    }

    // Get Ship Name
    public String getName() {
        return name;
    }

    // Set Ship Name
    public void setName(String name) {
        this.name = name;
    }

    // Get Ship Length
    public int getLength() {
        return length;
    }

    // Set Ship Length
    public void setLength(int length) {
        this.length = length;
    }

    // Get Ship Symbol
    public char getSymbol() {
        return symbol;
    }

    // Set Ship Symbol
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    // Get hits
    public int getHits() {
        return hits;
    }

    // Set hits
    public void setHits(int hits) {
        this.hits = hits;
    }

    // Get isSunk
    public boolean getIsSunk() {
        return isSunk;
    }

    // Set isSunk
    public void setIsSunk(boolean isSunk) {
        this.isSunk = isSunk;
    }

    /**
     * Called when the ship is hit by a shot.
     * Increases the hit counter by one.
     */
    public void trackHit() {
        hits += 1;
    }

    // Returns true when the ship has been hit enough times to sink
    public boolean isSunk() {
        if (hits >= length) {
            isSunk = true;
        }
        return isSunk;
    }

}
