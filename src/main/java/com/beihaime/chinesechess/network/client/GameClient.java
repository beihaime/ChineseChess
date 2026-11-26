package com.beihaime.chinesechess.network.client;


import com.beihaime.chinesechess.network.protocol.Message;
import com.beihaime.chinesechess.network.protocol.MessageCodec;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class GameClient  {
    private final String ip;
    private final int port;
    private final Socket socket;
    private final BufferedWriter output;
    private final BufferedReader input;

    public GameClient(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;

        socket = new Socket(ip, port);

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
    public BufferedReader getInput() {
        return input;
    }

    public BufferedWriter getOutput() {
        return output;
    }

    public void send(Message message) throws IOException {
        try {
            String json = MessageCodec.encode(message);
            output.write(json);
            output.newLine();
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Message receive() throws IOException {
        String json = input.readLine();

        if (json == null) {
            return null;
        }

        return MessageCodec.decode(json);
    }
}