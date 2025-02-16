package network;

import game.GameLogic;
import java.io.*;
import java.net.*;
import java.util.*;

public class TicTacToeServer {
    private ServerSocket serverSocket;
    private final List<ClientHandler> clients = new ArrayList<>();
    private GameLogic game;

    public TicTacToeServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            acceptClients();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
