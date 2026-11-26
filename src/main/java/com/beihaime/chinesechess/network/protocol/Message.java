package com.beihaime.chinesechess.network.protocol;

import com.fasterxml.jackson.databind.JsonNode;

public record Message(
        String type,
        JsonNode data
) {
}