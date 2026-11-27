package com.beihaime.chinesechess.ui.component;

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
    private static final double HEIGHT = 600;

    public RoomListView(Stage stage) {
        tilePane.setAlignment(Pos.TOP_LEFT);
        tilePane.setHgap(4);
        tilePane.setVgap(3);
        tilePane.setPrefColumns(1);
        tilePane.setPrefRows(4);
        tilePane.setPrefTileWidth(715);
        tilePane.setPrefTileHeight(150);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);

        scrollPane.setMinWidth(WIDTH);
        scrollPane.setMinHeight(HEIGHT);
        scrollPane.setMaxWidth(WIDTH);
        scrollPane.setMaxHeight(HEIGHT);

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setContent(tilePane);

        scrollPane.getStyleClass().add("room-scroll-pane");

        if (ChessApplication.getRoomManager().getCurrentRoom() == null) {
            return;
        }
        card = new RoomCard(ChessApplication.getRoomManager().getCurrentRoom(),stage);
        createRoomCard(card);
        refreshRoomCard(card);

    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public void createRoomCard(RoomCard roomCard) {
        tilePane.getChildren().add(roomCard.view());
    }

    public void refreshRoomCard(RoomCard roomCard) {
        tilePane.getChildren().clear();
        tilePane.getChildren().add(roomCard.view());
    }

    public RoomCard getCard() {
        return card;
    }


}
