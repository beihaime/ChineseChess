package com.beihaime.chinesechess.game;

public enum MoveResult {
    SUCCESS,
    NO_PIECE,
    NOT_YOUR_TURN,
    ILLEGAL_MOVE,
    GAME_OVER,
    UNDO,
    NOTHING_TO_UNDO,
    REDO,
    NOTHING_TO_REDO,
}
