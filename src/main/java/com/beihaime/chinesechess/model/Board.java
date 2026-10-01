package com.beihaime.chinesechess.model;

public class Board {
    private final Piece[][] pieces = new Piece[9][10];

    public boolean isInsideBoard(Position position) {
        return position.x() >= 0
                && position.y() >= 0
                && position.x() < 9
                && position.y() < 10;
    }


    public Piece getPiece(Position position) {
        return pieces[position.x()][position.y()];
    }


    public void setPiece(Position position, Piece piece) {
        pieces[position.x()][position.y()] = piece;
    }

    public void removePiece(Position position) {
        pieces[position.x()][position.y()] = null;
    }

    public void movePiece(Position from, Position to) {
        Piece originalPieces = getPiece(from);
        Piece newPieces = getPiece(to);
        if(originalPieces == null){
            return;
        }
        removePiece(from);
        setPiece(to, originalPieces);
    }

    public void printBoard() {
        for (int y=0; y<10; y++){
            for (int x=0; x<9; x++){
                if(pieces[x][y] == null){
                    System.out.print("+  ");
                }
                else{
                    System.out.print(pieces[x][y] + " ");
                }
            }
            System.out.println();
        }
    }

    public void setupInitialPosition() {
        //RED
        setPiece(new Position(0,9), new Piece(Side.RED, PieceType.ROOK));
        setPiece(new Position(8,9), new Piece(Side.RED, PieceType.ROOK));
        setPiece(new Position(1,9), new Piece(Side.RED, PieceType.HORSE));
        setPiece(new Position(7,9), new Piece(Side.RED, PieceType.HORSE));
        setPiece(new Position(2,9), new Piece(Side.RED, PieceType.ELEPHANT));
        setPiece(new Position(6,9), new Piece(Side.RED, PieceType.ELEPHANT));
        setPiece(new Position(3,9), new Piece(Side.RED, PieceType.ADVISOR));
        setPiece(new Position(5,9), new Piece(Side.RED, PieceType.ADVISOR));
        setPiece(new Position(4,9), new Piece(Side.RED, PieceType.GENERAL));

        setPiece(new Position(1,7), new Piece(Side.RED, PieceType.CANNON));
        setPiece(new Position(7,7), new Piece(Side.RED, PieceType.CANNON));

        setPiece(new Position(0,6), new Piece(Side.RED, PieceType.PAWN));
        setPiece(new Position(2,6), new Piece(Side.RED, PieceType.PAWN));
        setPiece(new Position(4,6), new Piece(Side.RED, PieceType.PAWN));
        setPiece(new Position(6,6), new Piece(Side.RED, PieceType.PAWN));
        setPiece(new Position(8,6), new Piece(Side.RED, PieceType.PAWN));

        //BLACK
        setPiece(new Position(0,0), new Piece(Side.BLACK, PieceType.ROOK));
        setPiece(new Position(8,0), new Piece(Side.BLACK, PieceType.ROOK));
        setPiece(new Position(1,0), new Piece(Side.BLACK, PieceType.HORSE));
        setPiece(new Position(7,0), new Piece(Side.BLACK, PieceType.HORSE));
        setPiece(new Position(2,0), new Piece(Side.BLACK, PieceType.ELEPHANT));
        setPiece(new Position(6,0), new Piece(Side.BLACK, PieceType.ELEPHANT));
        setPiece(new Position(3,0), new Piece(Side.BLACK, PieceType.ADVISOR));
        setPiece(new Position(5,0), new Piece(Side.BLACK, PieceType.ADVISOR));
        setPiece(new Position(4,0), new Piece(Side.BLACK, PieceType.GENERAL));

        setPiece(new Position(1,2), new Piece(Side.BLACK, PieceType.CANNON));
        setPiece(new Position(7,2), new Piece(Side.BLACK, PieceType.CANNON));

        setPiece(new Position(0,3), new Piece(Side.BLACK, PieceType.PAWN));
        setPiece(new Position(2,3), new Piece(Side.BLACK, PieceType.PAWN));
        setPiece(new Position(4,3), new Piece(Side.BLACK, PieceType.PAWN));
        setPiece(new Position(6,3), new Piece(Side.BLACK, PieceType.PAWN));
        setPiece(new Position(8,3), new Piece(Side.BLACK, PieceType.PAWN));
    }
}
