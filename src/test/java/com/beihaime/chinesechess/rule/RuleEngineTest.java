package com.beihaime.chinesechess.rule;

import com.beihaime.chinesechess.game.Game;
import com.beihaime.chinesechess.game.MoveResult;
import com.beihaime.chinesechess.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RuleEngineTest {
    private Board board;
    private RuleEngine ruleEngine;
    @BeforeEach
    void setUp() {
        board = new Board();
        ruleEngine = new RuleEngine();
        board.setPiece(new Position(3,9), new Piece(Side.RED, PieceType.GENERAL));
        board.setPiece(new Position(5,0), new Piece(Side.BLACK, PieceType.GENERAL));
    }
    
    @Test
    void horseShouldNotMoveWhenLegIsBlocked() {
        board.setPiece(new Position(1,9),new Piece(Side.RED, PieceType.HORSE));
        board.setPiece(new Position(1,8),new Piece(Side.RED, PieceType.PAWN));
        assertFalse(ruleEngine.isLegalMove(board,new Position(1,9),new Position(2,7)));
    }

    @Test
    void horseShouldMoveWhenLegIsClear() {
        board.setPiece(new Position(1,9),new Piece(Side.RED, PieceType.HORSE));
        assertTrue(ruleEngine.isLegalMove(board,new Position(1,9),new Position(2,7)));
    }

    @Test
    void horseShouldNotMoveInStraightLine() {
        board.setPiece(new Position(1,9),new Piece(Side.RED, PieceType.HORSE));
        assertFalse(ruleEngine.isLegalMove(board,new Position(1,9),new Position(1,7)));
    }

    @Test
    void rookShouldNotMoveWhenPathIsBlocked() {
        board.setPiece(new Position(0,9), new Piece(Side.RED, PieceType.ROOK));
        board.setPiece(new Position(0,7), new Piece(Side.RED, PieceType.PAWN));
        assertFalse(ruleEngine.isLegalMove(board,new Position(0,9),new Position(0,5)));
    }

    @Test
    void rookShouldMoveWhenPathIsClear() {
        board.setPiece(new Position(0,9), new Piece(Side.RED, PieceType.ROOK));
        assertTrue(ruleEngine.isLegalMove(board,new Position(0,9),new Position(0,5)));
    }

    @Test
    void rookShouldNotMoveDiagonally() {
        board.setPiece(new Position(0,9), new Piece(Side.RED, PieceType.ROOK));
        assertFalse(ruleEngine.isLegalMove(board,new Position(0,9),new Position(1,8)));
    }

    @Test
    void elephantShouldMoveDiagonallyTwoSquares() {
        board.setPiece(new Position(2,9), new Piece(Side.RED, PieceType.ELEPHANT));
        assertTrue(ruleEngine.isLegalMove(board,new Position(2,9),new Position(4,7)));
    }

    @Test
    void  elephantShouldNotMoveWhenEyeIsBlocked() {
        board.setPiece(new Position(2,9), new Piece(Side.RED, PieceType.ELEPHANT));
        board.setPiece(new Position(3,8), new Piece(Side.RED, PieceType.PAWN));
        assertFalse(ruleEngine.isLegalMove(board,new Position(2,9),new Position(4,7)));
    }

    @Test
    void elephantShouldNotCrossRiver() {
        board.setPiece(new Position(2,5), new Piece(Side.RED, PieceType.ELEPHANT));
        assertFalse(ruleEngine.isLegalMove(board,new Position(2,5),new Position(4,3)));
    }

    @Test
    void advisorShouldMoveDiagonallyOneSquare() {
        board.setPiece(new Position(4,9), new Piece(Side.RED, PieceType.ADVISOR));
        assertTrue(ruleEngine.isLegalMove(board,new Position(4,9),new Position(5,8)));
    }

    @Test
    void advisorShouldNotLeavePalace() {
        board.setPiece(new Position(5,9), new Piece(Side.RED, PieceType.ADVISOR));
        assertFalse(ruleEngine.isLegalMove(board,new Position(5,9),new Position(6,8)));
    }

    @Test
    void advisorShouldNotMoveStraight() {
        board.setPiece(new Position(5,9), new Piece(Side.RED, PieceType.ADVISOR));
        assertFalse(ruleEngine.isLegalMove(board,new Position(5,9),new Position(5,8)));
    }

    @Test
    void generalShouldMoveOneSquareInsidePalace() {
        assertTrue(ruleEngine.isLegalMove(board,new Position(3,9),new Position(3,8)));
    }

    @Test
    void generalShouldNotLeavePalace() {
        board.movePiece(new Position(3,9),new Position(3,8));
        assertFalse(ruleEngine.isLegalMove(board,new Position(3,8),new Position(2,8)));
    }

    @Test
    void generalShouldNotMoveDiagonally() {
        assertFalse(ruleEngine.isLegalMove(board,new Position(3,9),new Position(4,8)));
    }

    @Test
    void generalsShouldNotFaceEachOther() {
        board.movePiece(new Position(3,9),new Position(4,9));
        assertFalse(ruleEngine.isLegalMove(board,new Position(4,9),new Position(5,9)));
    }

    @Test
    void cannonShouldNotJumpOverPieceWhenNotCapturing() {
        board.setPiece(new Position(4,9), new Piece(Side.RED, PieceType.CANNON));
        board.setPiece(new Position(4,8), new Piece(Side.RED, PieceType.PAWN));
        assertFalse(ruleEngine.isLegalMove(board,new Position(4,9),new Position(4,7)));
    }

    @Test
    void cannonShouldNotCaptureWithoutScreen() {
        board.setPiece(new Position(4,9), new Piece(Side.RED, PieceType.CANNON));
        board.setPiece(new Position(4,8), new Piece(Side.BLACK, PieceType.PAWN));
        assertFalse(ruleEngine.isLegalMove(board,new Position(4,9),new Position(4,8)));
    }

    @Test
    void cannonShouldNotCaptureWithTwoScreens() {
        board.setPiece(new Position(4,9), new Piece(Side.RED, PieceType.CANNON));
        board.setPiece(new Position(4,8), new Piece(Side.RED, PieceType.PAWN));
        board.setPiece(new Position(4,7), new Piece(Side.RED, PieceType.PAWN));
        board.setPiece(new Position(4,6), new Piece(Side.BLACK, PieceType.PAWN));
        assertFalse(ruleEngine.isLegalMove(board,new Position(4,9),new Position(4,6)));
    }

    @Test
    void cannonShouldCaptureWithExactlyOneScreen() {
        board.setPiece(new Position(4,9), new Piece(Side.RED, PieceType.CANNON));
        board.setPiece(new Position(4,8), new Piece(Side.RED, PieceType.PAWN));
        board.setPiece(new Position(4,7), new Piece(Side.BLACK, PieceType.PAWN));
        assertTrue(ruleEngine.isLegalMove(board,new Position(4,9),new Position(4,7)));
    }

    @Test
    void cannonShouldMoveWhenPathIsClear() {
        board.setPiece(new Position(4,9), new Piece(Side.RED, PieceType.CANNON));
        assertTrue(ruleEngine.isLegalMove(board,new Position(4,9),new Position(4,7)));
    }

    @Test
    void redGeneralShouldBeInCheckByBlackRook() {
        board.setPiece(new Position(3,0), new Piece(Side.BLACK, PieceType.ROOK));
        assertTrue(ruleEngine.isInCheck(board, Side.RED));
    }

    @Test
    void redGeneralShouldNotBeInCheckWhenRookIsBlocked() {
        board.setPiece(new Position(3,0), new Piece(Side.BLACK, PieceType.ROOK));
        board.setPiece(new Position(3,5), new Piece(Side.RED, PieceType.PAWN));
        assertFalse(ruleEngine.isInCheck(board, Side.RED));
    }

    @Test
    void pieceShouldNotMoveIfItExposesOwnGeneral() {
        board.setPiece(new Position(3,5), new Piece(Side.RED, PieceType.ROOK));
        board.setPiece(new Position(3,0), new Piece(Side.BLACK, PieceType.ROOK));
        assertFalse(ruleEngine.isInCheck(board, Side.RED));
        assertFalse(ruleEngine.isLegalMove(board,new Position(3,5),new Position(4,5)));
    }

    @Test
    void illegalMoveShouldNotChangeBoard() {
        Piece redRook = new Piece(Side.RED, PieceType.ROOK);
        board.setPiece(new Position(3,5), redRook);
        board.setPiece(new Position(3,0), new Piece(Side.BLACK, PieceType.ROOK));
        assertFalse(ruleEngine.isLegalMove(board, new Position(3,5), new Position(4,5)));
        assertSame(redRook, board.getPiece(new Position(3,5)));
        assertNull(board.getPiece(new Position(4,5)));
    }

    @Test
    void legalMoveCheckShouldNotChangeBoard() {
        Piece redRook = new Piece(Side.RED, PieceType.ROOK);
        board.setPiece(new Position(0,9), redRook);
        assertTrue(ruleEngine.isLegalMove(board, new Position(0,9), new Position(0,5)));
        assertSame(redRook, board.getPiece(new Position(0,9)));
        assertNull(board.getPiece(new Position(0,5)));
    }
}
