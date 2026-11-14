package com.beihaime.chinesechess.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class MainMenuView {
    private static final double WINDOW_WIDTH = 1000;
    private static final double WINDOW_HEIGHT = 700;
    private final URL css = getClass().getResource("/css/chess.css");

    public MainMenuView(Stage stage) {
        stage.setTitle("Chinese Chess");
        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);

        BorderPane root = new BorderPane();


        VBox menu = new VBox(30);
        menu.setAlignment(Pos.CENTER);


        root.getStyleClass().add("main-container");

        Button buttonOffline = new Button("Offline Game");
        buttonOffline.setOnAction(event -> {
            ChessApplication.chess(stage);
        });

        Button buttonOnline = new Button("Online Game");
        buttonOnline.setOnAction(event -> {
            System.out.println("Starting Game online");
        });

        Label label = new Label("中国象棋");
        label.getStyleClass().add("title-label");

        BorderPane.setAlignment(label, Pos.CENTER);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        if (css == null) {
            throw new IllegalStateException("Cannot find css");
        }

        scene.getStylesheets().add(css.toExternalForm());

        buttonOffline.getStyleClass().add("start-button");
        buttonOnline.getStyleClass().add("start-button");

        menu.getChildren().addAll(buttonOffline, buttonOnline);

        root.setTop(label);
        root.setCenter(menu);

        stage.setScene(scene);
        stage.show();
    }
}
