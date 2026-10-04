package com.beihaime.chinesechess.game;
import com.beihaime.chinesechess.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void redPawnShouldMoveForward() {
        assertEquals(MoveResult.SUCCESS, game.move(
                new Position(0,6),
                new Position(0,5)
            )
        );
    }

    @Test
    void redPawnShouldNotMoveBackward() {
        assertEquals(MoveResult.ILLEGAL_MOVE, game.move(
                new Position(0,6),
                new Position(0,7)
            )
        );
    }

    @Test
    void blackPieceShouldNotMoveOnRedTurn() {
        assertEquals(MoveResult.NOT_YOUR_TURN, game.move(
                new Position(0,3),
                new Position(0,4)
            )
        );
    }

    @Test
    void blackPieceShouldMoveAfterRedTurn() {
        assertEquals(MoveResult.SUCCESS, game.move(
                new Position(0,6),
                new Position(0,5)
            )
        );
        assertEquals(MoveResult.SUCCESS, game.move(
                new Position(0,3),
                new Position(0,4)
        ));
    }

    @Test
    void emptySquareShouldReturnNoPiece() {
        assertEquals(MoveResult.NO_PIECE, game.move(
                new Position(0,1),
                new Position(0,2)
                )
        );
    }

    @Test
    void redPawnShouldNotMoveSidewaysBeforeCrossingRiver() {
        assertEquals(MoveResult.ILLEGAL_MOVE,game.move(
                new Position(0,6),
                new Position(1,6)
                )
        );
    }

    @Test
    void redPawnShouldMoveSidewaysAfterCrossingRiver() {
        assertEquals(
                MoveResult.SUCCESS,
                game.move(new Position(0,6), new Position(0,5))
        );
        assertEquals(
                MoveResult.SUCCESS,
                game.move(new Position(0,0), new Position(0,1))
        );
        assertEquals(
                MoveResult.SUCCESS,
                game.move(new Position(0,5), new Position(0,4))
        );
        assertEquals(
                MoveResult.SUCCESS,
                game.move(new Position(0,1), new Position(0,0))
        );
        assertEquals(
                MoveResult.SUCCESS,
                game.move(new Position(0,4), new Position(1,4))
        );
    }

    @Test
    void redPawnShouldNotMoveBackwardAfterCrossingRiver() {
        assertEquals(
                MoveResult.SUCCESS,
                game.move(new Position(0,6), new Position(0,5))
        );
        assertEquals(
                MoveResult.SUCCESS,
                game.move(new Position(0,0), new Position(0,1))
        );
        assertEquals(
                MoveResult.SUCCESS,
                game.move(new Position(0,5), new Position(0,4))
        );
        assertEquals(
                MoveResult.SUCCESS,
                game.move(new Position(0,1), new Position(0,0))
        );
        assertEquals(
                MoveResult.ILLEGAL_MOVE,
                game.move(new Position(0,4), new Position(0,5))
        );
    }

    @Test
    void undoShouldRestorePreviousBoardState() {
        Board board = game.getBoard();
        Piece redPawn = board.getPiece(new Position(0,6));
        assertEquals(MoveResult.SUCCESS, game.move(new Position(0,6), new Position(0,5)));
        assertEquals(MoveResult.UNDO, game.undo());
        assertSame(redPawn, board.getPiece(new Position(0,6)));
        assertNull(board.getPiece(new Position(0,5)));
    }

    @Test
    void undoShouldRestoreCapturedPiece() {
        Board board = game.getBoard();
        Piece redCannon = board.getPiece(new Position(1,7));
        Piece blackHorse = board.getPiece(new Position(1,0));
        assertEquals(MoveResult.SUCCESS, game.move(new Position(1,7), new Position(1,0)));
        assertEquals(MoveResult.UNDO, game.undo());
        assertSame(redCannon,board.getPiece(new Position(1,7)));
        assertSame(blackHorse,board.getPiece(new Position(1,0)));
    }

    @Test
    void redoShouldReapplyMove() {
        Board board = game.getBoard();
        Piece redPawn = board.getPiece(new Position(0,6));
        assertEquals(MoveResult.SUCCESS, game.move(new Position(0,6), new Position(0,5)));
        assertEquals(MoveResult.UNDO, game.undo());
        assertEquals(MoveResult.REDO, game.redo());
        assertNull(board.getPiece(new Position(0,6)));
        assertSame(redPawn, board.getPiece(new Position(0,5)));
    }

    @Test
    void redoShouldReapplyCapture() {
        Board board = game.getBoard();
        Piece redCannon = board.getPiece(new Position(1,7));
        Piece blackHorse = board.getPiece(new Position(1,0));
        assertEquals(MoveResult.SUCCESS, game.move(new Position(1,7), new Position(1,0)));
        assertEquals(MoveResult.UNDO, game.undo());
        assertSame(blackHorse, board.getPiece(new Position(1,0)));
        assertEquals(MoveResult.REDO, game.redo());
        assertNull(board.getPiece(new Position(1,7)));
        assertSame(redCannon, board.getPiece(new Position(1,0)));
    }

    @Test
    void newMoveAfterUndoShouldClearRedoHistory() {
        assertEquals(MoveResult.SUCCESS, game.move(new Position(0,6), new Position(0,5)));
        assertEquals(MoveResult.SUCCESS, game.move(new Position(0,3), new Position(0,4)));
        assertEquals(MoveResult.UNDO, game.undo());
        assertEquals(MoveResult.SUCCESS, game.move(new Position(2,3), new Position(2,4)));
        assertEquals(MoveResult.NOTHING_TO_REDO, game.redo());
    }

    @Test
    void undoWithNoHistoryShouldReturnNothingToUndo() {
        assertEquals(MoveResult.NOTHING_TO_UNDO, game.undo()
        );
    }

    @Test
    void redoWithNoHistoryShouldReturnNothingToRedo() {
        assertEquals(MoveResult.NOTHING_TO_REDO, game.redo()
        );
    }
}
