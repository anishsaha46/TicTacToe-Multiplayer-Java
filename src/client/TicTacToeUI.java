package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TicTacToeUI extends Application {
    private TicTacToeClient client;
    private final Button[][] board = new Button[3][3];
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public void setClient(TicTacToeClient client) {
        this.client = client;
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = new Button("-");
                board[i][j].setPrefSize(100, 100);
                int row = i, col = j;
                board[i][j].setOnAction(e -> handleMove(row, col));
                grid.add(board[i][j], j, i);
            }
        }

        Scene scene = new Scene(grid, 300, 300);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Start listening for server messages
        executor.execute(this::listenForServerMessages);
    }

    private void handleMove(int row, int col) {
        if (board[row][col].getText().equals("-")) {  // Prevent duplicate moves
            client.sendMove(row, col);
        }
    }
}