package com.beihaime.chinesechess.rule;

import com.beihaime.chinesechess.model.*;

public class RuleEngine {
    public boolean isLegalMove(Board board, Position from, Position to) {
        //Piece area valid check
        if (!board.isInsideBoard(from) || !board.isInsideBoard(to)) {
            return false;
        }
        Piece target = board.getPiece(to);
        Piece self = board.getPiece(from);
        if (self == null) {
            return false;
        }
        if (from.equals(to)) {
            return false;
        }
        if (target != null && target.getSide() == self.getSide()) {
            return false;
        }
        switch (self.getPieceType()) {
            case ROOK:
                return rookCheck(board, from, to);
            case HORSE:
                return horseCheck(board, from, to);
            case ELEPHANT:
                return elephantCheck(board, from, to);
            case ADVISOR:
                return advisorCheck(board, from, to);
            case GENERAL:
                return generalCheck(board, from, to);
            case CANNON:
                return cannonCheck(board, from, to);
            case PAWN:
                return pawnCheck(board, from, to);
            default:
                return false;
        }
    }

    private boolean rookCheck(Board board, Position from, Position to){
        boolean sameX = to.x() == from.x();
        boolean sameY = to.y() == from.y();
        int startX = Math.min(from.x(), to.x());
        int startY = Math.min(from.y(), to.y());
        int endX = Math.max(from.x(), to.x());
        int endY = Math.max(from.y(), to.y());
        if (!sameX && !sameY) {
            return false;
        }
        for (int x = startX +1; x < endX; x++) {
            // Row barrier check
            if(board.getPiece(new Position(x, from.y())) != null) {
                return false;
            }
        }
        for (int y = startY + 1; y < endY; y++) {
            // Column barrier check
            if (board.getPiece(new Position(from.x(), y)) != null) {
                return false;
            }
        }
        return true;
    }

    private boolean horseCheck(Board board, Position from, Position to)  {
        Position leg;
        int dx = Math.abs(to.x() - from.x());
        int dy = Math.abs(to.y() - from.y());
        boolean isHorseShape =(dx ==1 && dy ==2) || (dx ==2 && dy ==1);
        if (!isHorseShape){
            return false;
        }
        // Check whether the horse leg is blocked
        if (dx==2) {
            int direction = Integer.signum(to.x() - from.x());
            leg = new Position(from.x() + direction, from.y());
        } else{
            int direction = Integer.signum(to.y() - from.y());
            leg = new Position(from.x(), from.y() + direction);
        }
        if (board.getPiece(leg) != null) {
            return false;
        }
        return true;
    }

    private boolean elephantCheck(Board board, Position from, Position to) {
        Position center;
        Piece self = board.getPiece(from);
        int river = 5;
        int dx = Math.abs(to.x() - from.x());
        int dy = Math.abs(to.y() - from.y());
        boolean isElephantShape =(dx ==2 && dy ==2);
        int directionX = Integer.signum(to.x() - from.x());
        int directionY = Integer.signum(to.y() - from.y());
        if (!isElephantShape){
            return false;
        }
        //Elephant cannot cross river
        if (self.getSide() == Side.BLACK && to.y() >= river
                || self.getSide() == Side.RED && to.y() < river) {
            return false;
        }
        center = new Position(from.x() + directionX, from.y()+ directionY);
        if (board.getPiece(center) != null) {
            return false;
        }
        return true;
    }

    private boolean advisorCheck(Board board, Position from, Position to) {
        int dx = Math.abs(to.x() - from.x());
        int dy = Math.abs(to.y() - from.y());
        Piece self = board.getPiece(from);
        boolean isAdvisorShape =(dx ==1 && dy ==1);
        if (!isAdvisorShape){
            return false;
        }
        if (to.x() > 5 || to.x() < 3 ) {
            return false;
        }
        if (self.getSide() == Side.BLACK &&
                (to.y() > 2 || to.y() < 0)) {
            return false;
        }
        if (self.getSide() == Side.RED &&
                (to.y() > 9 || to.y() < 7)) {
            return false;
        }
        return true;
    }

    private boolean generalsFacing(Board board) {
        Position redGeneral = null;
        Position blackGeneral = null;
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 9; x++) {
                Piece piece = board.getPiece(new Position(x, y));
                if (piece == null) {
                    continue;
                }
                if (piece.getPieceType() == PieceType.GENERAL) {
                    if (piece.getSide() == Side.BLACK) {
                        blackGeneral = new Position(x, y);
                    }
                    if (piece.getSide() == Side.RED) {
                        redGeneral = new Position(x, y);
                    }
                }
            }
        }
        if (redGeneral == null || blackGeneral == null) {
            return false;
        }
        if (redGeneral.x() != blackGeneral.x()) {
            return false;
        }
        int startY = Math.min(redGeneral.y(), blackGeneral.y());
        int endY = Math.max(redGeneral.y(), blackGeneral.y());
        for (int y = startY + 1; y < endY; y++) {
            Position position = new Position(redGeneral.x(), y);
            if (board.getPiece(position) != null) {
                return false;
            }
        }
        return true;
    }

    private boolean generalCheck(Board board, Position from, Position to) {
        Piece self = board.getPiece(from);
        boolean sameX = to.x() == from.x();
        boolean sameY = to.y() == from.y();
        int dx = Math.abs(to.x() - from.x());
        int dy = Math.abs(to.y() - from.y());
        if (!sameX && !sameY) {
            return false;
        }
        //Step valid check
        boolean isGeneralShape =
                (dx ==1 && dy ==0)
                ||
                (dx ==0 && dy ==1);
        if (!isGeneralShape){
            return false;
        }
        if (to.x() > 5 || to.x() < 3) {
            return false;
        }
        //Black area
        if (self.getSide() == Side.BLACK &&
                (to.y() > 2 || to.y() < 0)) {
            return false;
        }
        //Red area
        if (self.getSide() == Side.RED &&
                (to.y() > 9 || to.y() < 7)) {
            return false;
        }
        //General cannot meet
        if (generalsFacing(board)) {
            return false;
        }
        return true;
    }

    private boolean cannonCheck(Board board, Position from, Position to) {
        int pieceCount = 0;
        Piece target = board.getPiece(to);
        boolean sameX = to.x() == from.x();
        boolean sameY = to.y() == from.y();
        int startX = Math.min(from.x(), to.x());
        int startY = Math.min(from.y(), to.y());
        int endX = Math.max(from.x(), to.x());
        int endY = Math.max(from.y(), to.y());

        if (!sameX && !sameY) {
            return false;
        }
        for (int x = startX +1; x < endX; x++) {
            // Row barrier check
            if(board.getPiece(new Position(x, from.y())) != null) {
                pieceCount ++;
            }
        }
        for (int y = startY + 1; y < endY; y++) {
            // Column barrier check
            if (board.getPiece(new Position(from.x(), y)) != null) {
                pieceCount ++;
            }
        }
        if (target == null) {
            return pieceCount == 0;
        }
        return pieceCount == 1;
    }

    private boolean pawnCheck(Board board, Position from, Position to) {
        Piece self = board.getPiece(from);
        int dx = Math.abs(to.x() - from.x());
        int dy = to.y() - from.y();
        boolean sideways = dx == 1 && dy == 0;
        boolean forward = (self.getSide() == Side.BLACK && dy == 1)
                            ||
                        (self.getSide() == Side.RED && dy == -1);
        int river = 5;
        //Pawn cannot move sideways before crossing the river
        boolean crossedRiver = false;
        if (self.getSide() == Side.BLACK ) {
            crossedRiver = from.y() >= 5;
        } else {
            crossedRiver = from.y() < 5;
        }
        if (!crossedRiver) {
            return forward;
        }
        return forward || sideways;
    }
}
