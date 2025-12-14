/**
 * Made by: Arwen Campbell
 * Date: 12/14/2025
 * Player class represents human and computer players.
 * Each Player has a name and a Board.
 */

public class Player {

    // Attributes
    private String name;
    private Board board;
    private Game game;

    /**
     * Player constructor:
     * Creates a new Player with a name, board size, and # of ships
     */
    public Player(String name, Game game) {
        this.name = name;
        this.board = new Board();
        this.game = game;
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
            int choice;
            boolean horizontal = false;

            // Loop Through each ship and place manually
            for (Ship s : board.getShips()) {
                boolean placed = false;
                while (placed == false) {
                    System.out.println("");
                    board.printBoard();
                    // Ask Player to choose direction of ship
                    System.out.println("Now Placing: " + s.getName() + " (Length: " + s.getLength() + ")");
                    System.out.println("Pick a Direction for this Ship to face.");
                    System.out.println("Type in the number of your choice. ie. 1 ");
                    System.out.println("1. Horizontal");
                    System.out.println("2. Vertical");
                    choice = game.getInt(1, 2);
                    if (choice == 1) {
                        horizontal = true;
                    } else {
                        horizontal = false;
                    }

                    // Ask Player for coordinates
                    System.out.println("");
                    int[] coordinate = game.getCoordinate(board, "Enter top-left coordinate (ie A5 or C 7): ");
                    System.out.println("");
                    int placeAtRow = coordinate[0];
                    int placeAtCol = coordinate[1];

                    // Try to place ship
                    placed = board.checkAndPlaceShip(s, placeAtRow, placeAtCol, horizontal);
                    if (placed == false) {
                        System.out.println("That placement goes out of bounds or overlaps another ship. Try again.");
                    }
                }
            }
        }
    }

}
