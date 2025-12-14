import java.util.Scanner;

/**
 * Made by: Arwen Campbell
 * Date: 12/14/2025
 * Game class controls the flow of the game.
 * It handles menus, player setup, ship placement, and turn gameplay.
 */

public class Game {

    // Attributes
    private Scanner kb = new Scanner(System.in);
    private Player player1;
    private Player player2;
    private boolean gameOver = false;
    private String winner;
    private int roundNumber = 0;

    /**
     * Game Set Up:
     * Runs menus, Builds Players, Places Ships, and Begins Game Loop
     */
    public void start() {
        String playerName = MenuPartA();
        // Exit Game
        if (playerName == null) {
            return;
        }
        setUpPlayers(playerName);
        boolean random = MenuPartB();
        player1.placeShips(random);
        // Computer always places randomly
        player2.placeShips(true);
        gameLoop();
    }

    /**
     * Game Loop:
     * Take turns between players until someone wins
     * Display Winner
     * Question 4: Play again?
     */
    private void gameLoop() {
        while (gameOver == false) {
            roundNumber += 1;
            takePlayer1Turn();
            gameOver = isGameOver();
            if (gameOver) {
                break;
            }
            takePlayer2Turn();
            gameOver = isGameOver();
            if (gameOver) {
                break;
            }
        }
        System.out.println("Game Over!");
        System.out.println(winner + " is the Winner!");
        System.out.println("Play again?");
        System.out.println("Type in the number of your choice. ie. 1 ");
        System.out.println("1. Yes");
        System.out.println("2. No");
        int replay = getInt(1, 2);
        if (replay == 1) {
            start();
        }
    }

    /**
     * MenuPartA:
     * Question 1: Start or Close Game
     * Question 2a: Ask for Player Name
     * Question 2b: Confirm Player Name
     * Returns playerName
     */
    private String MenuPartA() {
        boolean confirmName = false;
        String playerName = "";
        int choice;
        // Question 1: Start or Close Game
        System.out.println("Battleship Menu");
        System.out.println("Type in the number of your choice. ie. 1 ");
        System.out.println("1. Start Game");
        System.out.println("2. Close Game");
        choice = getInt(1, 2);
        if (choice == 2) {
            System.out.println("Goodbye!");
            return null;
        }

        // Question 2a: Player Name
        while (confirmName == false) {
            System.out.println("");
            System.out.print("Type in your name: ");
            playerName = kb.nextLine().trim();

            if (playerName.isBlank()) {
                System.out.println("Your name cannot be blank.");
            } else {
                // Question 2b: Confirm Player Name
                System.out.println("");
                System.out.println("Is " + playerName + " correct?");
                System.out.println("Type in the number of your choice. ie. 1 ");
                System.out.println("1. Yes");
                System.out.println("2. No");
                choice = getInt(1, 2);
                if (choice == 1) {
                    confirmName = true;
                }
            }
        }
        return playerName;
    }

    /**
     * MenuPartB:
     * Question 3: Place Ships Manually or Randomly
     * Returns True for Randomly, and False for Manually
     */
    private boolean MenuPartB() {
        int choice;
        // Question 3: Place Ships Manually or Randomly
        System.out.println("");
        System.out.println("How would you like to place your ships?");
        System.out.println("Type in the number of your choice. ie. 1 ");
        System.out.println("1. Randomly");
        System.out.println("2. Manually");
        choice = getInt(1, 2);
        if (choice == 1) {
            return true;
        } else {
            return false;
        }
    }

    // Make Player Objects
    private void setUpPlayers(String playerName) {
        player1 = new Player(playerName, this);
        player2 = new Player("Computer", this);
    }

    /**
     * Player 1 Turn (Human):
     * Shows both boards, Asks for coordinate input,
     * Checks Shot for Validity, Applies Shot
     */
    private void takePlayer1Turn() {
        int checkCol = -1;
        int checkRow = -1;
        printBoards();

        boolean correctCorr = false;
        while (correctCorr == false) {
            int[] coordinate = getCoordinate(player2.getBoard(), "Enter target (ie A5 or C 7): ");
            checkRow = coordinate[0];
            checkCol = coordinate[1];

            correctCorr = player2.getBoard().checkShotAndUpdate(checkRow, checkCol);
            if (correctCorr == false) {
                System.out.println("That target was Invalid. Please try again.");
            }
        }
        // Determine hit/miss
        Ship hitShip = player2.getBoard().getLastShipHit();
        boolean wasHit;
        if (hitShip != null) {
            wasHit = true;
        } else {
            wasHit = false;
        }

        // Show outcome of this Turn
        System.out.println("");
        System.out.println(
                player1.getName() + " targeted: " + player1.getBoard().getRowLabels()[checkRow] + " " + (checkCol + 1));
        turnOutcome(wasHit, hitShip, false);
    }

    /**
     * Player 2 Turn (Computer):
     * Randomly chooses coordinate, repeats until valid coordinate found
     */
    private void takePlayer2Turn() {
        int rowNum;
        int colNum;

        // Chooses a New, unused coordinate
        do {
            rowNum = (int) (Math.random() * player1.getBoard().getSize());
            colNum = (int) (Math.random() * player1.getBoard().getSize());
        } while (player1.getBoard().checkHasShotHere(rowNum, colNum));

        player1.getBoard().markShotTaken(rowNum, colNum);
        player1.getBoard().checkShotAndUpdate(rowNum, colNum);

        Ship hitShip = player1.getBoard().getLastShipHit();
        boolean wasHit;
        if (hitShip != null) {
            wasHit = true;
        } else {
            wasHit = false;
        }

        System.out.println("");
        System.out.println("Computer targeted: " + player1.getBoard().getRowLabels()[rowNum] + " " + (colNum + 1));

        turnOutcome(wasHit, hitShip, true);
    }

    // Print outcome of the last turn.
    private void turnOutcome(boolean wasHit, Ship hitShip, boolean isComputer) {
        // Miss
        if (wasHit == false) {
            System.out.println("And it Missed!");
            return;
        }
        // Hit
        System.out.println("And it Hit!");
        // Sunk Ship
        if (hitShip != null && hitShip.isSunk()) {
            if (isComputer) {
                System.out.println("Computer sunk your " + hitShip.getName() + "!");
            } else {
                System.out.println("You sunk their " + hitShip.getName() + "!");
            }
        }
    }

    // Check if a player has had all ships sunk
    private boolean isGameOver() {
        if (player1.getBoard().allShipsSunk()) {
            winner = player2.getName();
            return true;
        } else if (player2.getBoard().allShipsSunk()) {
            winner = player1.getName();
            return true;
        } else {
            return false;
        }
    }

    // Print Boards
    private void printBoards() {
        System.out.println("");
        System.out.println("Round: " + roundNumber);
        System.out.println(player2.getName());
        player2.getBoard().printHiddenBoard();
        System.out.println(player1.getName());
        player1.getBoard().printBoard();
    }

    // Check User Input (int)
    public int getInt(int min, int max) {
        while (true) {
            String input = kb.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException e) {
            }
            System.out.print("Invalid input. Enter a number between " + min + " and " + max + ": ");
        }
    }

    // Check User Input (Coordinates)
    public int[] getCoordinate(Board board, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = kb.nextLine().trim();

            // Check at least 2 characters
            if (input.length() < 2) {
                System.out.print("Invalid input. Enter a letter and number (ie A5 or C 7): ");
                continue;
            }

            // Get Row Letter
            char rowChar = Character.toUpperCase(input.charAt(0));
            if (Character.isLetter(rowChar) == false) {
                System.out.print("Invalid row letter. Try again: ");
                continue;
            }

            // Check row is in board rows
            int row = -1;
            char[] rows = board.getRowLabels();
            for (int i = 0; i < rows.length; i++) {
                if (rowChar == rows[i]) {
                    row = i;
                    break;
                }
            }
            if (row == -1) {
                System.out.print("Row out of range. Try again: ");
                continue;
            }

            // Get Column String
            String colString = input.substring(1).trim();

            // Check for Empty Column Number
            if (colString.isEmpty()) {
                System.out.print("Missing column number. Try again: ");
                continue;
            }

            // Parse Column Number
            int col;
            try {
                col = Integer.parseInt(colString) - 1;
            } catch (NumberFormatException e) {
                System.out.print("Invalid column number. Try again: ");
                continue;
            }

            // Check Column is in Bounds
            if (col < 0 || col >= board.getSize()) {
                System.out.print("Column out of range. Try again: ");
                continue;
            }
            return new int[] { row, col };
        }
    }

    public static void main(String[] args) {
        Game game1 = new Game();
        game1.start();
        game1.kb.close();
    }
}
