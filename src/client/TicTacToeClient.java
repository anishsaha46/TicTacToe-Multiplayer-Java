package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TicTacToeClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean running = true;

    public TicTacToeClient(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String welcomeMessage = in.readLine();
            if (welcomeMessage != null && welcomeMessage.startsWith("WELCOME")) {
                char playerSymbol = welcomeMessage.charAt(8);
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

    private void listenForMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null && running) {
                handleServerMessage(message);
            }
        } catch (IOException e) {
            System.out.println("❌ Connection lost. Exiting...");
        } finally {
            closeConnection();
        }
    }

    private void handleServerMessage(String message) {
        System.out.println("🔄 Server: " + message);
    }

    private void handleUserInput() {
        Scanner scanner = new Scanner(System.in);
        while (running) {
            System.out.print("🎯 Enter your move (row col) or 'exit' to quit: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                out.println("DISCONNECT");
                running = false;
                break;
            }
            try {
                String[] parts = input.split(" ");
                if (parts.length != 2) {
                    System.out.println("⚠️ Invalid input. Please enter row and column numbers.");
                    continue;
                }
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                if (row < 0 || row >= 3 || col < 0 || col >= 3) {
                    System.out.println("⚠️ Invalid move. Row and column must be between 0 and 2.");
                    continue;
                }
                sendMove(row, col);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid input. Please enter row and column numbers.");
            }
        }
        scanner.close();
    }

    private void closeConnection() {
        running = false;
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.out.println("⚠️ Error closing resources.");
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java client.TicTacToeClient <serverAddress> <port>");
            return;
        }
        String serverAddress = args[0];
        int port = 8080; // Default port
        if (args.length > 1) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port number.");
                return;
            }
        }
        new TicTacToeClient(serverAddress, port);
    }
}