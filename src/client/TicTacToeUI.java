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

    private void listenForServerMessages() {
        try {
            String message;
            while ((message = client.getIn().readLine()) != null) {
                handleServerMessage(message);
            }
        } catch (IOException e) {
            System.out.println("❌ Connection lost. Exiting...");
            Platform.runLater(() -> disableBoard("Connection lost!"));
        } finally {
            executor.shutdownNow();
        }
    }

    private void handleServerMessage(String message) {
        Platform.runLater(() -> {
            String[] parts = message.split(" ");
            switch (parts[0]) {
                case "MOVE":
                    int row = Integer.parseInt(parts[1]);
                    int col = Integer.parseInt(parts[2]);
                    String symbol = parts[3];
                    board[row][col].setText(symbol);
                    board[row][col].setDisable(true);
                    break;
                case "WIN":
                    disableBoard("🏆 Player " + parts[1] + " wins!");
                    break;
                case "DRAW":
                    disableBoard("🤝 The game is a draw.");
                    break;
                case "START":
                    resetBoard();
                    break;
                case "WAITING_FOR_PLAYER":
                    disableBoard("⌛ Waiting for another player...");
                    break;
                case "MOVE_FAILED":
                    System.out.println("❌ Invalid move! Try again.");
                    break;
                case "DISCONNECTED":
                    disableBoard("❌ Opponent disconnected. Waiting for a new player...");
                    break;
                default:
                    System.out.println("🔄 Server: " + message);
                    break;
            }
        });
    }
}