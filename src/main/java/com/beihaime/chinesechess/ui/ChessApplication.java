package com.beihaime.chinesechess.ui;
import com.beihaime.chinesechess.ui.manager.RoomManager;
import com.beihaime.chinesechess.ui.page.*;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.batik.transcoder.TranscoderException;

import java.io.IOException;

public class ChessApplication extends Application {
    private static final double WINDOW_WIDTH = 1000;
    private static final double WINDOW_HEIGHT = 700;
    private static RoomManager roomManager;

    @Override
    public void start(Stage stage) {
        roomManager = new RoomManager();
        mainMenu(stage);
    }

    public static void mainMenu(Stage stage) {
        new MainMenuView(stage);
    }

    public static void chess(Stage stage) throws TranscoderException, IOException {
        new ChessGameView(stage);
    }

    public static void createRoom(Stage stage) {
        new CreateRoomView(stage);
    }

    public static void joinRoom(Stage stage) {
        new JoinRoomView(stage);
    }

    public static void Room(Stage stage) {
        new RoomView(stage);
    }

    public static void launchApp(String[] args) {
        launch(args);
    }

    public static double getWindowWidth() {
        return WINDOW_WIDTH;
    }

    public static double getWindowHeight() {
        return WINDOW_HEIGHT;
    }

    public static RoomManager getRoomManager() {
        return roomManager;
    }

}
