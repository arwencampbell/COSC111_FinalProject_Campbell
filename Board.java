/**
 * Made by: Arwen Campbell
 * Date: 12/14/2025
 * Board class is the array used during gameplay.
 * It contains size, 2D array, ships, logic for placing & checking ships
 */

public class Board {

    // Attributes
    private int size;
    private char[][] grid;
    private boolean[][] shotsTaken;
    private Ship lastShipHit = null;
    // Make Ships
    private Ship aircraft_carrier = new Ship("Aircraft Carrier", 5);
    private Ship battleship = new Ship("Battleship", 4);
    private Ship cruiser = new Ship("Cruiser", 3);
    private Ship submarine = new Ship("Submarine", 3);
    private Ship destroyer = new Ship("Destroyer", 2);
    private Ship[] ships = new Ship[] { aircraft_carrier, battleship, cruiser, submarine, destroyer };
    private char[] rows = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };

    /**
     * Board Constructor:
     * Creates an empty game board containing just water
     */
    public Board() {
        this.size = 10;
        this.shotsTaken = new boolean[size][size];
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

    // Get Board
    public char[][] getBoard() {
        return grid;
    }

    // Get Last Ship Hit
    public Ship getLastShipHit() {
        return lastShipHit;
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
        // Check horizontal placement
        if (horizontal) {
            // Check out of bounds
            if (placeAtCol < 0 || placeAtCol + s.getLength() - 1 >= size) {
                return false;
            } else {
                // Check for overlap
                for (int l = 0; l < s.getLength(); l++) {
                    if (grid[placeAtRow][placeAtCol + l] != '~') {
                        return false;
                    }
                }
            }
            // Check vertical placement
        } else {
            // Check out of bounds
            if (placeAtRow < 0 || placeAtRow + s.getLength() - 1 >= size) {
                return false;
            } else {
                // Check for overlap
                for (int l = 0; l < s.getLength(); l++) {
                    if (grid[placeAtRow + l][placeAtCol] != '~') {
                        return false;
                    }
                }
            }
        }
        // Place Ship
        for (int l = 0; l < s.getLength(); l++) {
            if (horizontal) {
                grid[placeAtRow][placeAtCol + l] = s.getSymbol();
            } else {
                grid[placeAtRow + l][placeAtCol] = s.getSymbol();
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
        // Check for Out of bounds
        if (checkRow < 0 || checkRow >= size || checkCol < 0 || checkCol >= size) {
            return false;
        }

        // Check if Already shot
        char cell = grid[checkRow][checkCol];
        if (cell == 'X' || cell == 'O') {
            return false;
        }

        // Check if Miss
        if (cell == '~') {
            grid[checkRow][checkCol] = 'O';
            lastShipHit = null;
            return true;
        }

        // Check if Hit
        Ship hitShip = null;

        switch (cell) {
            case 'A':
                hitShip = aircraft_carrier;
                break;
            case 'B':
                hitShip = battleship;
                break;
            case 'C':
                hitShip = cruiser;
                break;
            case 'D':
                hitShip = destroyer;
                break;
            case 'S':
                hitShip = submarine;
                break;
        }

        // Update Board
        grid[checkRow][checkCol] = 'X';

        // Track Hit
        hitShip.trackHits();
        lastShipHit = hitShip;
        return true;
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

    // Display Hidden Board
    public void printHiddenBoard() {
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
                char c = grid[row][col];
                if (c == 'X' || c == 'O') {
                    System.out.print(c + "  ");
                } else {
                    // Hide Ships as water
                    System.out.print("~  ");
                }
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

    public boolean checkHasShotHere(int row, int col) {
        return shotsTaken[row][col];
    }

    public void markShotTaken(int row, int col) {
        shotsTaken[row][col] = true;
    }
}
