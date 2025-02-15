package game;

public class GameLogic {
    private char[][] board;
    private char currentPlayer;
    private boolean gameWon;

    public GameLogic(){
        board = new char[3][3];
        resetBoard();
    }

    public void resetBoard(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                board[i][j]='-';
            }
        }
        currentPlayer = 'X';
        gameWon = false;
    }
}
