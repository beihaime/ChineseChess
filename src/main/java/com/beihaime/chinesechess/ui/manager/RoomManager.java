package com.beihaime.chinesechess.ui.manager;

import com.beihaime.chinesechess.model.Room;
import com.beihaime.chinesechess.network.client.GameClient;
import com.beihaime.chinesechess.network.protocol.Message;
import com.beihaime.chinesechess.network.protocol.MessageType;
import com.beihaime.chinesechess.network.server.GameServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.UUID;

public class RoomManager {
    private Room currentRoom;
    private GameClient currentClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public Room createRoom(String roomName, int port,int maxPlayers) throws IOException {
        GameServer server = new GameServer(port);
        Thread serverThread = new Thread(server::start);
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
            Message response = null;
            try {
                response = currentClient.receive();
                if (response != null) {
                    String content = response.data().get("message").asText();
                    System.out.println(content);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Thread outputThread = new Thread(() ->{
            ObjectNode data = mapper.createObjectNode();
            data.put("message","getServerInfo");
            try {
                currentClient.send(new Message(MessageType.JOIN_ROOM.name(), data));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        inputThread.start();
        outputThread.start();
        clientThread.start();
//        currentRoom = new Room();



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
