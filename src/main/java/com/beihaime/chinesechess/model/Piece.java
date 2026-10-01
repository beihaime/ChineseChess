package com.beihaime.chinesechess.model;

public class Piece {
    private final Side side;
    private final PieceType pieceType;

    public Piece(Side side ,PieceType pieceType) {
        this.side = side;
        this.pieceType = pieceType;
    }

    public Side getSide() {
        return side;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public String toString() {
        switch (pieceType) {
            case ROOK:
                if(side == Side.BLACK){
                    return "車";
                }
                return "俥";
            case HORSE:
                if(side == Side.BLACK){
                    return "馬";
                }
                return "傌";
            case ELEPHANT:
                if(side == Side.BLACK){
                    return "象";
                }
                return "相";
            case ADVISOR:
                if(side == Side.BLACK){
                    return "士";
                }
                return "仕";
            case GENERAL:
                if(side == Side.BLACK){
                    return "將";
                }
                return "帥";
            case CANNON:
                if(side == Side.BLACK){
                    return "砲";
                }
                return "炮";
            case PAWN:
                if(side == Side.BLACK) {
                    return "卒";
                }
                return "兵";
        }
        return "?";
    }
}
