package com.beihaime.chinesechess.ui.manager;

import com.beihaime.chinesechess.model.Room;
import java.io.IOException;
import java.util.UUID;

public class RoomManager {
    private Room currentRoom;

    public Room createRoom(String roomName, int port,int maxPlayers) throws IOException {
        currentRoom = new Room(String.valueOf(UUID.randomUUID()),
                roomName,
                "LocalHost",
                port,
                maxPlayers);
        return currentRoom;
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
