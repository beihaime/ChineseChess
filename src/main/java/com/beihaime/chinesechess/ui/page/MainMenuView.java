package com.beihaime.chinesechess.ui.page;

import com.beihaime.chinesechess.ui.ChessApplication;
import com.beihaime.chinesechess.ui.component.ChessButton;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.batik.transcoder.TranscoderException;

import java.net.URL;

public class MainMenuView {
    private static final double WINDOW_WIDTH = 1000;
    private static final double WINDOW_HEIGHT = 700;
    private static final String CSS_PATH = "/css/chess.css";

    public MainMenuView(Stage stage) {
        URL css = getClass().getResource(CSS_PATH);
        stage.setTitle("Chinese Chess");
        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);

        BorderPane root = new BorderPane();


        VBox menu = new VBox(30);
        menu.setAlignment(Pos.CENTER);

        root.getStyleClass().add("main-container");

        ChessButton controlPanel = new ChessButton(stage);

        Button buttonOffline = controlPanel.OfflineButton();
        Button buttonOnline = controlPanel.OnlineButton();
        Button buttonQuitGame = controlPanel.quitGameButton();

        buttonOffline.setOnAction(event -> {
            try {
                ChessApplication.chess(stage);
            } catch (TranscoderException e) {
                throw new RuntimeException(e);
            }
        });

        buttonOnline.setOnAction(event -> {
            System.out.println("Starting Game online");
        });

        buttonQuitGame.setOnAction(event -> {
            Platform.exit();
        });

        Label label = new Label("中国象棋");
        label.getStyleClass().add("title-label");

        BorderPane.setAlignment(label, Pos.CENTER);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        if (css == null) {
            throw new IllegalStateException("Cannot find css");
        }

        scene.getStylesheets().add(css.toExternalForm());

        menu.getChildren().addAll(buttonOffline, buttonOnline,buttonQuitGame);

        root.setTop(label);
        root.setCenter(menu);

        stage.setScene(scene);
        stage.show();
    }
}
