import java.util.Scanner;

/**
 * Player class represents human and computer players.
 * Each Player has a name and a Board.
 */

public class Player {

    // Attributes
    private Scanner kb = new Scanner(System.in);
    private String name;
    private Board board = new Board(0, 0);

    /**
     * Player constructor:
     * Creates a new Player with a name, board size, and # of ships
     */
    public Player(String name, int size, int numShips) {
        this.name = name;
        this.board = new Board(size, numShips);
    }

    // Get Player Name
    public String getName() {
        return name;
    }

    // Set Player Name
    public void setName(String name) {
        this.name = name;
    }

    // Get Board
    public Board getBoard() {
        return board;
    }

    // Set Board
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Controls how ships are placed
     * If random calls placeShipsRandomly
     * If manual allows player to manually choose placements of ships
     */
    public void placeShips(boolean random) {
        if (random) {
            board.placeShipsRandomly();
        } else {
            boolean validInput1;
            boolean validInput2;
            int choice;
            boolean horizontal = false;

            // Loop Through each ship and place manually
            // TODO: make ship lists different for each size of board
            for (Ship s : board.getShips()) {
                boolean placed = false;
                validInput1 = false;
                validInput2 = false;
                while (placed == false) {
                    board.printBoard();
                    // Ask Player to choose direction of ship
                    while (validInput1 == false) {
                        System.out.println("");
                        System.out.println("Now Placing: " + s.getName() + " (Length: " + s.getLength() + ")");
                        System.out.println("Pick a Direction for this Ship to face.");
                        System.out.println("Type in the number of your choice. ie. 1 ");
                        System.out.println("1. Horizontal");
                        System.out.println("2. Vertical");
                        choice = kb.nextInt();
                        if (choice == 1) {
                            validInput1 = true;
                            horizontal = true;
                        } else if (choice == 2) {
                            validInput1 = true;
                            horizontal = false;
                        } else {
                            System.out.println("That input is Invalid. Please try again.");
                        }
                    }
                    // Ask Player for coordinates
                    while (validInput2 == false) {
                        boolean correctCorr = false;
                        while (correctCorr == false) {
                            System.out.println("");
                            System.out.println("Enter top-left coordinate of ship. ie. A 2, E 5 etc.");
                            char charRow = kb.next().charAt(0);
                            int placeAtCol = kb.nextInt() - 1;
                            int placeAtRow = -1;
                            // Change row letter to number
                            for (int r = 0; r < board.getSize(); r++) {
                                if (charRow == board.getRowLabels()[r]) {
                                    placeAtRow = r;
                                }
                            }
                            // Try to place ship
                            correctCorr = board.checkAndPlaceShip(s, placeAtRow, placeAtCol, horizontal);
                            if (correctCorr) {
                                placed = true;
                                validInput2 = true;
                            } else {
                                System.out.println("That is Invalid. Please try again.");
                            }
                        }
                    }
                }
            }
        }
    }

}
