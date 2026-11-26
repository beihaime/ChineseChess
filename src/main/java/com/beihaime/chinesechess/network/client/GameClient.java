package com.beihaime.chinesechess.network.client;


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
    public BufferedReader getInput(){
        return input;
    }

    public BufferedWriter getOutput(){
        return output;
    }

    public void sendMessage(String message){
        try {
            output.write(message);
            output.newLine();
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}