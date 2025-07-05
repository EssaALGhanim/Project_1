
import game.AI;
import game.Player;
import game.Session;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        loop:
        while (true) {
            try {
                System.out.println("\n=== Tic Tac Toe Game ===");
                System.out.println("1. Play Solo (vs Computer)");
                System.out.println("2. Play Duo (vs Friend)");
                System.out.println("3. Exit");
                System.out.print("Enter your choice (1-3): ");

                int gameMode = input.nextInt();

                if (gameMode < 1 || gameMode > 3) {
                    throw new IllegalArgumentException("Invalid game mode!");
                }


                if (gameMode == 3) {
                    System.out.println("Thanks for playing!");
                    break loop;
                }

                System.out.println("\nSelect number of rounds:");
                System.out.println("1. Single Round");
                System.out.println("2. Best of 3");
                System.out.print("Enter your choice (1-2): ");
                int roundChoice = input.nextInt();

                if (roundChoice != 1 && roundChoice != 2) {
                    throw new IllegalArgumentException("Invalid round choice!");
                }

                int totalRounds;
                if (roundChoice == 1) {
                    totalRounds = 1;
                } else {
                    totalRounds = 3;
                }

                switch (gameMode) {
                    case 1:

                        // Solo mode
                        try {
                            System.out.println("\nSelect difficulty:");
                            System.out.println("1. Easy");
                            System.out.println("2. Hard");
                            System.out.print("Enter difficulty (1-2): ");
                            int difficulty = input.nextInt();
                            input.nextLine(); // Clear buffer

                            if (difficulty != 1 && difficulty != 2) {
                                throw new IllegalArgumentException("Invalid difficulty level!");
                            }

                            System.out.print("Enter your name: ");
                            String playerName = input.nextLine();

                            Player player1 = new Player(playerName, "X");
                            Player player2 = new Player("Computer", "O");

                            playMultipleRounds(player1, player2, "Solo", difficulty, totalRounds);
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input! Please enter a number.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    case 2:

                        // Duo mode
                        try {

                            System.out.print("");
                            input.nextLine();
                            System.out.print("Enter Player 1 name: ");
                            String p1Name = input.nextLine();

                            System.out.print("Enter Player 2 name: ");
                            String p2Name = input.nextLine();

                            Player duoPlayer1 = new Player(p1Name, "X");
                            Player duoPlayer2 = new Player(p2Name, "O");

                            playMultipleRounds(duoPlayer1, duoPlayer2, "Duo", 0, totalRounds);
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                            input.nextLine();
                        }
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                input.nextLine();
            }
        }
        input.close();
    }

    private static void playMultipleRounds(Player player1, Player player2, String gameMode,
                                           int difficulty, int totalRounds) {
        int wins1 = 0;
        int wins2 = 0;

        for (int i = 0; i < totalRounds; i++) {
            System.out.println("\n=== Round " + (i + 1) + " of " + totalRounds + " ===");
            System.out.println("Current Score - " + player1.getName() + ": " + wins1 +
                    " | " + player2.getName() + ": " + wins2);

            Session session = new Session(gameMode, player1, player2);
            AI ai = null;
            if (gameMode.equals("Solo")) {
                ai = new AI(difficulty == 1 ? "Easy" : "Hard", session, player2);
            }

            if (gameMode.equals("Solo")) {
                playSoloGame(session, ai);
            } else {
                playDuoGame(session);
            }

            if (!session.winner.equals("Tie")) {
                if (session.winner.equals(player1.getName())) {
                    wins1++;
                } else {
                    wins2++;
                }
            }

            // if someone won 2 rounds end the game cuz last round doesn't matter
            if (totalRounds == 3 && (wins1 == 2 || wins2 == 2)) {
                break;
            }
        }

        // Announce final winner
        System.out.println("\n=== Final Results ===");
        System.out.println(player1.getName() + ": " + wins1 + " wins");
        System.out.println(player2.getName() + ": " + wins2 + " wins");
        if (wins1 > wins2) {
            System.out.println(player1.getName() + " wins the game!");
        } else if (wins2 > wins1) {
            System.out.println(player2.getName() + " wins the game!");
        } else {
            System.out.println("The game is a tie!");
        }
    }

    private static void playSoloGame(Session session, AI ai) {
        try {
            while (!session.isGameOver(session.getBoard())) {
                System.out.println("\nCurrent board:");
                displayBoard(session.getBoard().getBoard());

                if (session.turn) {
                    System.out.println("\n" + session.player1.getName() + "'s turn (X)");
                    makePlayerMove(session);
                } else {
                    System.out.println("\nComputer's turn (O)");
                    if (ai.getGameMode().equals("Easy")) {
                        ai.makeEasyMove();
                    } else {
                        ai.makeHardMove();
                    }
                }
            }

            displayBoard(session.getBoard().getBoard());
            if (session.winner.equals("Tie")) {
                System.out.println("Round Over - It's a tie!");
            } else {
                System.out.println("Round Over - " + session.winner + " wins!");
            }
        } catch (Exception e) {
            System.out.println("Error during game: " + e.getMessage());
        }
    }

    private static void playDuoGame(Session session) {
        Scanner input = new Scanner(System.in);
        try {
            while (!session.isGameOver(session.getBoard())) {
                System.out.println("\nCurrent board:");
                displayBoard(session.getBoard().getBoard());

                Player currentPlayer = session.turn ? session.player1 : session.player2;
                System.out.println("\n" + currentPlayer.getName() + "'s turn (" + currentPlayer.getSign() + ")");

                makePlayerMove(session);
            }

            displayBoard(session.getBoard().getBoard());
            if (session.winner.equals("Tie")) {
                System.out.println("Round Over - It's a tie!");
            } else {
                System.out.println("Round Over - " + session.winner + " wins!");
            }
        } catch (Exception e) {
            System.out.println("Error during game: " + e.getMessage());
        }
    }

    private static void makePlayerMove(Session session) {
        Scanner input = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Enter row (0-2): ");
                int row = input.nextInt();
                System.out.print("Enter column (0-2): ");
                int col = input.nextInt();

                if (row < 0 || row > 2 || col < 0 || col > 2) {
                    System.out.println("Invalid position! Row and column must be between 0 and 2.");
                    continue;
                }

                if (!session.getBoard().isEmpty(row, col)) {
                    System.out.println("That position is already taken! Try again.");
                    continue;
                }

                session.movePlayer(row, col);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter numbers between 0 and 2.");
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                input.nextLine();
            }
        }
    }

    private static void displayBoard(String[][] board) {
        try {
            System.out.println("-------------");
            for (int i = 0; i < 3; i++) {
                System.out.print("| ");
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == null || board[i][j].equals("*")) {
                        System.out.print(i + "," + j + " ");
                    } else {
                        System.out.print(" " + board[i][j] + "  ");
                    }
                    System.out.print("| ");
                }
                System.out.println("\n-------------");
            }
        } catch (Exception e) {
            System.out.println("Error displaying board: " + e.getMessage());
        }
    }
}