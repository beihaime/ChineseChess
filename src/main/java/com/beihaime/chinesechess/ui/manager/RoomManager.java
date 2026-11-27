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
    private Room currentRoom;
    private GameClient currentClient;
    private final ObjectMapper mapper = MessageCodec.getMapper();
    private GameServer currentServer;

    public Room createRoom(String roomName, int port,int maxPlayers) throws IOException {

        currentServer = new GameServer(port);
        currentServer.bind();

        currentRoom = new Room(String.valueOf(UUID.randomUUID()),
                roomName,
                currentServer.getIp(),
                port,
                1,
                maxPlayers,
                RoomStatus.WAITING);

        Thread serverThread = new Thread(() -> {
            currentServer.start(currentRoom);
        });
        serverThread.start();

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
                        Room room= new Room(
                                roomId,
                                roomName,
                                ip,
                                port,
                                playerCount,
                                maxPlayers,
                                status
                        );
                        currentRoom = room;

                        Platform.runLater(() -> ChessApplication.Room(stage));
                    }
                    case ("JOIN_FAILED") -> {
                        System.out.println("Failed to join room");
                        currentClient.close();
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        joinThread.start();

    }


    public final Room getCurrentRoom() {
        if (currentRoom== null) {
            return null;
        }
        return currentRoom;
    }

    public final Room closeCurrentRoom() throws IOException {
        if (currentServer != null) {
            currentServer.stop();
            currentServer = null;
        }

        if (currentClient != null) {
            currentClient.close();
            currentClient = null;
        }

        currentRoom = null;
        return currentRoom;
    }
}
