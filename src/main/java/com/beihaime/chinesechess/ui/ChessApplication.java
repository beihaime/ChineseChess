package com.beihaime.chinesechess.ui;
import com.beihaime.chinesechess.game.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;

public class ChessApplication extends Application {
    private final Label statusLabel = new Label();
    private final URL css = getClass().getResource("/css/chess.css");

    @Override
    public void start(Stage stage) {
       mainMenu(stage);
    }

    public void mainMenu(Stage stage) {
        stage.setTitle("Chinese Chess");
        stage.setWidth(1000);
        stage.setHeight(700);

        BorderPane root = new BorderPane();

        root.getStyleClass().add("main-container");

        Button button = new Button("Start");
        button.setOnAction(event -> {
            chess(stage);
        });

        Label label = new Label("中国象棋");
        label.setFont(Font.font(50));

        label.getStyleClass().add("label");

        Scene scene = new Scene(root, 1000, 700);

        if (css == null) {
            throw new IllegalStateException("Cannot find css");
        }

        scene.getStylesheets().add(css.toExternalForm());

        button.getStyleClass().add("button");

        button.setPrefWidth(200);
        button.setPrefHeight(60);

        root.setCenter(button);
        root.setTop(label);

        stage.setScene(scene);
        stage.show();

    }

    public void chess(Stage stage){
        Game game = new Game();
        BorderPane root = new BorderPane();
        root.getStyleClass().add("main-container");

        ChessBoardView boardView = new ChessBoardView(500, 500, game);
        boardView.setWidth(500);
        boardView.setHeight(500);
        boardView.getStyleClass().add("board-container ");

        Scene scene = new Scene(root, 850 ,700);
        if (css == null) {
            throw new IllegalStateException("Cannot find css");
        }

        scene.getStylesheets().add(css.toExternalForm());
        stage.setTitle("Chinese Chess");
        stage.setWidth(1000);
        stage.setHeight(700);
        stage.setMinWidth(900);
        stage.setMinHeight(650);


        ChessInfoPanel infoPanel = new ChessInfoPanel(game);

        infoPanel.getStyleClass().add("label");

        root.setLeft(boardView);
        root.setRight(infoPanel);

        boardView.setOnGameStateChanged(infoPanel::updateStatus);
        infoPanel.setPrefWidth(220);
        infoPanel.setPrefHeight(180);
        infoPanel.setMaxWidth(260);

        stage.setScene(scene);
        stage.show();
    }

    public static void launchApp(String[] args) {
        launch(args);
    }
}
