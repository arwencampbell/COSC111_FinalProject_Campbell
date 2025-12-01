import java.util.Scanner;

/**
 * Game class controls the flow of the game.
 * It handles menus, player setup, ship placement, and turn gameplay.
 */

public class Game {

    // Attributes
    private Scanner kb = new Scanner(System.in);
    private Player player1 = new Player(null, 0, 0);
    private Player player2 = new Player(null, 0, 0);
    private boolean isGameOver = false;
    private String winner;

    /**
     * MenuPartA:
     * Question 1: Start or Close Game
     * Question 2a: Ask for Player Name
     * Question 2b: Confirm Player Name
     * Returns playerName
     */
    private String MenuPartA() {
        boolean validInput1 = false;
        boolean validInput2 = false;
        String playerName = "";
        int choice;
        // Question 1: Start or Close Game
        while (validInput1 == false) {
            System.out.println("Battleship Menu");
            System.out.println("");
            System.out.println("Type in the number of your choice. ie. 1 ");
            System.out.println("1. Start Game");
            System.out.println("2. Close Game");
            choice = kb.nextInt();
            if (choice == 1) {
                validInput1 = true;
            } else if (choice == 2) {
                validInput1 = true;
                // TODO: add close program
            } else {
                System.out.println("That input is Invalid. Please try again.");
            }
        }
        // Question 2a: Player Name
        validInput1 = false;
        while (validInput1 == false) {
            validInput2 = false;
            System.out.println("");
            System.out.print("Type in your name: ");
            playerName = kb.next();
            // Not allowing blank input
            if (playerName.isBlank()) {
                System.out.println("That input is Invalid. Please try again.");
            } else {
                // Question 2b: Confirm Player Name
                while (validInput2 == false) {
                    System.out.println("");
                    System.out.println("Is " + playerName + " correct?");
                    System.out.println("Type in the number of your choice. ie. 1 ");
                    System.out.println("1. Yes");
                    System.out.println("2. No");
                    choice = kb.nextInt();
                    if (choice == 1) {
                        validInput1 = true;
                        validInput2 = true;
                    } else if (choice == 2) {
                        validInput2 = true;
                    } else {
                        System.out.println("That input is Invalid. Please try again.");
                    }
                }
            }
        }
        return playerName;
    }

    /**
     * MenuPartB:
     * Question 3: size of Board
     * Returns size of Board
     */
    private int MenuPartB() {
        boolean validInput1 = false;
        int size = 0;
        int choice;
        // Question 3: Size of Board
        while (validInput1 == false) {
            System.out.println("");
            System.out.println("What size board would you like to play on?");
            System.out.println("Recommended: 3. Extra-Large");
            // TODO: Get all sizes to work then determine what reccomended size should be
            System.out.println("Type in the number of your choice. ie. 3");
            System.out.println("1. Normal");
            System.out.println("2. Large");
            System.out.println("3. Extra-Large");
            choice = kb.nextInt();
            if (choice == 1) {
                validInput1 = true;
                size = 6;
            } else if (choice == 2) {
                size = 8;
                validInput1 = true;
            } else if (choice == 3) {
                size = 10;
                validInput1 = true;
            } else {
                System.out.println("That input is Invalid. Please try again.");
            }
        }
        return size;
    }

    // Make Player Objects
    private void setUpPlayers(String playerName, int size) {
        int numShips = 0;
        // Set number of ships based on size of board
        // TODO: Get all sizes to work and figure out how many ships for each size is
        // ideal
        // TODO: setup a way to get specific types of ships based on size
        if (size == 5) {
            numShips = 3;
        } else if (size == 6) {
            numShips = 4;
        } else if (size == 8) {
            numShips = 5;
        }
        player1 = new Player(playerName, size, numShips);
        player2 = new Player("Computer", size, numShips);
    }

    /**
     * MenuPartC:
     * Question 4: Place Ships Manually or Randomly
     * Returns True for Randomly, and False for Manually
     */
    private boolean MenuPartC() {
        boolean validInput1 = false;
        int choice;
        // Question 4: Place Ships Manually or Randomly
        while (validInput1 == false) {
            System.out.println("");
            System.out.println("How would you like to place your ships?");
            System.out.println("Type in the number of your choice. ie. 1 ");
            System.out.println("1. Randomly");
            System.out.println("2. Manually");
            choice = kb.nextInt();
            if (choice == 1) {
                validInput1 = true;
                return true;
            } else if (choice == 2) {
                validInput1 = true;
                return false;
            } else {
                System.out.println("That input is Invalid. Please try again.");
            }
        }
        return false;
    }

    /**
     * Game Set Up:
     * Runs menus, Builds Players, Places Ships, and Begins Game Loop
     */
    public void start() {
        String playerName = MenuPartA();
        int size = MenuPartB();
        setUpPlayers(playerName, size);
        boolean random = MenuPartC();
        player1.placeShips(random);
        // Computer always places randomly
        player2.placeShips(true);
        gameLoop();
    }

    /**
     * Game Loop:
     * Take turns between players until someone wins
     */
    private void gameLoop() {
        while (isGameOver == false) {
            takePlayer1Turn();
            isGameOver = isGameOver();
            if (isGameOver) {
                break;
            }
            takePlayer2Turn();
            isGameOver = isGameOver();
            if (isGameOver) {
                break;
            }
        }
        System.out.println("Game Over!");
        System.out.println(winner + " is the Winner!");
    }

    /**
     * Player 1 Turn (Human):
     * Shows both boards, Asks for coordinate input,
     * Checks Shot for Validity, Applies Shot
     */
    private void takePlayer1Turn() {
        // Print computer board
        // TODO: make this board hidden (disabled for testing)
        System.out.println(player2.getName());
        player2.getBoard().printBoard();
        // Print player board
        System.out.println(player1.getName());
        player1.getBoard().printBoard();
        System.out.println("");

        boolean correctCorr = false;
        while (correctCorr == false) {
            System.out.println("What corrdinates do you want to target? ie. A 2, E 5 etc.");
            char charRow = kb.next().charAt(0);
            int checkCol = kb.nextInt() - 1;
            int checkRow = -1;
            // Change letter to row number
            for (int r = 0; r < player2.getBoard().getSize(); r++) {
                if (charRow == player2.getBoard().getRowLabels()[r]) {
                    checkRow = r;
                }
            }
            correctCorr = player2.getBoard().checkShotAndUpdate(checkRow, checkCol);
            if (correctCorr == false) {
                System.out.println("That is Invalid. Please try again.");
            }
        }

        // Redisplay updated boards
        System.out.println(player2.getName());
        player2.getBoard().printBoard();
        System.out.println(player1.getName());
        player1.getBoard().printBoard();
        System.out.println("");

        turnOutcome(player1);
    }

    /**
     * Player 2 Turn (Computer):
     * Randomly chooses coordinate, repeats until valid coordinate found
     */
    private void takePlayer2Turn() {
        boolean correctCorr = false;
        int rowNum;
        char row = 'z';
        int col = -1;
        while (correctCorr == false) {
            rowNum = (int) (Math.random() * player1.getBoard().getSize());
            row = player1.getBoard().getRowLabels()[rowNum];
            col = (int) (Math.random() * player1.getBoard().getSize());
            correctCorr = player1.getBoard().checkShotAndUpdate(rowNum, col);
        }
        System.out.println("");
        System.out.println("Computer targeted: " + row + " " + col);

        // TODO: Consider displaying computer turn differently
        turnOutcome(player2);
    }

    // Print outcome of the last turn.
    // TODO: Make turnOutcome work
    private void turnOutcome(Player lastPlayer) {
        boolean hit = true;
        if (hit == true) {
            if (lastPlayer.getBoard().getCruiser().isSunk()) {
                System.out.println("And sunk your " + lastPlayer.getBoard().getCruiser().getName() + "!");
            }
            System.out.println("And it Hit!");
        } else {
            System.out.println("And it Missed!");
        }
    }

    // Check if a player has had all ships sunk
    private boolean isGameOver() {
        if (player1.getBoard().allShipsSunk()) {
            winner = "Player 2";
            return true;
        } else if (player2.getBoard().allShipsSunk()) {
            winner = "Player 1";
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        Game game1 = new Game();
        game1.start();
        game1.kb.close();
    }
}
