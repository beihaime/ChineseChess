package com.beihaime.chinesechess.ui.component;


import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ChessButton {
    private final Stage stage;

    public ChessButton(Stage stage) {
        this.stage = stage;
    }

    public final Button quitButton() {
        Button quit = new Button("Quit");
        quit.getStyleClass().add("quit-button");
        return quit;
    }

    public final Button undoButton() {
        Button undo = new Button("Undo");
        undo.getStyleClass().add("undo-button");
        return undo;
    }

    public final Button redoButton() {
        Button redo = new Button("Redo");
        redo.getStyleClass().add("redo-button");
        return redo;
    }
}
