import java.util.*;

public class Game {
    // Constants:
    private static final String DIVIDERL = "("; // Left divider for game board
    private static final String DIVIDERR = ")"; // Right divider for game board
    private static final String RED = "\u001B[31m◉\u001B[0m"; // Red token
    private static final String YELLOW = "\u001B[33m◉\u001B[0m"; // Yellow token
    // Variables:
    private static String[][] board = new String[6][7]; // Each spot on the board, first index is the y which goes 0-5 top to bottom, second index is the x which goes 0-6 left to right
    private static boolean isPlayer1 = true;  // Whether it's the first players turn or not
    private static Scanner scanner = new Scanner(System.in); // The system scanner
    private static int input; // The input from the user, the x of the token las dropped
    private static int y; // The y of the token that was last dropped

    /**
     * The main method runs the initialize method.
     * @param args The in arguments
     *
     * @author Kiefer Menard
     */
    public static void main(String[] args) {
        initialize();
    }

    /**
     * The initialize method sets up the game board and welcomes the user.
     *
     * @author Kiefer Menard
     */
    public static void initialize() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = DIVIDERL + " " + DIVIDERR;
            }
        }

        System.out.println("Welcome to connect 4, on your turn type the column you wish to play in (1-7) and press enter.\n");

        play();
    }

    /**
     * The play method handles the game flow.
     *
     * @author Kiefer Menard
     */
    private static void play() {
        // Thread so the rest of the program doesn't stop (Useful for networking)
        new Thread (() -> {
            while (true) {
                // Prints the board
                System.out.println(" 1   2   3   4   5   6   7");

                for (String[] spots : board) {
                    for (String spot : spots) {
                        System.out.print(spot + " ");
                    }
                    System.out.println();
                }

                // Request and handle input
                requestInput();
                handleInput();
                // If someone won, stop the program
                if (checkWin()) {
                    return;
                }

                // Switch the player
                isPlayer1 = !isPlayer1;
            }
        }).start();
    }

    /**
     * The requestInput method asks the player for input and checks if it's a number.
     *
     * @author Kiefer Menard
     */
    private static void requestInput() {
        // Prints out the player
        System.out.print((isPlayer1 ? RED : YELLOW) + " Player " + (isPlayer1 ? 1 : 2) + ": ");
        // Check to make sure the input was a number
        try {
            input = scanner.nextInt();
            scanner.nextLine();
        } catch (RuntimeException e) {
            // Ask for a new number
            scanner.nextLine();
            System.out.println("Invalid input, must be 1-7, try again.");

            requestInput();
        }
    }

    /**
     * The handleInput method checks if the input is between 1-7, adds a token to the selected column, and handles if it's full.
     *
     * @author Kiefer Menard
     */
    private static void handleInput() {
        input -= 1; // Decrement the input since arrays start at 0

        // Make sure the number is between 0-6
        if (!(0 <= input && input < 7)) {
            // Ask for a new number if it's not
            System.out.println("Invalid input, must be 1-7, try again.");
            requestInput();
            handleInput();
            return;
        }

        // Add a token to the board if there's room
        for (y = 5; y >= 0; y--) {
            if (board[y][input].equals(DIVIDERL + " " + DIVIDERR)) {
                board[y][input] = DIVIDERL + (isPlayer1 ? RED : YELLOW) + DIVIDERR;

                return;
            }
        }

        // If there's no room, ask for a new input
        System.out.println("Column is full, try again.");
        requestInput();
        handleInput();
    }

    /**
     * The checkWin method checks if someone has won.
     *
     * @return Returns whether someone has won.
     *
     * @author Kiefer Menard
     */
    private static boolean checkWin() {
        String token = (isPlayer1 ? RED : YELLOW); // The proper token string based off the player
        int count = 0; // The amount of same tokens next to each other
        ArrayList<ArrayList<Integer>> masterArray; // The array containing the x and y arrays
        ArrayList<Integer> xArray; // An array containing x coordinates
        ArrayList<Integer> yArray; // An array containing x coordinates

        // Horizontal win
        // Look 3 on each side of the last placed token.
        for (int i = Math.max(0, input - 3); i <= Math.min(6, input + 3); i++) {
            // If the spot has the same color token, increment count
            if (board[y][i].equals(DIVIDERL + token + DIVIDERR)) {
                count++;

                // If there's 4 in a row, highlight them and print the winning game board
                if (count == 4) {
                    board[y][i - 3] = colorText();
                    board[y][i - 2] = colorText();
                    board[y][i - 1] = colorText();
                    board[y][i] = colorText();

                    printWin(true);

                    return true;
                }
            }
            // Otherwise reset it
            else {
                count = 0;
            }
        }

        // Vertical win
        count = 0; // Reset count

        // Look 3 on the top and bottom of the last placed token.
        for (int i = Math.max(0, y - 3); i <= Math.min(5, y + 3); i++) {
            // If the spot has the same color token, increment count
            if (board[i][input].equals(DIVIDERL + token + DIVIDERR)) {
                count++;

                // If there's 4 in a row, highlight them and print the winning game board
                if (count == 4) {
                    board[i - 3][input] = colorText();
                    board[i - 2][input] = colorText();
                    board[i - 1][input] = colorText();
                    board[i][input] = colorText();

                    printWin(false);

                    return true;
                }
            }
            // Otherwise reset it
            else {
                count = 0;
            }
        }

        // Bottom-left to top-right diagonal win
        count = 0; // Reset the count
        masterArray = diagonal("BLTR"); // Get the diagonal spots
        xArray = masterArray.get(0); // X coordinates
        yArray = masterArray.get(1); // Y coordinates

        // Look 3 to each side diagonally of the last placed token.
        for (int i = 0; i < xArray.size(); i++) {
            // If the spot has the same color token, increment count
            if (board[yArray.get(i)][xArray.get(i)].equals(DIVIDERL + token + DIVIDERR)) {
                count++;

                // If there's 4 in a row, highlight them and print the winning game board
                if (count == 4) {
                    board[yArray.get(i - 3)][xArray.get(i - 3)] = colorText();
                    board[yArray.get(i - 2)][xArray.get(i - 2)] = colorText();
                    board[yArray.get(i - 1)][xArray.get(i - 1)] = colorText();
                    board[yArray.get(i)][xArray.get(i)] = colorText();

                    printWin(false);

                    return true;
                }
            }
            // Otherwise reset it
            else {
                count = 0;
            }
        }

        // Top-left to bottom-right diagonal win
        count = 0; // Reset the count
        masterArray = diagonal("TLBR"); // Get the diagonal spots
        xArray = masterArray.get(0); // X coordinates
        yArray = masterArray.get(1); // Y coordinates

        // Look 3 to each side diagonally of the last placed token.
        for (int i = 0; i < xArray.size(); i++) {
            // If the spot has the same color token, increment count
            if (board[yArray.get(i)][xArray.get(i)].equals(DIVIDERL + token + DIVIDERR)) {
                count++;

                // If there's 4 in a row, highlight them and print the winning game board
                if (count == 4) {
                    board[yArray.get(i - 3)][xArray.get(i - 3)] = colorText();
                    board[yArray.get(i - 2)][xArray.get(i - 2)] = colorText();
                    board[yArray.get(i - 1)][xArray.get(i - 1)] = colorText();
                    board[yArray.get(i)][xArray.get(i)] = colorText();

                    printWin(false);

                    return true;
                }
            }
            // Otherwise reset it
            else {
                count = 0;
            }
        }

        return false;
    }

    /**
     * The diagonal method gets the spots in a diagonal of the token based on the direction specified.
     *
     * @param direction The direction that the diagonal should go in.
     * @return The x and y coordinates of the spots.
     *
     * @author Kiefer Menard
     */
    public static ArrayList<ArrayList<Integer>> diagonal(String direction) {
        ArrayList<ArrayList<Integer>> masterArray = new ArrayList<>(); // Contains the x and y arrays, so they can be returned together
        ArrayList<Integer> xArray = new ArrayList<>(); // The x coordinates of the spots
        ArrayList<Integer> yArray = new ArrayList<>(); // The y coordinates of the spots
        int tempX = input; // A temporary variable to store the x
        int tempY = y; // A temporary variable to store the y

        // Loop backward through the array 3 times or until a border, will need to be in reverse order later
        for (int i = 0; i < 3; i++) {
            tempX--; // Decrement x

            // Check if x has gone out of bounds
            if (tempX < 0) {
                break;
            }

            // Either increment or decrement y based on the direction, and check if it's gone out of bounds
            if (direction.equals("BLTR")) {
                tempY++; // Increment y

                // Check for out of bounds
                if (tempY > 5) {
                    break;
                }
            } else {
                tempY--; // Decrement y

                // Check for out of bounds
                if (tempY < 0) {
                    break;
                }
            }

            // Add the value to the array after it's passed the checks
            xArray.add(tempX);
            yArray.add(tempY);
        }

        // Reverse the arrays so they are the correct orders
        Collections.reverse(xArray);
        Collections.reverse(yArray);

        // Reset the temp vars
        tempX = input;
        tempY = y;

        // Add the current spot to the array
        xArray.add(tempX);
        yArray.add(tempY);

        // Loop forward through the array 3 times or until a border
        for (int i = 0; i < 3; i++) {
            tempX++; // Increment x

            // Check if x has gone out of bounds
            if (tempX > 6) {
                break;
            }

            // Either decrement or increment y based on the direction, and check if it's gone out of bounds
            if (direction.equals("BLTR")) {
                tempY--; // Decrement y

                // Check for out of bounds
                if (tempY < 0) {
                    break;
                }
            } else {
                tempY++; // Increment y

                // Check for out of bounds
                if (tempY > 5) {
                    break;
                }
            }

            // Add the value to the array after it's passed the checks
            xArray.add(tempX);
            yArray.add(tempY);
        }

        // Add the arrays to the master array, so they can both be returned
        masterArray.add(xArray);
        masterArray.add(yArray);
        return masterArray;
    }

    /**
     * The printWin method prints out the winning game board + the winner.
     *
     * @param horizontal Whether the winning move was horizontal or not
     *
     * @author Kiefer Menard
     */
    private static void printWin(boolean horizontal) {
        // Prints the board
        System.out.println(" 1   2   3   4   5   6   7");

        for (String[] spots : board) {
            for (String spot : spots) {
                // Change whether the color goes before or after the space depending on the direction of the win
                if (horizontal) System.out.print(spot + " \u001B[0m");
                else System.out.print(spot + "\u001B[0m ");
            }
            System.out.println();
        }

        // Tell which player won
        System.out.println("\nPlayer " + (isPlayer1 ? 1: 2) + " wins!");
    }

    /**
     * The colorText method adds a green highlight to the background of the spot.
     *
     * @return The String with the highlighted spot.
     *
     * @author Kiefer Menard
     */
    private static String colorText() {
        return "\u001B[42m" + DIVIDERL + (isPlayer1 ? "\u001B[31m": "\u001B[33m") + "◉\u001B[0m\u001B[42m" +DIVIDERR;
    }
}
