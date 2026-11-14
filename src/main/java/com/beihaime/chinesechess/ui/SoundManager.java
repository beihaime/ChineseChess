package com.beihaime.chinesechess.ui;

import javafx.scene.media.AudioClip;
import java.net.URL;

public class SoundManager {
    private final AudioClip selectSound;
    private final AudioClip moveSound;

    public SoundManager() {
        selectSound = loadSound("/sounds/select.wav");
        moveSound = loadSound("/sounds/select.wav");
    }

    private AudioClip loadSound(String path) {
        URL resource = getClass().getResource(path);

        if (resource == null) {
            throw new IllegalStateException(
                    "Sound resource not found: " + path
            );
        }

        return new AudioClip(resource.toExternalForm());
    }

    public void playSelect(double volume) {
        selectSound.play(volume);
    }

    public void playMove(double volume) {
        moveSound.play(volume);
    }
}
