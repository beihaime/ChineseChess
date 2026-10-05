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
    private Position selectedPosition;
    private static final double START_X = 50;
    private static final double START_Y = 50;
    private static final double SPACING = 40;
    private static final double RIGHT_X = START_X + 8 * SPACING;
    private static final double BOTTOM_Y = START_Y + 9 * SPACING;
    private static final double PIECE_SIZE = 32;


    public ChessBoardView(double width, double height, Game game) {
        super(width, height);
        this.game = game;
        this.board = game.getBoard();
        refresh();
        setOnMouseClicked(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();
            int x = (int) Math.round((mouseX - START_X) / SPACING);
            int y = (int) Math.round((mouseY - START_Y) / SPACING);
            if (x < 0 || x > 8 || y < 0 || y > 9) {
                return;
            }
            Position clickedPosition = new Position(x, y);
            Piece clickedPiece = board.getPiece(clickedPosition);
            if (selectedPosition == null) {
                if (clickedPiece == null) {
                    return;
                }
                if (clickedPiece.getSide() != game.getCurrentTurn()) {
                    return;
                }
                //First choose
                selectedPosition = clickedPosition;
                refresh();
                System.out.println("Selected: " + selectedPosition);
            } else {
                if (clickedPiece != null && clickedPiece.getSide() == game.getCurrentTurn()) {
                    selectedPosition = clickedPosition;
                    //Rechoose
                    System.out.println("Reselected: " + selectedPosition);
                    refresh();
                    return;
                }
                MoveResult result = game.move(selectedPosition, clickedPosition);
                System.out.println("MoveResult: " + result);
                if (result == MoveResult.SUCCESS) {
                    selectedPosition = null;
                    refresh();
                }
            }
        });
    }

    private void drawBoard() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.BLACK);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(Font.font(20));
        for (int x = 0; x < 9; x++) {
            //Column line
            double screenX = START_X + x * SPACING;
            if (x == 0 || x == 8) {
                //Each two Side
                gc.strokeLine(screenX, START_Y, screenX, BOTTOM_Y);
            } else {
                //Upside
                gc.strokeLine(screenX, START_Y, screenX, START_Y + 4 * SPACING);
                //Downside
                gc.strokeLine(screenX, START_Y + 5 * SPACING, screenX, BOTTOM_Y);
            }
        }
        for (int y =0; y < 10; y++){
            double screenY = START_Y + y * SPACING;
            //Row line
            gc.strokeLine(START_X,screenY,RIGHT_X,screenY);
        }

        //Diagonal-UP
        gc.strokeLine(START_X+ 3 *SPACING, START_Y ,START_X + 5 * SPACING, START_Y + 2 * SPACING);
        gc.strokeLine(START_X+ 5 *SPACING, START_Y ,START_X + 3 * SPACING, START_Y + 2 * SPACING);

        //Diagonal-Down
        gc.strokeLine(START_X+ 3 *SPACING, BOTTOM_Y ,START_X + 5 * SPACING, BOTTOM_Y - 2 * SPACING);
        gc.strokeLine(START_X+ 5 *SPACING, BOTTOM_Y ,START_X + 3 * SPACING, BOTTOM_Y - 2 * SPACING);

        gc.fillText("楚河", START_X + 2 * SPACING, START_Y + 4.5 * SPACING);
        gc.fillText("汉界", START_X + 6 * SPACING, START_Y + 4.5 * SPACING);
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
                    double screenX = START_X + x * SPACING;
                    double screenY = START_Y + y * SPACING;
                    if (position.equals(selectedPosition)) {
                        gc.setStroke(Color.BLUE);
                        gc.strokeOval(
                                screenX - PIECE_SIZE / 2 - 4,
                                screenY - PIECE_SIZE / 2 - 4,
                                PIECE_SIZE + 8,
                                PIECE_SIZE + 8
                        );
                    }
                    //PieceColor
                    if (piece.getSide() == Side.RED) {
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

    public void refresh() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        drawBoard();
        drawPieces();
    }
}
