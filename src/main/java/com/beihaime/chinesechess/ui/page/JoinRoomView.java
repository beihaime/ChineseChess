package com.beihaime.chinesechess.ui.page;

import com.beihaime.chinesechess.ui.ChessApplication;
import com.beihaime.chinesechess.ui.component.ChessButton;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class JoinRoomView {
    private static final String CSS_PATH = "/css/chess.css";
    private final TextField ipField = new TextField();
    private final TextField portField = new TextField();

    public JoinRoomView(Stage stage) {
        URL css = getClass().getResource(CSS_PATH);

        ChessButton chessButton = new ChessButton(stage);

        stage.setTitle("Join a Room");

        initializeFields();

        Button cancel = chessButton.JoinRoomButton();
        cancel.setText("Cancel");
        cancel.setOnAction(event -> {
            ChessApplication.Room(stage);
        });

        Button join = chessButton.JoinRoomButton();
        join.setOnAction(event -> {
            String ip = ipField.getText();
            int port = Integer.parseInt(portField.getText());
            try {
                ChessApplication.getRoomManager().joinRoom(ip,port);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        VBox root = new VBox(10);

        root.setAlignment(Pos.CENTER);

        root.getChildren().addAll(ipField, portField,join , cancel);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(css.toExternalForm());

        stage.setScene(scene);
        stage.show();

    }

    private void initializeFields() {
        ipField.getStyleClass().add("room-input");
        ipField.setPromptText("Enter IP Address");
        portField.getStyleClass().add("room-input");
        portField.setPromptText("Enter Port");

    }
}
