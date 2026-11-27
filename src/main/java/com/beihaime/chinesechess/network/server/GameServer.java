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
    public GameServer(int port) throws IOException {
        this.port = port;
    }

    public void bind() throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start(Room room) {
        try {
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                executor.submit(new ClientHandler(socket,room));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        try {
            if (!serverSocket.isClosed() || serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            executor.shutdown();
        }
    }

    public String getIp(){
        return serverSocket.getInetAddress().getHostAddress();
    }


}