package com.hackbulgaria.corejava.asciiplayer;

import java.awt.image.BufferedImage;

public class BufferedVideoPlayer extends VideoPlayer {
    public BufferedVideoPlayer(String path) {
        super(path);

        frameDecoder = new FrameDecoder(path, MILLISECONDS_BETWEEN_FRAMES * 1000) {
            @Override
            public void onFrameCaptured(BufferedImage frame) {
                sequence.add(frame);
            }
        };

        frameDecoder.start();
    }
}
