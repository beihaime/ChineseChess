package com.beihaime.chinesechess.ui.manager;

import com.beihaime.chinesechess.model.PieceType;
import com.beihaime.chinesechess.model.Side;
import javafx.scene.image.Image;
import org.apache.batik.transcoder.TranscoderException;

import java.io.IOException;
import java.util.EnumMap;

public class PieceImageManager {
    private final SvgLoader svgLoader = new SvgLoader();

    private static final String Piece = "images/pieces/piece.svg";

    private static final String PAWN_RED = "images/pieces/red/red-pawn.svg";
    private static final String PAWN_BLACK = "images/pieces/black/black-pawn.svg";

    private static final String ROOK_RED = "images/pieces/red/red-rook.svg";
    private static final String ROOK_BLACK = "images/pieces/black/black-rook.svg";

    private static final String HORSE_RED = "images/pieces/red/red-horse.svg";
    private static final String HORSE_BLACK = "images/pieces/black/black-horse.svg";

    private static final String GENERAL_RED = "images/pieces/red/red-general.svg";
    private static final String GENERAL_BLACK = "images/pieces/black/black-general.svg";

    private static final String ELEPHANT_RED = "images/pieces/red/red-elephant.svg";
    private static final String ELEPHANT_BLACK = "images/pieces/black/black-elephant.svg";

    private static final String CANNON_RED = "images/pieces/red/red-cannon.svg";
    private static final String CANNON_BLACK = "images/pieces/black/black-cannon.svg";

    private static final String ADVISOR_RED = "images/pieces/red/red-advisor.svg";
    private static final String ADVISOR_BLACK = "images/pieces/black/black-advisor.svg";

    private final EnumMap<Side, EnumMap<PieceType, Image>> pieceImages = new EnumMap<>(Side.class);

    public PieceImageManager() throws TranscoderException, IOException {
        EnumMap<PieceType, Image> red = new EnumMap<>(PieceType.class);
        EnumMap<PieceType, Image> black = new EnumMap<>(PieceType.class);

        pieceImages.put(Side.BLACK,black);
        pieceImages.put(Side.RED,red);

        red.put(PieceType.PAWN, svgLoader.load(PAWN_RED));
        black.put(PieceType.PAWN, svgLoader.load(PAWN_BLACK));

        red.put(PieceType.ROOK, svgLoader.load(ROOK_RED));
        black.put(PieceType.ROOK, svgLoader.load(ROOK_BLACK));

        red.put(PieceType.HORSE, svgLoader.load(HORSE_RED));
        black.put(PieceType.HORSE, svgLoader.load(HORSE_BLACK));

        red.put(PieceType.GENERAL, svgLoader.load(GENERAL_RED));
        black.put(PieceType.GENERAL, svgLoader.load(GENERAL_BLACK));

        red.put(PieceType.ELEPHANT, svgLoader.load(ELEPHANT_RED));
        black.put(PieceType.ELEPHANT, svgLoader.load(ELEPHANT_BLACK));

        red.put(PieceType.CANNON, svgLoader.load(CANNON_RED));
        black.put(PieceType.CANNON, svgLoader.load(CANNON_BLACK));

        red.put(PieceType.ADVISOR, svgLoader.load(ADVISOR_RED));
        black.put(PieceType.ADVISOR, svgLoader.load(ADVISOR_BLACK));

    }

    public final Image getTextImage(Side side, PieceType pieceType) {
        if (side == null) {
            throw new NullPointerException("Cannot recognize side");
        }

        if (pieceType == null) {
            throw new NullPointerException("Cannot recognize piece type");
        }

        EnumMap<PieceType, Image> pieceImageMap = pieceImages.get(side);
        Image image = pieceImageMap.get(pieceType);
        if (image == null) {
            throw new IllegalArgumentException("Can't find piece image for side " + side.name());
        }
        return image;

    }

    public final Image getPieceImage() throws TranscoderException, IOException {
        return svgLoader.load(Piece);
    }

}
