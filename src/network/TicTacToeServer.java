package network;

import game.GameLogic;
import java.io.*;
import java.net.*;
import java.util.*;

public class TicTacToeServer {
    try{
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);
        acceptClients();
    }catch(IOException e){
        e.printStackTrace();
    }
}
