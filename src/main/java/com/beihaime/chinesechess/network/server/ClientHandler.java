package com.beihaime.chinesechess.network.server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void handleClient() throws IOException {
        BufferedWriter output = new BufferedWriter(
            new OutputStreamWriter(
                    socket.getOutputStream(),
                    StandardCharsets.UTF_8
            )
        );

        BufferedReader input = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream(),
                        StandardCharsets.UTF_8
                )
        );


        String response = input.readLine();
        System.out.println(response);
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