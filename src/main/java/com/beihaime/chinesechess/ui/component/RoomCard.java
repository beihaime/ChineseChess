package com.beihaime.chinesechess.ui.component;

import com.beihaime.chinesechess.model.Room;
import com.beihaime.chinesechess.ui.ChessApplication;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.apache.batik.transcoder.TranscoderException;

import java.io.IOException;

public record RoomCard(VBox view, Room room) {
    public RoomCard(Room room, Stage stage) {
        this(new VBox(5), room);

        ChessButton chessButton = new ChessButton(stage);

        HBox header = new HBox();
        GridPane information = new GridPane();
        ColumnConstraints firstColumn = new ColumnConstraints();
        firstColumn.setHgrow(Priority.ALWAYS);

        ColumnConstraints secondColumn = new ColumnConstraints();
        secondColumn.setMinWidth(100);
        secondColumn.setPrefWidth(100);
        secondColumn.setMaxWidth(100);

        information.getColumnConstraints().addAll(
                firstColumn,
                secondColumn
        );

        Separator separator = new Separator(Orientation.HORIZONTAL);

        information.setHgap(20);
        information.setVgap(15);

        view.getStyleClass().add("room-card");

        Label roomNameLabel = new Label("Room: " + room.getRoomName());
        Label hostAddressLabel = new Label("HOST ADDRESS:");
        Label hostAddress = new Label(room.getHostAddress());
        Label portLabel = new Label("PORT: ");
        Label port = new Label(String.valueOf(room.getPort()));
        Label playerCountLabel = new Label("PLAYERS: " + room.getPlayerCount() + " / " + room.getMaxPlayers());
        Label roomStatus = new Label(room.getStatus().toString());

        HBox.setHgrow(roomNameLabel, Priority.ALWAYS);
        roomNameLabel.setMaxWidth(Double.MAX_VALUE);
        roomNameLabel.setTextOverrun(OverrunStyle.ELLIPSIS);
        roomStatus.setMinWidth(Region.USE_PREF_SIZE);

        hostAddress.setMaxWidth(Double.MAX_VALUE);
        hostAddress.setTextOverrun(OverrunStyle.ELLIPSIS);

        Button enterButton = chessButton.EnterRoomButton();
        enterButton.setOnAction(event -> {
            if (ChessApplication.getRoomManager().getCurrentRoom() == null) {
                return;
            }
            try {
                //enter Lan server room
                ChessApplication.chess(stage);
            } catch (TranscoderException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        roomNameLabel.getStyleClass().add("room-name");
        separator.getStyleClass().add("room-separator");
        hostAddressLabel.getStyleClass().add("host-address");
        portLabel.getStyleClass().add("port");
        hostAddress.getStyleClass().add("room-info-value");
        port.getStyleClass().add("room-info-value");
        playerCountLabel.getStyleClass().add("player-count");
        switch (room.getStatus()) {
            case CLOSED -> roomStatus.getStyleClass().add("status-closed");
            case WAITING -> roomStatus.getStyleClass().add("status-waiting");
            case READY -> roomStatus.getStyleClass().add("status-ready");
            case PLAYING -> roomStatus.getStyleClass().add("status-playing");
        }

        information.add(hostAddressLabel, 0, 0);
        information.add(hostAddress, 0, 1);
        information.add(portLabel, 1, 0);
        information.add(port, 1, 1);
        information.add(playerCountLabel, 0, 2);
        information.add(enterButton, 1, 2);

        header.getChildren().addAll(roomNameLabel, roomStatus);

        view.getChildren().addAll(header, separator, information);

        view.setPadding(new Insets(10, 10, 10, 0));
        view.setSpacing(0);
    }
}
