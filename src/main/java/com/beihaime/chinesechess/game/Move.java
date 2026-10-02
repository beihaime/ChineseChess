package com.beihaime.chinesechess.game;

import com.beihaime.chinesechess.model.Piece;
import com.beihaime.chinesechess.model.Position;

public record Move(Position from, Position to, Piece movingPiece, Piece capturedPiece) {

}

