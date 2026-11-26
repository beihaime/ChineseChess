package com.beihaime.chinesechess.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {
    private final int port;
    private ServerSocket serverSocket;
    private final ExecutorService executor= Executors.newFixedThreadPool(5);
    public GameServer(int port) {
        this.port = port;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            while(true){
                Socket socket = serverSocket.accept();
                executor.submit(new ClientHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        try {
            serverSocket.close();
            executor.shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getIp(){
        return serverSocket.getInetAddress().getHostAddress();
    }

}