package com.beihaime.chinesechess.ui.component;

import com.beihaime.chinesechess.model.Room;
import com.beihaime.chinesechess.ui.ChessApplication;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RoomListView {
    private final ScrollPane scrollPane = new ScrollPane();
    private final TilePane tilePane = new TilePane();
    private final BorderPane root;
    private RoomCard card = null;

    private static final double WIDTH = 730;
    private static final double HEIGHT = 500;

    public RoomListView(Stage stage) {
        root = new BorderPane();

        tilePane.setAlignment(Pos.TOP_LEFT);
        tilePane.setHgap(4);
        tilePane.setVgap(3);
        tilePane.setPrefColumns(1);
        tilePane.setPrefRows(4);
        tilePane.setPrefTileWidth(WIDTH);
        tilePane.setPrefTileHeight(150);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);

        scrollPane.setMinWidth(WIDTH);
        scrollPane.setMinHeight(HEIGHT);
        scrollPane.setMaxWidth(WIDTH);
        scrollPane.setMaxHeight(HEIGHT);

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setContent(new VBox(tilePane));

        scrollPane.getStyleClass().add("room-scroll-pane");

        root.setCenter(tilePane);
        root.setBottom(scrollPane);

        if (ChessApplication.getRoomManager().getCurrentRoom() == null) {
            return;
        }
        card = new RoomCard(ChessApplication.getRoomManager().getCurrentRoom(),stage);
        createRoomCard(card);
    }

    public BorderPane getView() {
        return root;
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
