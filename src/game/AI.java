package game;

import java.util.ArrayList;

public class AI {

    private String level;
    private String gameMode;
    private Board board;
    private Session session;
    private Player player;


    public AI(String gameMode, Session session, Player player) {
        this.player = player;
        this.gameMode = gameMode;
        this.session = session;
        this.board = session.getBoard();
    }

    public String getGameMode() {
        return gameMode;
    }

    public void makeEasyMove() {
        ArrayList<Integer> emptySpaces = board.getEmptySpaces();
        int randomIndex = (int) (Math.random() * emptySpaces.size());
        session.movePlayer(emptySpaces.get(randomIndex) / 10, emptySpaces.get(randomIndex) % 10);
    }

    public int checkPotentialWin(Player player) {
        ArrayList<Integer> emptySpaces = board.getEmptySpaces();

        // check potential wins for each empty space
        for (int i = 0; i < emptySpaces.size(); i++) {
            int row = emptySpaces.get(i) / 10;
            int col = emptySpaces.get(i) % 10;
            int check = 0;
            // check row win
            for (int y = 0; y < 3; y++) {

                if (y != col) {
                    if (board.getBoard()[row][y] != null && board.getBoard()[row][y].equals(player.getSign())) {
                        check++;
                    }
                }
                if (check == 2) {
                    return emptySpaces.get(i);
                }
            }
            check = 0;


            // check column win
            for (int x = 0; x < 3; x++) {

                if (x != row) {
                    if (board.getBoard()[x][col] != null && board.getBoard()[x][col].equals(player.getSign())) {
                        check++;
                    }
                }
                if (check == 2) {
                    return emptySpaces.get(i);
                }
            }
            check = 0;

            // check corners wins (0,0) (0,2) (2,0) (2,2)
            if ((row == 0 || row == 2) && (col == 0 || col == 2)) {
                if (board.getBoard()[1][1] != null && board.getBoard()[1][1].equals(player.getSign())) {
                    check++;
                }
                if (row == 2) {
                    row = 0;
                } else {
                    row = 2;
                }

                if (col == 2) {
                    col = 0;
                } else {
                    col = 2;
                }
                if (board.getBoard()[row][col] != null && board.getBoard()[row][col].equals(player.getSign())) {
                    check++;
                }
                if (check == 2) {
                    return emptySpaces.get(i);
                }
            }

            // check midlle win, aka (1,1) win

            if (row == 1 && col == 1) {
                if (board.getBoard()[0][0] != null && board.getBoard()[2][2] != null && board.getBoard()[0][0].equals(player.getSign()) && board.getBoard()[2][2].equals(player.getSign())) {
                    return 11;
                }
                if (board.getBoard()[0][2] != null && board.getBoard()[2][0] != null && board.getBoard()[0][2].equals(player.getSign()) && board.getBoard()[2][0].equals(player.getSign())) {
                    return 11;
                }

            }


        }
        // -1 is a sign for no wins found
        return -1;
    }

    public void makeHardMove() {

        // this will check AI's chances of winning
        int checkWin = checkPotentialWin(player);
        if (checkWin != -1) {
            System.out.println("win chance at: " + checkWin);
            session.movePlayer(checkWin / 10, checkWin % 10);
            return;
        }

        // this code will check the other player's obj and will check his chances of winning
        int checkLost = checkPotentialWin(player.equals(session.player1) ? session.player2 : session.player1);
        if (checkLost != -1) {
            System.out.println("lost chance at: " + checkLost);
            session.movePlayer(checkLost / 10, checkLost % 10);
            return;
        }
        // if no wining chance for both players play anywhere
        makeEasyMove();
    }


}
