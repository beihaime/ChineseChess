package com.beihaime.chinesechess.game;

import com.beihaime.chinesechess.model.Board;
import com.beihaime.chinesechess.model.Piece;
import com.beihaime.chinesechess.model.Position;
import com.beihaime.chinesechess.model.Side;
import com.beihaime.chinesechess.rule.RuleEngine;

public class Game {
    public Game() {
        board = new Board();
        board.setupInitialPosition();
        ruleEngine = new RuleEngine();
        currentTurn = Side.RED;
    }
    private final Board board;
    private final RuleEngine ruleEngine;
    private Side currentTurn;

    public void printBoard() {
        board.printBoard();
    }

    public Board getBoard() {
        return board;
    }

    public MoveResult move(Position from, Position to){
        Piece piece = board.getPiece(from);
        if(piece == null){
            return MoveResult.NO_PIECE;
        }
        if (piece.getSide() != currentTurn) {
            return MoveResult.NOT_YOUR_TURN;
        }
        if (!ruleEngine.isLegalMove(board, from, to)) {
            return MoveResult.ILLEGAL_MOVE;
        }
        board.movePiece(from, to);
        if (currentTurn == Side.RED) {
            currentTurn = Side.BLACK;
        } else {
            currentTurn = Side.RED;
        }
        return MoveResult.SUCCESS;
    }

}
