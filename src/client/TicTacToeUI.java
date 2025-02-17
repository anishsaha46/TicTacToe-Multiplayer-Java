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

    
}