package com.beihaime.chinesechess.network.server;

import com.beihaime.chinesechess.model.Room;
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
    private final ObjectMapper mapper = new ObjectMapper();
    private BufferedWriter output;
    private BufferedReader input;
    private final Room serverRoom = ChessApplication.getRoomManager().getCurrentRoom();

    public ClientHandler(Socket socket) {
        this.socket = socket;
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

        String serverInfo = serverRoom.getRoomName() +
                serverRoom.getRoomId() +
                serverRoom.getPlayerCount() +
                serverRoom.getMaxPlayers() +
                serverRoom.getRoomId() +
                serverRoom.getStatus();

        Message message = receive();
        switch (message.type()) {
            case "JOIN_ROOM" -> {
                String content = message.data().get("message").asText();
                System.out.println( "received "+ content);
                ObjectNode data = mapper.createObjectNode();
                data.put("message", serverInfo);
                send(new Message(MessageType.JOIN_SUCCESS.name(), data));
            }
        }



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

    @Override
    public void run() {
        try {
            handleClient();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}