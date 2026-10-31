package com.beihaime.chinesechess.ui;
import com.beihaime.chinesechess.game.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class ChessApplication extends Application {
    @Override
    public void start(Stage stage) {
        chess(stage);
    }

    public void chess(Stage stage){
        Game game = new Game();
        BorderPane root = new BorderPane();
        ChessBoardView boardView = new ChessBoardView(500, 500, game);
        root.setCenter(boardView);
        root.getStyleClass().add("main-container");
        Scene scene = new Scene(root, 800 ,700);
        URL css = getClass().getResource("/css/chess.css");
        if (css == null) {
            throw new IllegalStateException("Cannot find css");
        }
        scene.getStylesheets().add(css.toExternalForm());
        stage.setTitle("Chinese Chess");
        stage.setScene(scene);
        stage.setMinHeight(600);
        stage.setMinWidth(700);
        stage.show();
    }

    public static void launchApp(String[] args) {
        launch(args);
    }
}
