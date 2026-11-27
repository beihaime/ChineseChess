package com.beihaime.chinesechess.model;

public class Room {

    private final String roomId;
    private String roomName;
    private final String hostAddress;
    private final int port;
    private int playerCount;
    private final int maxPlayers;
    private RoomStatus status;

    public Room(String roomId, String roomName, String hostAddress, int port, int playerCount,int maxPlayers, RoomStatus status) {
        this.roomId = roomId;
        this.playerCount = playerCount;
        this.maxPlayers = maxPlayers;
        this.roomName = roomName;
        this.hostAddress = hostAddress;
        this.port = port;
        this.status = status;

        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Invalid port");
        }
    }

    public String getRoomId() {
        return roomId;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getPort() {
        return port;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void changeRoomStatus(RoomStatus status) {
        this.status = status;
    }

    public synchronized boolean tryaddPlayer() {
        if (playerCount == maxPlayers) {
            return false;
        }
        this.playerCount++;
        return true;

    }

    public void changeRoomName(String roomName) {
        this.roomName = roomName;
    }
}
