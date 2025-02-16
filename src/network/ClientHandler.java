package network;
import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
    private Socket socket;
    private TicTacToeServer server;
    private PrintWriter out;
    private BufferedReader in;
    private char playerSymbol;

    public ClientHandler(Socket socket,TicToeServer server,char playerSymbol){
        this.socket = socket;
        this.server = server;
        this.playerSymbol = playerSymbol;
    }
}
