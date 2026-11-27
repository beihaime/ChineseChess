package com.beihaime.chinesechess.ui.manager;

import com.beihaime.chinesechess.model.Room;
import com.beihaime.chinesechess.model.RoomStatus;
import com.beihaime.chinesechess.network.client.GameClient;
import com.beihaime.chinesechess.network.protocol.Message;
import com.beihaime.chinesechess.network.protocol.MessageCodec;
import com.beihaime.chinesechess.network.protocol.MessageType;
import com.beihaime.chinesechess.network.server.GameServer;
import com.beihaime.chinesechess.ui.ChessApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.UUID;

public class RoomManager {
    private static Room currentRoom;
    private GameClient currentClient;
    private final ObjectMapper mapper = MessageCodec.getMapper();

    public Room createRoom(String roomName, int port,int maxPlayers) throws IOException {
        GameServer server = new GameServer(port,currentRoom);
        Thread serverThread = new Thread(server::start);
        serverThread.start();

        currentRoom = new Room(String.valueOf(UUID.randomUUID()),
                roomName,
                server.getIp(),
                port,
                1,
                maxPlayers,
                RoomStatus.WAITING);
        return currentRoom;
    }

    public void joinRoom(String ip, int port, Stage stage) throws IOException {
        Thread joinThread = new Thread(() -> {
            try {
                currentClient = new GameClient(ip, port);
                ObjectNode data = mapper.createObjectNode();
                data.put("playerName", "player1");
                currentClient.send(new Message(MessageType.JOIN_ROOM.name(), data));

                Message response = currentClient.receive();
                switch (response.type()) {
                    case ("JOIN_SUCCESS") -> {
                        String roomId = response.data().get("roomId").asText();
                        String roomName = response.data().get("roomName").asText();
                        int playerCount = response.data().get("playerCount").asInt();
                        int maxPlayers = response.data().get("maxPlayers").asInt();
                        RoomStatus status = RoomStatus.valueOf(String.valueOf(response.data().get("status").asText()));
                        System.out.println(roomId + " " + roomName + " " + playerCount + " " + maxPlayers + " " + status);
                        currentRoom= new Room(
                                roomId,
                                roomName,
                                ip,
                                port,
                                playerCount,
                                maxPlayers,
                                status
                        );
                        Platform.runLater(() -> ChessApplication.Room(stage));
                    }
                    case ("JOIN_FAILED") -> {
                        System.out.println("Failed to join room");
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        joinThread.start();

    }


    public static Room getCurrentRoom() {
        if (currentRoom == null) {
            return new Room("NO-ROOMS","NOROOMS","NOROOMS",8888,1,1,RoomStatus.WAITING);
        }
        return currentRoom;
    }

    public static Room closeCurrentRoom() {
        currentRoom = null;
        return currentRoom;
    }
}
