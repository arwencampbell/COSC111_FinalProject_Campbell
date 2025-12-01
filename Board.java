/**
 * Board class is the array used during gameplay.
 * It contains size, 2D array, ships, logic for placing & checking ships
 */

public class Board {

    // Attributes
    private int size;
    private char[][] grid;
    private Ship lastShipHit = null;
    // Make Ships
    private Ship aircraft_carrier = new Ship("Aircraft Carrier", 5);
    private Ship battleship = new Ship("Battleship", 4);
    private Ship cruiser = new Ship("Cruiser", 3);
    private Ship destroyer1 = new Ship("Destroyer", 2);
    private Ship destroyer2 = new Ship("Destroyer", 2);
    // TODO: fix for different sized boards, currently player always has 5 ships
    private Ship[] ships = new Ship[] { aircraft_carrier, battleship, cruiser, destroyer1, destroyer2 };
    private char[] rows = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };

    /**
     * Board Constructor:
     * Creates an empty game board containing just water
     */
    public Board(int size, int numShips) {
        // TODO: Set up numShips based on size of board
        this.size = size;
        this.grid = new char[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                this.grid[row][col] = '~';
            }
        }
    }

    // Get Board Size
    public int getSize() {
        return size;
    }

    // Set Board Size
    public void setSize(int size) {
        this.size = size;
    }

    // Get Board
    public char[][] getBoard() {
        return grid;
    }

    // Set Board
    public void setBoard(char[][] grid) {
        this.grid = grid;
    }

    // Get Last Ship Hit
    public Ship getLastShipHit() {
        return lastShipHit;
    }

    // Get each ship
    public Ship getAircraftCarrier() {
        return aircraft_carrier;
    }

    public Ship getBattleship() {
        return battleship;
    }

    public Ship getCruiser() {
        return cruiser;
    }

    public Ship getDestroyer1() {
        return destroyer1;
    }

    public Ship getDestroyer2() {
        return destroyer2;
    }

    // Get All Ships
    public Ship[] getShips() {
        return ships;
    }

    // Get Row Labels
    public char[] getRowLabels() {
        return rows;
    }

    /**
     * Random Ship Placement:
     * Picks random direction, rows, and columns until all ships fit
     */
    public void placeShipsRandomly() {
        for (Ship s : ships) {
            boolean placed = false;
            while (placed == false) {
                int placeAtRow = (int) (Math.random() * (size));
                int placeAtCol = (int) (Math.random() * (size));
                boolean horizontal;
                if (Math.random() < 0.5) {
                    horizontal = true;
                } else {
                    horizontal = false;
                }
                if (checkAndPlaceShip(s, placeAtRow, placeAtCol, horizontal)) {
                    placed = true;
                }
            }
        }
    }

    /**
     * Tries to place ship by:
     * Check it will stay within array
     * Check it is not overlapping another ship
     * Then place ship
     * Returns true if placement was successful
     */
    public boolean checkAndPlaceShip(Ship s, int placeAtRow, int placeAtCol, boolean horizontal) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (row == placeAtRow && col == placeAtCol) {
                    // Check horizontal placement
                    if (horizontal) {
                        if (row + s.getLength() > grid[row].length) {
                            return false;
                        } else {
                            for (int l = 0; l < s.getLength(); l++) {
                                if (grid[row][col + l] != '~') {
                                    return false;
                                }
                            }
                        }
                        // Check vertical placement
                    } else {
                        if (col + s.getLength() > grid[row].length) {
                            return false;
                        } else {
                            for (int l = 0; l < s.getLength(); l++) {
                                if (grid[row + l][col] != '~') {
                                    return false;
                                }
                            }
                        }
                    }
                    // Place Ship
                    for (int l = 0; l < s.getLength(); l++) {
                        if (horizontal) {
                            grid[row][col + l] = s.getSymbol();
                        } else {
                            grid[row + l][col] = s.getSymbol();
                        }
                    }
                    return true;
                }
            }
        }
        return true;
    }

    /**
     * Check if Coordinate can be Shot at
     * If so returns true
     * If not returns false
     */
    public boolean checkShotAndUpdate(int checkRow, int checkCol) {
        boolean correctCorr = false;
        // Check for Out of bounds
        if (checkRow == -1 || checkCol > grid.length) {
            return correctCorr;
        }
        // TODO: update lastShipHit appropriatly
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (row == checkRow && col == checkCol) {
                    if (grid[row][col] == 'X') { // Hit
                        return correctCorr;
                    } else if (grid[row][col] == 'O') { // Miss
                        return correctCorr;
                    } else if (grid[row][col] == 'A') { // Aircraft Carrier
                        grid[row][col] = 'X';
                        aircraft_carrier.trackHit();
                    } else if (grid[row][col] == 'B') { // Battleship
                        grid[row][col] = 'X';
                        battleship.trackHit();
                    } else if (grid[row][col] == 'C') { // Cruiser
                        grid[row][col] = 'X';
                        cruiser.trackHit();
                    } else if (grid[row][col] == 'D') { // Destroyer
                        grid[row][col] = 'X';
                        // TODO: fix this for having 2 destroyers & other sizes of boards
                    } else if (grid[row][col] == '~') { // Water
                        grid[row][col] = 'O';
                    }
                    correctCorr = true;
                    return correctCorr;
                }
            }
        }
        return correctCorr;
    }

    // Display Board
    public void printBoard() {
        // Print column numbers
        System.out.print("    ");
        for (int col = 0; col < grid.length; col++) {
            System.out.print((col + 1) + "  ");
        }
        System.out.println();

        // Print a top border
        System.out.print("   ");
        for (int col = 0; col < grid.length; col++) {
            System.out.print("___");
        }
        System.out.println();

        // Print each row
        for (int row = 0; row < grid.length; row++) {
            // Row label
            System.out.print(rows[row] + " | ");

            // Row cells
            for (int col = 0; col < grid[row].length; col++) {
                System.out.print(grid[row][col] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Returns true if every ship is sunk.\
    public boolean allShipsSunk() {
        for (Ship s : ships) {
            if (s.isSunk() == false) {
                return false;
            }
        }
        return true;
    }
}
