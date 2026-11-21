package com.beihaime.chinesechess.ui;
import com.beihaime.chinesechess.ui.page.ChessGameView;
import com.beihaime.chinesechess.ui.page.MainMenuView;
import javafx.application.Application;
import javafx.stage.Stage;

public class ChessApplication extends Application {
    @Override
    public void start(Stage stage) {
       mainMenu(stage);
    }

    public static void mainMenu(Stage stage) {
        new MainMenuView(stage);
    }

    public static void chess(Stage stage){
        new ChessGameView(stage);
    }

    public static void launchApp(String[] args) {
        launch(args);
    }
}
