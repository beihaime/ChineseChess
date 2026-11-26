package com.beihaime.chinesechess.ui.page;

import com.beihaime.chinesechess.ui.ChessApplication;
import com.beihaime.chinesechess.ui.component.ChessButton;
import com.beihaime.chinesechess.ui.component.RoomListView;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class RoomView {
    private static final String CSS_PATH = "/css/chess.css";
    private final RoomListView roomListView;

    public RoomView(Stage stage){
        stage.setTitle("Room Selector");

        ChessButton button = new ChessButton(stage);

        BorderPane root = new BorderPane();
        VBox controlPanel = new VBox(10);
        roomListView = new RoomListView();

        controlPanel.setAlignment(Pos.TOP_CENTER);

        Button quit = button.quitToHomeButton();
        Button createRoom = button.CreateRoomButton();

        quit.setOnAction(event -> {
            ChessApplication.mainMenu(stage);
        });

        createRoom.setOnAction(event -> {
            ChessApplication.createRoom(stage);
        });

        controlPanel.getChildren().addAll(createRoom,quit);

        URL css = getClass().getResource(CSS_PATH);
        if (css == null) {
            throw new IllegalStateException("Cannot find css");
        }
        Scene scene = new Scene(root);

        root.getStyleClass().add("main-container");

        root.setLeft(roomListView.getScrollPane());
        root.setRight(controlPanel);

        scene.getStylesheets().add(css.toExternalForm());

        stage.setScene(scene);

        stage.show();

    }
}
