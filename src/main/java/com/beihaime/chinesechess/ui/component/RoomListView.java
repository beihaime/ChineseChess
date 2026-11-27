package com.beihaime.chinesechess.ui.component;

import com.beihaime.chinesechess.model.Room;
import com.beihaime.chinesechess.ui.ChessApplication;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class RoomListView {
    private final ScrollPane scrollPane = new ScrollPane();
    private final TilePane tilePane = new TilePane();
    private RoomCard card = null;

    private static final double WIDTH = 730;
    private static final double HEIGHT = 500;

    public RoomListView(Stage stage) {
        tilePane.setAlignment(Pos.TOP_LEFT);
        tilePane.setHgap(4);
        tilePane.setVgap(3);
        tilePane.setPrefColumns(1);
        tilePane.setPrefRows(4);
        tilePane.setPrefTileWidth(WIDTH);
        tilePane.setPrefTileHeight(150);
        if (ChessApplication.getRoomManager().getCurrentRoom() == null) {
            return;
        }
        card = new RoomCard(ChessApplication.getRoomManager().getCurrentRoom(),stage);
        createRoomCard(card);
    }

    public  TilePane getTitlePane() {
        return tilePane;
    }

    public void createRoomCard(RoomCard roomCard) {
        tilePane.getChildren().add(roomCard.view());
    }

    public void refreshRoomCard(Stage stage) {
        tilePane.getChildren().clear();

        Room room = ChessApplication.getRoomManager().getCurrentRoom();

        if (room == null) {
            card = null;
            return;
        }

        card = new RoomCard(room, stage);
        tilePane.getChildren().add(card.view());
    }

    public RoomCard getCard() {
        return card;
    }


}
