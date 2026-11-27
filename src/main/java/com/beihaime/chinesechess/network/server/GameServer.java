package com.beihaime.chinesechess.network.server;

import com.beihaime.chinesechess.model.Room;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {
    private final int port;
    private ServerSocket serverSocket;
    private final ExecutorService executor= Executors.newFixedThreadPool(5);
    private final Room serverRoom;
    public GameServer(int port, Room serverRoom) {
        this.port = port;
        this.serverRoom = serverRoom;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            while(true){
                Socket socket = serverSocket.accept();
                executor.submit(new ClientHandler(socket,serverRoom));
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

    public Room getRoom(){
        return serverRoom;
    }

}