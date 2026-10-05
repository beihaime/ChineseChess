package com.beihaime.chinesechess.game;

import com.beihaime.chinesechess.model.Board;
import com.beihaime.chinesechess.model.Piece;
import com.beihaime.chinesechess.model.Position;
import com.beihaime.chinesechess.model.Side;
import com.beihaime.chinesechess.rule.RuleEngine;

import java.util.ArrayList;
import java.util.List;

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
    private final List<Move> moveHistory = new ArrayList<>();
    private final List<Move> redoHistory = new ArrayList<>();
    public void printBoard() {
        board.printBoard();
    }

    public Board getBoard() {
        return board;
    }

    private void switchTurn() {
        currentTurn = (currentTurn == Side.RED) ? Side.BLACK : Side.RED;
    }

    public List<Move> getMoveHistory() {
        return List.copyOf(moveHistory);
    }

    public void  printMoveHistory() {
        int number = 1;
        for (Move move: moveHistory) {
            System.out.print(number + "."
            + move.movingPiece()
            + " "
            + "("+ (move.from().x() + 1)+","+ (move.from().y()+ 1)+")"
            + "->"
            + "("+ (move.to().x() + 1)+","+ (move.to().y()+ 1)+")"
            );
            number++;
            if (move.capturedPiece() != null) {
                System.out.print( "吃"+ move.capturedPiece());
            }
            System.out.print("\n");
        }

    }

    public MoveResult move(Position from, Position to) {
        if (isGameOver()) {
            return MoveResult.GAME_OVER;
        }
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
        Piece movingPiece = board.getPiece(from);
        Piece capturedPiece = board.getPiece(to);
        board.movePiece(from, to);
        moveHistory.add(new Move(from, to ,movingPiece ,capturedPiece));
        switchTurn();
        redoHistory.clear();
        return MoveResult.SUCCESS;
    }

    public MoveResult undo() {
        if (moveHistory.isEmpty()) {
            return MoveResult.NOTHING_TO_UNDO;
        }
        Move lastMove = moveHistory.removeLast();
        board.setPiece(lastMove.from(),lastMove.movingPiece());
        board.removePiece(lastMove.to());
        if (lastMove.capturedPiece() != null) {
            board.setPiece(lastMove.to(),lastMove.capturedPiece());
        }
        redoHistory.add(lastMove);
        switchTurn();
        return MoveResult.UNDO;
    }

    public MoveResult redo() {
        if (redoHistory.isEmpty()) {
            return MoveResult.NOTHING_TO_REDO;
        }
        Move lastUndoneMove = redoHistory.removeLast();
        board.movePiece(lastUndoneMove.from(),lastUndoneMove.to());

        moveHistory.add(lastUndoneMove);
        switchTurn();
        return MoveResult.REDO;
    }



    public boolean isCurrentPlayerInCheck() {
        return ruleEngine.isInCheck(board, currentTurn);
    }

    public boolean isGameOver() {
        GameStatus status = getStatus();
        return status == GameStatus.CHECKMATE || status == GameStatus.STALEMATE;
    }

    public Side getWinner() {
        if (!isGameOver()) {
            return null;
        }
        return (currentTurn == Side.RED) ? Side.BLACK : Side.RED;
    }

    public GameStatus getStatus() {
        boolean inCheck = ruleEngine.isInCheck(board, currentTurn);
        boolean hasLegalMoves = ruleEngine.hasAnyLegalMoves(board, currentTurn);
        if (!hasLegalMoves) {return inCheck ? GameStatus.CHECKMATE : GameStatus.STALEMATE;}
        return inCheck ? GameStatus.CHECK : GameStatus.RUNNING;
    }
}
