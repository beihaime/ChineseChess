package com.beihaime.chinesechess.network.server;

import com.beihaime.chinesechess.model.Room;
import com.beihaime.chinesechess.model.RoomStatus;
import com.beihaime.chinesechess.network.protocol.Message;
import com.beihaime.chinesechess.network.protocol.MessageCodec;
import com.beihaime.chinesechess.network.protocol.MessageType;
import com.beihaime.chinesechess.ui.ChessApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final ObjectMapper mapper = MessageCodec.getMapper();
    private BufferedWriter output;
    private BufferedReader input;
    private final Room serverRoom;

    public ClientHandler(Socket socket,Room serverRoom) {
        this.socket = socket;
        this.serverRoom = serverRoom;
    }

    public void handleClient() throws IOException {
        output = new BufferedWriter(
            new OutputStreamWriter(
                    socket.getOutputStream(),
                    StandardCharsets.UTF_8
            )
        );

        input = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream(),
                        StandardCharsets.UTF_8
                )
        );

    }

    public Message receive() throws IOException {
        String json = input.readLine();
        Message message = MessageCodec.decode(json);
        return message;
    }

    public void send(Message message) throws IOException {
        String json = mapper.writeValueAsString(message);
        output.write(json);
        output.newLine();
        output.flush();
    }

    private Message handleMessage(Message message) {
        switch (message.type()) {
            case "JOIN_ROOM" -> {
                if (!serverRoom.tryaddPlayer()) {
                    ObjectNode data = mapper.createObjectNode();
                    data.put("reason","Room is fulled");
                    return new Message(MessageType.JOIN_FAILED.name(), data );
                }
                serverRoom.changeRoomStatus(RoomStatus.PLAYING);
                ChessApplication.getRoomManager().notifyOnRoomChanged();
                String player = message.data().get("playerName").asText();
                System.out.println( "received "+ player);
                ObjectNode data = mapper.createObjectNode();
                data.put("roomId", serverRoom.getRoomId());
                data.put("roomName", serverRoom.getRoomName());
                data.put("playerCount", serverRoom.getPlayerCount());
                data.put("maxPlayers", serverRoom.getMaxPlayers());
                data.put("status",String.valueOf(serverRoom.getStatus()));
                return new Message(MessageType.JOIN_SUCCESS.name(), data);
            }
        }
        return null;

    }

    @Override
    public void run() {
        try {
            handleClient();
            Message message = receive();
            Message response = handleMessage(message);

            send(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}