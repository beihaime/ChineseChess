package com.beihaime.chinesechess.ui.page;

import com.beihaime.chinesechess.ui.ChessApplication;
import com.beihaime.chinesechess.ui.component.ChessButton;
import com.beihaime.chinesechess.ui.manager.RoomManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class CreateRoomView {
    private static final String CSS_PATH = "/css/chess.css";
    private final TextField roomNameField = new TextField();
    private final TextField portField = new TextField();
    private final TextField maxPlayersField = new TextField();

    public CreateRoomView(Stage stage) {
        URL css = getClass().getResource(CSS_PATH);

        ChessButton chessButton = new ChessButton(stage);

        stage.setTitle("Create a Room");

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Create a Room");

        initializeFields();

        Button host = chessButton.HostButton();
        Button cancel = chessButton.OnlineButton();
        cancel.setText("Cancel");

        host.setOnAction(event -> {
            if (!isValidInput()) {
                return;
            }
            String roomName = roomNameField.getText();
            int port = Integer.parseInt(portField.getText());
            int maxPlayers = Integer.parseInt(maxPlayersField.getText());
            RoomManager roomManager = ChessApplication.getRoomManager();
            try {
                roomManager.createRoom(roomName,port,maxPlayers);
                System.out.println("success");
                ChessApplication.Room(stage);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        cancel.setOnAction(event -> {
            ChessApplication.Room(stage);
        });

        root.getChildren().addAll(title,roomNameField,portField,maxPlayersField,host,cancel);

        Scene scene = new Scene(root);

        scene.getStylesheets().add(css.toExternalForm());

        stage.setScene(scene);
    }

    private void initializeFields() {
        roomNameField.getStyleClass().add("room-input");
        portField.getStyleClass().add("room-input");
        maxPlayersField.getStyleClass().add("room-input");

        roomNameField.setPromptText("Enter Room Name:");
        roomNameField.setAlignment(Pos.CENTER);

        portField.setPromptText("Enter Room Port:");
        portField.setAlignment(Pos.CENTER);

        maxPlayersField.setPromptText("Enter Room Max Players:");
        maxPlayersField.setAlignment(Pos.CENTER);

    }

    private boolean isValidInput() {
        String roomName = roomNameField.getText().trim();
        String portText = portField.getText().trim();
        String maxPlayersText = maxPlayersField.getText().trim();

        if (roomName.isEmpty() || portText.isEmpty() || maxPlayersText.isEmpty()) {
            return false;
        }

        try {
            int port = Integer.parseInt(portText);
            return port >= 1 && port <= 65535;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
