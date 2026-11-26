package com.beihaime.chinesechess.ui.component;

import com.beihaime.chinesechess.model.Room;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RoomCard {
    private final VBox view;
    private final Room room;
    public RoomCard(Room room) {
        this.room = room;
        this.view = new VBox(5);

        HBox header = new HBox(400);
        GridPane information = new GridPane();

        Separator separator = new Separator(Orientation.HORIZONTAL);

        information.setHgap(300);
        information.setVgap(15);

        view.getStyleClass().add("room-card");

        Label roomNameLabel = new Label( "Room: "+  room.getRoomName());
        Label hostAddressLabel = new Label("HOST ADDRESS:");
        Label hostAddress = new Label(room.getHostAddress());
        Label portLabel = new Label("PORT: ");
        Label port = new Label(String.valueOf(room.getPort()));
        Label playerCountLabel = new Label("PLAYERS: " + room.getPlayerCount() +" / " + room.getMaxPlayers());
        Label roomStatus = new Label(room.getStatus().toString());

        roomNameLabel.getStyleClass().add("room-name");
        separator.getStyleClass().add("room-separator");
        hostAddressLabel.getStyleClass().add("host-address");
        portLabel.getStyleClass().add("port");
        hostAddress.getStyleClass().add("room-info-value");
        port.getStyleClass().add("room-info-value");
        playerCountLabel.getStyleClass().add("player-count");
        switch (room.getStatus()) {
            case CLOSED -> roomStatus.getStyleClass().add("status-closed");
            case WAITING ->  roomStatus.getStyleClass().add("status-waiting");
            case PLAYING ->   roomStatus.getStyleClass().add("status-playing");
        }

        information.add(hostAddressLabel,0,0);
        information.add(hostAddress, 0, 1);
        information.add(portLabel, 1, 0);
        information.add(port, 1, 1);
        information.add(playerCountLabel, 0, 2);

        header.getChildren().addAll(roomNameLabel,roomStatus);

        view.getChildren().addAll(header, separator, information);

        view.setPadding(new Insets(12));
        view.setSpacing(0);
    }

    public VBox getView() {
        return view;
    }

    public Room getRoom() {
        return room;
    }
}
