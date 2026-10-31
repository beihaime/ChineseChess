package com.beihaime.chinesechess.ui;

import com.beihaime.chinesechess.game.Game;
import com.beihaime.chinesechess.game.MoveResult;
import com.beihaime.chinesechess.model.Board;
import com.beihaime.chinesechess.model.Piece;
import com.beihaime.chinesechess.model.Position;
import com.beihaime.chinesechess.model.Side;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;



public class ChessBoardView extends Canvas {
    private final Game game;
    private final Board board;
    private final SoundManager soundManager = new SoundManager();
    private Position selectedPosition;
    private Runnable onGameStateChanged;
    private static final double PIECE_SIZE = 34;
    private static final double SPACING = 40;
    private double getStartX() {return (getWidth() - 8 * SPACING) / 2;}
    private double getStartY() {return (getHeight() - 9 * SPACING) / 2;}
    private double getRightX() {return getStartX() + 8 * SPACING;}
    private double getBottomY() {return getStartY() + 9 * SPACING;}

    public void setOnGameStateChanged(Runnable callback) {
        this.onGameStateChanged = callback;
    }

    public ChessBoardView(double width, double height, Game game) {
        super(width, height);
        this.game = game;
        this.board = game.getBoard();
        refresh();
        setOnMouseClicked(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();
            int x = (int) Math.round((mouseX - getStartX()) / SPACING);
            int y = (int) Math.round((mouseY - getStartY()) / SPACING);
            if (x < 0 || x > 8 || y < 0 || y > 9) {
                return;
            }
            Position clickedPosition = new Position(x, y);
            Piece clickedPiece = board.getPiece(clickedPosition);
            if (selectedPosition == null) {
                if (clickedPiece == null) {
                    return;
                }
                if (clickedPiece.side() != game.getCurrentTurn()) {
                    return;
                }
                //First choose
                selectedPosition = clickedPosition;
                refresh();
                soundManager.playSelect(0.5);
                System.out.println("Selected: " + selectedPosition);
            } else {
                if (clickedPiece != null && clickedPiece.side() == game.getCurrentTurn()) {
                    selectedPosition = clickedPosition;
                    //Rechoose
                    System.out.println("Reselected: " + selectedPosition);
                    soundManager.playSelect(0.5);
                    refresh();
                    return;
                }
                MoveResult result = game.move(selectedPosition, clickedPosition);
                System.out.println("MoveResult: " + result);
                if (result == MoveResult.SUCCESS) {
                    soundManager.playMove(0.6);
                    selectedPosition = null;
                    refresh();
                    if (onGameStateChanged != null) {
                        onGameStateChanged.run();
                    }
                }
            }
        });
    }

    private void drawBoard() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.BEIGE);
        gc.fillRect(
                getStartX() - 20,
                getStartY() -20,
                8 * SPACING + 40,
                9 * SPACING + 40
        );
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(Font.font(20));
        for (int x = 0; x < 9; x++) {
            //Column line
            double screenX = getStartX() + x * SPACING;
            if (x == 0 || x == 8) {
                //Each two Side
                gc.strokeLine(screenX, getStartY(), screenX, getBottomY());
            } else {
                //Upside
                gc.strokeLine(screenX, getStartY(), screenX, getStartY() + 4 * SPACING);
                //Downside
                gc.strokeLine(screenX, getStartY() + 5 * SPACING, screenX, getBottomY());
            }
        }
        for (int y =0; y < 10; y++){
            double screenY = getStartY() + y * SPACING;
            //Row line
            gc.strokeLine(getStartX(),screenY,getRightX(),screenY);
        }

        //Diagonal-UP
        gc.strokeLine(getStartX()+ 3 *SPACING, getStartY() ,getStartX() + 5 * SPACING, getStartY() + 2 * SPACING);
        gc.strokeLine(getStartX()+ 5 *SPACING, getStartY() ,getStartX() + 3 * SPACING, getStartY() + 2 * SPACING);

        //Diagonal-Down
        gc.strokeLine(getStartX()+ 3 *SPACING, getBottomY() ,getStartX() + 5 * SPACING, getBottomY() - 2 * SPACING);
        gc.strokeLine(getStartX()+ 5 *SPACING, getBottomY() ,getStartX() + 3 * SPACING, getBottomY() - 2 * SPACING);
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Noto Sans CJK SC", 35));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText("楚河", getStartX() + 2 * SPACING, getStartY() + 4.5 * SPACING);
        gc.fillText("汉界", getStartX() + 6 * SPACING, getStartY() + 4.5 * SPACING);
    }

    private void drawPieces() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(Font.font(20));
        for (int y = 0; y < 10; y++) {
            for(int x = 0; x < 9; x++){
                Position position = new Position(x, y);
                Piece piece = board.getPiece(position);
                if (piece != null){
                    double screenX = getStartX() + x * SPACING;
                    double screenY = getStartY() + y * SPACING;
                    //Highlight selected piece
                    if (position.equals(selectedPosition)) {
                        drawHighlight(screenX, screenY ,Color.YELLOW);
                        drawHint(selectedPosition,Color.GREEN);
                    }
                    //PieceColor
                    if (piece.side() == Side.RED) {
                        gc.setStroke(Color.RED);
                        gc.setFill(Color.RED);
                    } else {
                        gc.setStroke(Color.BLACK);
                        gc.setFill(Color.BLACK);
                    }
                    //PieceShape
                    gc.strokeOval(screenX - PIECE_SIZE/2, screenY - PIECE_SIZE/2, PIECE_SIZE, PIECE_SIZE);
                    //PieceText
                    gc.fillText(piece.toString(), screenX, screenY);
                }
            }
        }
    }

    public void drawHint(Position selectedPosition , Color color) {
        for (int y = 0; y < 10; y++) {
            for(int x = 0; x < 9; x++){
                Position capturePosition = new Position(x, y);
                double screenX = getStartX() + x * SPACING;
                double screenY = getStartY() + y * SPACING;
                if (game.move(selectedPosition , capturePosition) == MoveResult.SUCCESS) {
                    drawHighlight(screenX, screenY,color);
                    game.undo();
                }
            }
        }
    }

    public void drawHighlight(double screenX, double screenY,Color color) {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setStroke(color);
        gc.strokeOval(
                screenX - PIECE_SIZE / 2,
                screenY - PIECE_SIZE / 2 ,
                PIECE_SIZE,
                PIECE_SIZE
        );
    }

    public void refresh() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        drawBoard();
        drawPieces();
    }
}
