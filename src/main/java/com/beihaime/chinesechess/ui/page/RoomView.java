package com.beihaime.chinesechess.ui.page;

import com.beihaime.chinesechess.ui.ChessApplication;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class RoomView {
    private static final String CSS_PATH = "/css/chess.css";

    public RoomView(Stage stage){
        stage.setTitle("Room Selector");

        BorderPane root = new BorderPane();
        VBox controlPanel = new VBox(10);

        controlPanel.setAlignment(Pos.BOTTOM_CENTER);

        Button quit = new Button("Main Menu");
        quit.getStyleClass().add("quitToHome");
        quit.setOnAction(event -> {
            ChessApplication.mainMenu(stage);
        });

        controlPanel.getChildren().addAll(quit);

        URL css = getClass().getResource(CSS_PATH);
        if (css == null) {
            throw new IllegalStateException("Cannot find css");
        }
        Scene scene = new Scene(root);

        root.getStyleClass().add("main-container");

        root.setRight(controlPanel);


        scene.getStylesheets().add(css.toExternalForm());

        stage.setScene(scene);

        stage.show();

    }
}
