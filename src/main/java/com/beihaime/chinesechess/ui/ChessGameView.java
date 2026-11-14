package com.beihaime.chinesechess.ui;

import com.beihaime.chinesechess.game.Game;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class ChessGameView {
    private static final double WINDOW_WIDTH = 1000;
    private static final double WINDOW_HEIGHT = 700;
    private static final String CSS_PATH = "/css/chess.css";


    public ChessGameView(Stage stage) {
        Game game = new Game();
        BorderPane root = new BorderPane();
        URL css = this.getClass().getResource(CSS_PATH);

        root.getStyleClass().add("main-container");

        ChessBoardView boardView = new ChessBoardView(500, 500, game);
        boardView.setWidth(500);
        boardView.setHeight(500);
        boardView.getStyleClass().add("board-container");

        Button buttonQuit = new Button("Quit");
        buttonQuit.getStyleClass().add("quit-button");
        buttonQuit.setOnAction(event -> {
            ChessApplication.mainMenu(stage);
        });
        Scene scene = new Scene(root, 850 ,WINDOW_HEIGHT);
        if (css == null) {
            throw new IllegalStateException("Cannot find css");
        }

        scene.getStylesheets().add(css.toExternalForm());
        stage.setTitle("Chinese Chess");
        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);
        stage.setMinWidth(900);
        stage.setMinHeight(650);

        ChessInfoPanel infoPanel = new ChessInfoPanel(game);

        boardView.setOnGameStateChanged(infoPanel::updateStatus);

        infoPanel.setPrefWidth(220);
        infoPanel.setPrefHeight(180);
        infoPanel.setMaxWidth(260);

        root.setLeft(boardView);
        root.setRight(infoPanel);
        root.setBottom(buttonQuit);

        stage.setScene(scene);
        stage.show();

    }
}
