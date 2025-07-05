package game;

import java.util.ArrayList;

public class Board {

    private String sessionID;
    private String[][] board;
    private String gameMode;
    private Player player1;
    private Player player2;


    public Board( Player player1, Player player2, Session session){
        this.board = new String[3][3];
        this.sessionID = session.sessionID;
        this.gameMode = session.gameMode;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void setBoard(int x, int y, String sign) {
        board[x][y] = sign;
    }

    public String[][] getBoard() {
        return board;
    }

    public ArrayList<Integer> getEmptySpaces(){
        ArrayList<Integer> emptySpaces = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (isEmpty(i,j)) {
                    emptySpaces.add(10 * i + j);
                }
            }
        }
        return emptySpaces;
    }

    public boolean isEmpty(int x, int y) {
        if (board[x][y] == null) {
            return true;
        }
        return false;
    }

    public void movePlayer (Player player, int x, int y){
        if (isEmpty(x,y)) {
            if (player.equals(player1)) {
                board[x][y] = "X";
            }
            else {
                board[x][y] = "O";
            }
        }
    }
    public String toString(){
        String result = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!isEmpty(i,j)){
                result += board[i][j] + " ";}
                else{
                    result += "* ";
                }
            }
            result += "\n";
        }
        return result;
    }
}
