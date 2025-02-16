package network;

import game.GameLogic;
import java.io.*;
import java.net.*;
import java.util.*;

public class TicTacToeServer {
    private ServerSocket serverSocket;
    private final List<ClientHandler> clients = new ArrayList<>();
    private GameLogic game;
    private boolean waitingForPlayer = false;

    public TicTacToeServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            acceptClients();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void acceptClients() {
        new Thread(() -> {
            while (true) { // Continuously accept clients
                try {
                    Socket clientSocket = serverSocket.accept();
                    char playerSymbol = clients.size() % 2 == 0 ? 'X' : 'O';
                    ClientHandler client = new ClientHandler(clientSocket, this, playerSymbol);
                    clients.add(client);
                    client.start();
                    System.out.println("Player " + playerSymbol + " connected.");

                    if (clients.size() == 2) {
                        startGame();
                    } else if (clients.size() == 1 && waitingForPlayer) {
                        client.sendMessage("WAITING_FOR_PLAYER");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private synchronized void startGame() {
        game = new GameLogic(); // Initialize game logic for a new game
        broadcast("START");
        waitingForPlayer = false;
    }
}
