package game;

public class GameLogic {
    private char[][] board;
    private char currentPlayer;
    private boolean gameWon;

    public GameLogic() {
        board = new char[3][3];
        resetBoard();
    }

    public void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
        currentPlayer = 'X';
        gameWon = false;
    }

    public boolean makeMove(int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            return false; // Invalid coordinates
        }
        if (board[row][col] == '-' && !gameWon) {
            board[row][col] = currentPlayer;
            gameWon = checkWin();
            if (!gameWon && isDraw()) {
                gameWon = true; // Game is a draw
            }
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            return true;
        }
        return false;
    }

    public boolean checkWin() {
        return checkRows() || checkColumns() || checkDiagonals();
    }

    private boolean checkRows() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != '-' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns() {
        for (int j = 0; j < 3; j++) {
            if (board[0][j] != '-' && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals() {
        return (board[0][0] != '-' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) ||
               (board[0][2] != '-' && board[0][2] == board[1][1] && board[1][1] == board[2][0]);
    }

    public boolean isDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false; // Board is not full
                }
            }
        }
        return true; // Board is full and no one has won
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public char[][] getBoard() {
        char[][] copy = new char[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, 3);
        }
        return copy;
    }

    public void resetGame() {
        resetBoard();
        gameWon = false;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public boolean isGameDrawn() {
        return isDraw() && gameWon;
    }
}