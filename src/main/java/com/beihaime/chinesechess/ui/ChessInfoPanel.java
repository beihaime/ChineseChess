package com.beihaime.chinesechess.ui;

import com.beihaime.chinesechess.game.Game;
import com.beihaime.chinesechess.game.GameStatus;
import com.beihaime.chinesechess.model.Side;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ChessInfoPanel extends VBox {

    private final Game game;
    private final Label statusLabel = new Label();

    public ChessInfoPanel(Game game) {
        this.game = game;
        getStyleClass().add("info-panel");
        statusLabel.getStyleClass().add("status-label");
        getChildren().add(statusLabel);
        updateStatus();
    }

    public void updateStatus() {
        GameStatus status = game.getStatus();
        if (status == GameStatus.CHECK) {
            statusLabel.setText("将军！");
            return;
        }
        if (status == GameStatus.CHECKMATE) {
            String notification = (game.getWinner() == Side.RED) ? "红方胜利" : "黑方胜利";
            statusLabel.setText("将死！ " + notification);
            return;
        }
        if (status == GameStatus.STALEMATE) {
            statusLabel.setText("和棋！");
            return;
        }
        String currentSide = (game.getCurrentTurn() == Side.RED) ? "红方" : "黑方";
        statusLabel.setText("当前回合:" + currentSide);
    }
}