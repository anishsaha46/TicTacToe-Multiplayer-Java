package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TicTacToeClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private char playerSymbol;
    private boolean running = true;

    
    public TicTacToeClient(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String welcomeMessage = in.readLine();
            if (welcomeMessage != null && welcomeMessage.startsWith("WELCOME")) {
                playerSymbol = welcomeMessage.charAt(8);
                System.out.println("✅ You are player " + playerSymbol);
            } else {
                System.out.println("⚠️ Server did not send a welcome message.");
                closeConnection();
                return;
            }

            new Thread(this::listenForMessages).start();
            handleUserInput();
        } catch (IOException e) {
            System.out.println("❌ Unable to connect to the server. Make sure the server is running.");
            closeConnection();
        }
    }

    public void sendMove(int row, int col) {
        out.println("MOVE " + row + " " + col);
    }
}
