package com.beihaime.chinesechess.network.protocol;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MessageCodec {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String encode(Message message) throws IOException {
        return MAPPER.writeValueAsString(message);
    }

    public static Message decode(String message) throws IOException {
        return MAPPER.readValue(message, Message.class);
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }

}
