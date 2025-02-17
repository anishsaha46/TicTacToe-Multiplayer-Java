package src;

import client.TicTacToeClient;
import network.TicTacToeServer;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Main <server|client> [port]");
            return;
        }

        String mode = args[0];
        int port = 8080; // Default port

        if (args.length > 1) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port number.");
                return;
            }
        }

        if ("server".equalsIgnoreCase(mode)) {
            startServer(port);
        } else if ("client".equalsIgnoreCase(mode)) {
            startClient(port);
        } else {
            System.out.println("Unknown mode. Use 'server' or 'client'.");
        }
    }

    private static void startServer(int port) {
        System.out.println("Starting Tic Tac Toe Server on port " + port);
        new TicTacToeServer(port);
    }

    private static void startClient(int port) {
        System.out.println("Starting Tic Tac Toe Client on port " + port);
        new TicTacToeClient("localhost", port);
    }
}