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

    public BufferedReader getIn() {
        return in;
    }

    private void listenForMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null && running) {
                switch (message.split(" ")[0]) {
                    case "MOVE":
                        System.out.println("🔄 Opponent moved: " + message);
                        break;
                    case "WIN":
                        System.out.println("🏆 Player " + message.charAt(4) + " wins!");
                        running = false;
                        break;
                    case "DRAW":
                        System.out.println("🤝 The game is a draw.");
                        running = false;
                        break;
                    case "START":
                        System.out.println("🎮 Game started! You are player " + playerSymbol);
                        break;
                    case "WAITING_FOR_PLAYER":
                        System.out.println("⌛ Waiting for another player to join...");
                        break;
                    case "MOVE_SUCCESS":
                        System.out.println("✅ Your move was successful.");
                        break;
                    case "MOVE_FAILED":
                        System.out.println("❌ Your move was invalid. Try again.");
                        break;
                    case "INVALID_MOVE":
                        System.out.println("⚠️ Invalid move format. Please enter row and column numbers.");
                        break;
                    case "UNKNOWN_COMMAND":
                        System.out.println("❓ Unknown command received from the server.");
                        break;
                    case "DISCONNECTED":
                        System.out.println("❌ Your opponent has disconnected. Waiting for a new player...");
                        running = false;
                        break;
                    default:
                        System.out.println("🔄 Server: " + message);
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Connection lost. Exiting...");
        } finally {
            closeConnection();
        }
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
        new TicTacToeClient("localhost", 8080);
    }


}
