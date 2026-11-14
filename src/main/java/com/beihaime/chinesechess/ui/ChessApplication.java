package com.beihaime.chinesechess.ui;
import com.beihaime.chinesechess.game.Game;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class ChessApplication extends Application {
    private static final double WINDOW_WIDTH = 1000;
    private static final double WINDOW_HEIGHT = 700;
    private final URL css = getClass().getResource("/css/chess.css");

    @Override
    public void start(Stage stage) {
       mainMenu(stage);
    }

    public void mainMenu(Stage stage) {
        stage.setTitle("Chinese Chess");
        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);

        BorderPane root = new BorderPane();


        VBox menu = new VBox(30);
        menu.setAlignment(Pos.CENTER);


        root.getStyleClass().add("main-container");

        Button button = new Button("Start");
        button.setOnAction(event -> {
            chess(stage);
        });

        Label label = new Label("中国象棋");
        label.getStyleClass().add("title-label");

        BorderPane.setAlignment(label, Pos.CENTER);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        if (css == null) {
            throw new IllegalStateException("Cannot find css");
        }

        scene.getStylesheets().add(css.toExternalForm());

        button.getStyleClass().add("start-button");
        button.setPrefWidth(200);
        button.setPrefHeight(60);

        menu.getChildren().addAll(button);

        root.setTop(label);
        root.setCenter(menu);

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
        boardView.getStyleClass().add("board-container");

        Button buttonQuit = new Button("Quit");
        buttonQuit.getStyleClass().add("quit-button");
        buttonQuit.setOnAction(event -> {
            mainMenu(stage);
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

    public static void launchApp(String[] args) {
        launch(args);
    }
}
