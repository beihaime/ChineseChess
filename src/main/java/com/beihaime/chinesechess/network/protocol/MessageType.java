package com.beihaime.chinesechess.network.protocol;

public enum MessageType {
    CHAT,
    JOIN_ROOM,
    JOIN_SUCCESS,
    JOIN_FAILED,
    PLAYER_JOINED,
    GAME_START,
    MOVE,
    MOVE_UPDATE,
    GAME_OVER
}