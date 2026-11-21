package com.beihaime.chinesechess.ui.manager;

import javafx.scene.image.Image;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class SvgLoader {
    private final Image svgImage;

    public SvgLoader() throws TranscoderException {
        InputStream svgInputStream;
        svgInputStream = getClass().getClassLoader().getResourceAsStream("images/pieces/piece.svg");
        if (svgInputStream == null){
            throw new IllegalArgumentException("Cannot find resources");
        }

        TranscoderInput input = new TranscoderInput(svgInputStream);
        PNGTranscoder transcoder = new PNGTranscoder();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        TranscoderOutput output = new TranscoderOutput(outputStream);

        transcoder.transcode(input , output);

        ByteArrayInputStream pngInputStream= new ByteArrayInputStream(outputStream.toByteArray());

        svgImage= new Image(pngInputStream);
    }

    public Image getImage() {
        return svgImage;
    }


}
