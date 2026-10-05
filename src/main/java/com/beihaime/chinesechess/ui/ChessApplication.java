package com.beihaime.chinesechess.ui;
import com.beihaime.chinesechess.game.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ChessApplication extends Application {
    @Override
    public void start(Stage stage) {
        Game game = new Game();
        BorderPane root = new BorderPane();
        ChessBoardView boardView = new ChessBoardView(450, 500, game);
        root.setCenter(boardView);
        Scene scene = new Scene(root, 800 ,700);
        stage.setTitle("Chinese Chess");
        stage.setScene(scene);
        stage.show();
    }
    public static void launchApp(String[] args) {
        launch(args);
    }
}
