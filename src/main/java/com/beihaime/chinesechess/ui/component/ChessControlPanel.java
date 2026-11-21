package com.beihaime.chinesechess.ui.component;


import com.beihaime.chinesechess.game.Game;
import com.beihaime.chinesechess.ui.ChessApplication;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ChessControlPanel {
    private final Stage stage;

    public ChessControlPanel(Stage stage) {
        this.stage = stage;
    }

    public final Button quitButton() {
        Button quit = new Button("Quit");
        quit.getStyleClass().add("quit-button");
        quit.setOnAction(event -> {
            ChessApplication.mainMenu(stage);
        });
        return quit;
    }

    public final Button undoButton(Game game, ChessBoardView boardView, ChessInfoPanel infoPanel) {
        Button undo = new Button("Undo");
        undo.getStyleClass().add("undo-button");
        undo.setOnAction(event -> {
            game.undo();
            boardView.refresh();
            infoPanel.updateStatus();
        });
        return undo;
    }
}
