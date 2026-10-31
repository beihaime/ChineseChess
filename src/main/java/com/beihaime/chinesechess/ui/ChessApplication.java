package com.beihaime.chinesechess.ui;
import com.beihaime.chinesechess.game.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class ChessApplication extends Application {
    private final Label statusLabel = new Label();

    @Override
    public void start(Stage stage) {
        chess(stage);
    }

    public void chess(Stage stage){
        Game game = new Game();
        BorderPane root = new BorderPane();
        root.getStyleClass().add("main-container");
        URL css = getClass().getResource("/css/chess.css");
        ChessBoardView boardView = new ChessBoardView(500, 500, game);
        boardView.setWidth(500);
        boardView.setHeight(500);

        Scene scene = new Scene(root, 850 ,700);
        if (css == null) {
            throw new IllegalStateException("Cannot find css");
        }
        scene.getStylesheets().add(css.toExternalForm());
        stage.setTitle("Chinese Chess");
        stage.setScene(scene);
        stage.setMinWidth(700);
        stage.setMinHeight(650);
        stage.show();

        ChessInfoPanel infoPanel = new ChessInfoPanel(game);
        infoPanel.setPrefWidth(100);
        infoPanel.setPrefHeight(50);
        infoPanel.setMaxWidth(150);
        root.setCenter(boardView);
        root.setRight(infoPanel);

        boardView.setOnGameStateChanged(infoPanel::updateStatus);
    }

    public static void launchApp(String[] args) {
        launch(args);
    }
}
