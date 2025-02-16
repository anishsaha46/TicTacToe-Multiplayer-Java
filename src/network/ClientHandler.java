package network;

import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
    private Socket socket;
    private TicTacToeServer server;
    private PrintWriter out;
    private BufferedReader in;
    private char playerSymbol;

    public ClientHandler(Socket socket, TicTacToeServer server, char playerSymbol) {
        this.socket = socket;
        this.server = server;
        this.playerSymbol = playerSymbol;
    }

    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("WELCOME " + playerSymbol);

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received: " + message);
                processClientMessage(message);
            }
        } catch (IOException e) {
            System.out.println("Player " + playerSymbol + " disconnected.");
        } finally {
            cleanup();
        }
    }

    private void processClientMessage(String message) {
        if (message.startsWith("MOVE")) {
            handleMove(message);
        } else if (message.equals("DISCONNECT")) {
            server.removeClient(this);
        } else {
            sendMessage("UNKNOWN_COMMAND");
        }
    }

}
