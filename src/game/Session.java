package game;

import java.util.ArrayList;

public class Session {

    public String sessionID;
    public Board board;
    public Player player1;
    public Player player2;
    public String gameMode;
    public String winner;
    public boolean turn;


    public Session(String gameMode, Player player1, Player player2){
        sessionID = Math.random() * 1000 + "";
        this.gameMode = gameMode;
        this.player1 = player1;
        this.player2 = player2;
        this.board = new Board( player1, player2, this);
        this.turn = true;
    }

    public Board getBoard() {
        return board;
    }

    public void movePlayer(int x, int y){
        if (turn) {
            board.movePlayer(player1, x, y);
            turn = false;
        }
        else {
            board.movePlayer(player2, x, y);
            turn = true;
        }
    }

    public boolean isGameOver(Board board){
        if (checkWin(board)) {
            return true;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.isEmpty(i,j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkWin(Board board){

        ArrayList<Integer> emptySpaces = board.getEmptySpaces();

        // Remove nulls
        for (int i = 0; i < emptySpaces.size(); i++) {
            int row = emptySpaces.get(i) / 10;
            int col = emptySpaces.get(i) % 10;
            board.setBoard(row, col, "*");
        }



           if (board.getBoard()[0][0].equals("X") && board.getBoard()[0][1].equals("X") && board.getBoard()[0][2].equals("X")
                   || board.getBoard()[1][0].equals("X") && board.getBoard()[1][1].equals("X") && board.getBoard()[1][2].equals("X")
                   || board.getBoard()[2][0].equals("X") && board.getBoard()[2][1].equals("X") && board.getBoard()[2][2].equals("X")
                   || board.getBoard()[0][0].equals("X") && board.getBoard()[1][0].equals("X") && board.getBoard()[2][0].equals("X")
                   || board.getBoard()[0][1].equals("X") && board.getBoard()[1][1].equals("X") && board.getBoard()[2][1].equals("X")
                   || board.getBoard()[0][2].equals("X") && board.getBoard()[1][2].equals("X") && board.getBoard()[2][2].equals("X")
                   || board.getBoard()[0][0].equals("X") && board.getBoard()[1][1].equals("X") && board.getBoard()[2][2].equals("X")
                   || board.getBoard()[0][2].equals("X") && board.getBoard()[1][1].equals("X") && board.getBoard()[2][0].equals("X")) {
               winner = player1.getName();
               return true;
           } else if (board.getBoard()[0][0].equals("O") && board.getBoard()[0][1].equals("O") && board.getBoard()[0][2].equals("O")
                   || board.getBoard()[1][0].equals("O") && board.getBoard()[1][1].equals("O") && board.getBoard()[1][2].equals("O")
                   || board.getBoard()[2][0].equals("O") && board.getBoard()[2][1].equals("O") && board.getBoard()[2][2].equals("O")
                   || board.getBoard()[0][0].equals("O") && board.getBoard()[1][0].equals("O") && board.getBoard()[2][0].equals("O")
                   || board.getBoard()[0][1].equals("O") && board.getBoard()[1][1].equals("O") && board.getBoard()[2][1].equals("O")
                   || board.getBoard()[0][2].equals("O") && board.getBoard()[1][2].equals("O") && board.getBoard()[2][2].equals("O")
                   || board.getBoard()[0][0].equals("O") && board.getBoard()[1][1].equals("O") && board.getBoard()[2][2].equals("O")
                   || board.getBoard()[0][2].equals("O") && board.getBoard()[1][1].equals("O") && board.getBoard()[2][0].equals("O")) {
               winner = player2.getName();
               return true;
           } else {
               winner = "Tie";
           }


        for (int i = 0; i < emptySpaces.size(); i++) {
            int row = emptySpaces.get(i) / 10;
            int col = emptySpaces.get(i) % 10;
            board.setBoard(row, col, null);
        }
        return false;
    }

    public String toString(){
        return board.toString();
    }


}
