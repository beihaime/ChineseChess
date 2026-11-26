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

    public final Button quitToHomeButton() {
        Button quit = new Button("QuitToHome");
        quit.getStyleClass().add("quit-to-home");
        return quit;
    }

    public final Button CreateRoomButton() {
        Button createRoom = new Button("CreateRoom");
        createRoom.getStyleClass().add("create-room-button");
        return createRoom;
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

    public final Button OfflineButton() {
        Button buttonOffline = new Button("Offline Game");
        buttonOffline.getStyleClass().add("start-button");
        return buttonOffline;
    }

    public final Button OnlineButton() {
        Button buttonOffline = new Button("Online Game");
        buttonOffline.getStyleClass().add("start-button");
        return buttonOffline;
    }

    public final Button quitGameButton() {
        Button quit = new Button("QuitGame");
        quit.getStyleClass().add("quitGame-button");
        return quit;
    }

    public final Button HostButton() {
        Button host = new Button("HostGame");
        host.getStyleClass().add("host-game");
        return host;
    }

    public final Button EnterRoomButton() {
        Button enterRoom = new Button("EnterRoom");
        enterRoom.getStyleClass().add("enter-room-button");
        return enterRoom;
    }

}
