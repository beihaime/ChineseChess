package com.beihaime.chinesechess.ui.page;

import com.beihaime.chinesechess.ui.ChessApplication;
import com.beihaime.chinesechess.ui.component.ChessButton;
import com.beihaime.chinesechess.ui.component.RoomListView;
import com.beihaime.chinesechess.ui.manager.RoomManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class RoomView {
    private static final String CSS_PATH = "/css/chess.css";
    private final RoomListView roomListView;
    private final RoomManager roomManager = ChessApplication.getRoomManager();

    public RoomView(Stage stage){
        stage.setTitle("Room Selector");

        ChessButton button = new ChessButton(stage);

        BorderPane root = new BorderPane();
        VBox controlPanel = new VBox(10);
        roomListView = new RoomListView(stage);

        controlPanel.setAlignment(Pos.TOP_CENTER);

        Button quit = button.quitToHomeButton();
        Button createRoom = button.CreateRoomButton();
        Button joinRoom = button.JoinRoomButton();

        quit.setOnAction(event -> {
            try {
                roomManager.closeCurrentRoom();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ChessApplication.mainMenu(stage);
        });

        createRoom.setOnAction(event -> {
            ChessApplication.createRoom(stage);
        });

        joinRoom.setOnAction(event -> {
            ChessApplication.joinRoom(stage);
        });

        controlPanel.getChildren().addAll(createRoom,joinRoom,quit);

        URL css = getClass().getResource(CSS_PATH);
        if (css == null) {
            throw new IllegalStateException("Cannot find css");
        }
        Scene scene = new Scene(root);

        root.getStyleClass().add("main-container");

        root.setLeft(roomListView.getView());
        root.setRight(controlPanel);

        scene.getStylesheets().add(css.toExternalForm());

        stage.setScene(scene);

        stage.show();

        roomManager.setOnRoomChanged( () -> refreshRoomlist(stage));
    }

    public void refreshRoomlist(Stage stage) {
        roomListView.refreshRoomCard(stage);
    }

}
