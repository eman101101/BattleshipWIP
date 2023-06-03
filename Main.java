import java.util.Scanner;

public class Main {
    private static final char EMPTY_CELL = '~';
    private static final char SHIP_CELL = 'O';
    private static final char HIT_CELL = 'X';
    private static final char MISS_CELL = 'M';
    private static final int BOARD_SIZE = 10;
    private static char[][] board;
    private static char[][] playerBoard;
    private static char[][] board2;
    private static char[][] playerBoard2;

    public static void main(String[] args) {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        playerBoard = new char[BOARD_SIZE][BOARD_SIZE];
        fillBoard(board);
        fillBoard(playerBoard);

        board2 = new char[BOARD_SIZE][BOARD_SIZE];
        playerBoard2 = new char[BOARD_SIZE][BOARD_SIZE];
        fillBoard(board2);
        fillBoard(playerBoard2);

        try (Scanner scanner = new Scanner(System.in)) {
            // Set up ships
                  int[] shipSizes = new int[]{5, 4, 3, 3, 2};
            String[] shipNames = new String[]{"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};

            // Ship placement loop
            for (int i = 0; i < shipSizes.length; i++) {
                printBoard(board);
                System.out.println("Enter the coordinates of the " + shipNames[i] + " (" + shipSizes[i] + " cells):");
                while (true) {
                    String start = scanner.next();
                    String end = scanner.next();
                    int startRow = start.charAt(0) - 'A';
                    int startCol = Integer.parseInt(start.substring(1)) - 1;
                    int endRow = end.charAt(0) - 'A';
                    int endCol = Integer.parseInt(end.substring(1)) - 1;
            
                    // Ensure the start is always the smaller coordinate
                    if (startRow > endRow || startCol > endCol) {
                        int tempRow = startRow;
                        int tempCol = startCol;
                        startRow = endRow;
                        startCol = endCol;
                        endRow = tempRow;
                        endCol = tempCol;
                    }
            
                    // Input Code
                    if ((Math.abs(endRow - startRow) + 1 == shipSizes[i] && startCol == endCol) || 
                        (Math.abs(endCol - startCol) + 1 == shipSizes[i] && startRow == endRow)) {
                        if (canPlaceShip(startRow, startCol, endRow, endCol)) {
                            for (int j = startRow; j <= endRow; j++) {
                                for (int k = startCol; k <= endCol; k++) {
                                    board[j][k] = SHIP_CELL;
                                }
                            }
                            break;
                        } else {
                            System.out.println("Error! You placed it too close to another one. Try again:");
                        }
                    } else {
                        System.out.println("Error! Wrong length of the " + shipNames[i] + "! Try again:");
                    }
                }
            }
            
            printBoard(board);

            System.out.println("Player 2, it's your turn to place your ships.");
            
            for (int i = 0; i < shipSizes.length; i++) {
                printBoard(board2);
                System.out.println("\nEnter the coordinates of the " + shipNames[i] + " (" + shipSizes[i] + " cells):");
                while (true) {
                    String start = scanner.next();
                    String end = scanner.next();
                    int startRow = start.charAt(0) - 'A';
                    int startCol = Integer.parseInt(start.substring(1)) - 1;
                    int endRow = end.charAt(0) - 'A';
                    int endCol = Integer.parseInt(end.substring(1)) - 1;
            
                    // Ensure the start is always the smaller coordinate
                    if (startRow > endRow || startCol > endCol) {
                        int tempRow = startRow;
                        int tempCol = startCol;
                        startRow = endRow;
                        startCol = endCol;
                        endRow = tempRow;
                        endCol = tempCol;
                    }
            
                   // Valid Placement check
                    if ((Math.abs(endRow - startRow) + 1 == shipSizes[i] && startCol == endCol) || 
                        (Math.abs(endCol - startCol) + 1 == shipSizes[i] && startRow == endRow)) {
                        if (canPlaceShip(startRow, startCol, endRow, endCol)) {
                            for (int j = startRow; j <= endRow; j++) {
                                for (int k = startCol; k <= endCol; k++) {
                                    board2[j][k] = SHIP_CELL;
                                }
                            }
                            break;
                        } else {
                            System.out.println("Error! You placed it too close to another one. Try again:");
                        }
                    } else {
                        System.out.println("Error! Wrong length of the " + shipNames[i] + "! Try again:");
                    }
                }
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        printBoard(board2);
        
        // The game starts!
        System.out.println("The game starts!");

        // Take shots until game ends
        while (true) {
            System.out.println("Player 1's turn to shoot!");
            printBoard(playerBoard);
            takeShot(board2, playerBoard);  // Player 1 shoots at Player 2's board

            System.out.println("Player 2's turn to shoot!");
            printBoard(playerBoard2);
            takeShot(board, playerBoard2);  // Player 2 shoots at Player 1's board
        }
    }

    private static boolean canPlaceShip(int startRow, int startCol, int endRow, int endCol) {
        for (int i = Math.max(0, startRow - 1); i <= Math.min(BOARD_SIZE - 1, endRow + 1); i++) {
            for (int j = Math.max(0, startCol - 1); j <= Math.min(BOARD_SIZE - 1, endCol + 1); j++) {
                if (board[i][j] == SHIP_CELL) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private static void fillBoard(char[][] boardToFill) {
        for (char[] row : boardToFill) {
            java.util.Arrays.fill(row, EMPTY_CELL);
        }
    }

    private static void printBoard(char[][] boardToPrint) {
        System.out.print("  ");
        for (int i = 1; i <= BOARD_SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(boardToPrint[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void printPlayerBoard() {
        System.out.print("  ");
        for (int i = 1; i <= BOARD_SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(playerBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void takeShot(char[][] targetBoard, char[][] playerViewBoard) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Take a shot!");
   
            String shot = scanner.next();
            int shotRow = shot.charAt(0) - 'A';
            int shotCol = Integer.parseInt(shot.substring(1)) - 1;
   
            // Ensure the coordinates are valid
            if (shotRow < 0 || shotRow >= BOARD_SIZE || shotCol < 0 || shotCol >= BOARD_SIZE) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                takeShot(targetBoard, playerViewBoard);
            } else {
                    if (playerViewBoard[shotRow][shotCol] == HIT_CELL) {
                    printPlayerBoard();
                    System.out.println("You hit a ship!");
                    takeShot(targetBoard, playerViewBoard);
                    return;
                } else if (playerViewBoard[shotRow][shotCol] == MISS_CELL) {
                    printPlayerBoard();
                    System.out.println("You Missed!");
                    takeShot(targetBoard, playerViewBoard);
                    return;
                }
                // Update the based on whether it's a hit or a miss
                if (targetBoard[shotRow][shotCol] == SHIP_CELL) {
                    playerViewBoard[shotRow][shotCol] = HIT_CELL;
                    targetBoard[shotRow][shotCol] = HIT_CELL;
   
                    int hitCount = countHitCells();
                    if (hitCount == 17) {
                        printPlayerBoard();
                        System.out.println("You sank the last ship. You won. Congratulations!");
                        System.exit(0); // End the game
                    } else {
                        System.out.println("You hit a ship!");
                    }
                } else {
                    playerViewBoard[shotRow][shotCol] = MISS_CELL;
                    targetBoard[shotRow][shotCol] = MISS_CELL;
                    System.out.println("You missed!");
                }
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    
    private static int countHitCells() {
        int hitCount = 0;
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == HIT_CELL) {
                    hitCount++;
                }
            }
        }
        return hitCount;
    }
}
