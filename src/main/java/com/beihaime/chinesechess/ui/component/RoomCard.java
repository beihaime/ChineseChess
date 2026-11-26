package com.beihaime.chinesechess.ui.component;

import com.beihaime.chinesechess.model.Room;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RoomCard {
    private final VBox view;
    private final Room room;
    public RoomCard(Room room) {
        this.room = room;
        this.view = new VBox(5);

        HBox header = new HBox(500);
        GridPane information = new GridPane();

        information.setHgap(15);
        information.setVgap(10);

        view.getStyleClass().add("room-card");

        Label roomNameLabel = new Label( "Room: "+  room.getRoomName());
        Label hostAddressLabel = new Label("Host: "+ room.getHostAddress());
        Label portLabel = new Label("Port: "+ room.getPort());
        Label playerCountLabel = new Label("Players: " + room.getPlayerCount() +" / " + room.getMaxPlayers());
        Label roomStatus = new Label(room.getStatus().toString());

        roomNameLabel.getStyleClass().add("room-name");
        hostAddressLabel.getStyleClass().add("host-address");
        portLabel.getStyleClass().add("port");
        playerCountLabel.getStyleClass().add("player-count");
        switch (room.getStatus()) {
            case CLOSED -> roomStatus.getStyleClass().add("status-closed");
            case WAITING ->  roomStatus.getStyleClass().add("status-waiting");
            case PLAYING ->   roomStatus.getStyleClass().add("status-playing");
        }

        information.add(hostAddressLabel,0,0);
        information.add(portLabel, 1, 0);
        information.add(playerCountLabel, 0, 1);
        information.add(roomStatus, 1, 1);

        header.getChildren().addAll(roomNameLabel,roomStatus);

        view.getChildren().addAll(header, information);

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
