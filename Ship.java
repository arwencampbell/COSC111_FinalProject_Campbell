/**
 * Made by: Arwen Campbell
 * Date: 12/14/2025
 * Ship class holds information about each ship:
 * Name, length, hit counter, and symbol used on the board
 */

public class Ship {

    // Attributes
    private String name;
    private int length;
    private int hits;
    private char symbol;

    /**
     * Ship Constructor:
     * Creates a ship with a name, length, and symbol
     */
    public Ship(String name, int length) {
        this.name = name;
        this.length = length;
        this.hits = 0;
        this.symbol = name.charAt(0);
    }

    // Get Ship Name
    public String getName() {
        return name;
    }

    // Get Ship Length
    public int getLength() {
        return length;
    }

    // Get Ship Symbol
    public char getSymbol() {
        return symbol;
    }

    // Get hits
    public int getHits() {
        return hits;
    }

    /**
     * Called when the ship is hit by a shot.
     * Increases the hit counter by one.
     */
    public void trackHits() {
        hits += 1;
    }

    // Returns true when the ship has been hit enough times to sink
    public boolean isSunk() {
        return hits >= length;
    }

}
