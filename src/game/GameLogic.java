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

    public boolean makeMove(int row, int col){
        if(board[i][j]=='-' && !gameWon){
            board[row][col] = currentPlayer;
            gameWon = checkWin();
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            return true;
        }
        return false;
    }
    public boolean checkWin() {
        return checkRows() || checkColumns() || checkDiagonals();
    }

    private boolean checkRows() {
        for(int i=0;i<3;i++){
            if(board[i][0] != '-' && board[i][0] == board[i][1] &&  board[i][1] == board[i][2]){
                return true;
            }
        }
        return false;
    }

    
}
