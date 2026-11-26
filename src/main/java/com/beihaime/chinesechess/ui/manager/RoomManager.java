package com.beihaime.chinesechess.ui.manager;

import com.beihaime.chinesechess.model.Room;
import com.beihaime.chinesechess.network.client.GameClient;
import com.beihaime.chinesechess.network.server.GameServer;

import java.io.IOException;
import java.util.UUID;

public class RoomManager {
    private Room currentRoom;
    private GameClient currentClient;

    public Room createRoom(String roomName, int port,int maxPlayers) throws IOException {
        GameServer server = new GameServer(port);
        Thread serverThread = new Thread(() ->{
            server.start();
        });
        serverThread.start();

        currentRoom = new Room(String.valueOf(UUID.randomUUID()),
                roomName,
                server.getIp(),
                port,
                maxPlayers);
        return currentRoom;
    }

    public void joinRoom(String ip,int port) throws IOException {
        Thread clientThread = new Thread(() ->{
            try {
                currentClient = new GameClient(ip, port);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Thread inputThread = new Thread(() ->{
            String response = null;
            try {
                response = currentClient.getInput().readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(response);
        });
        Thread outputThread = new Thread(() ->{
            currentClient.sendMessage("你好");
        });
        inputThread.start();
        outputThread.start();
        clientThread.start();
        currentRoom = new Room("test","test",ip,port,1);



    }

    public Room getCurrentRoom() {
        if (currentRoom == null) {
            return new Room("NO-ROOMS","NOROOMS","NOROOMS",8888,1);
        }
        return currentRoom;
    }

    public Room closeCurrentRoom() {
        currentRoom = null;
        return currentRoom;
    }
}
