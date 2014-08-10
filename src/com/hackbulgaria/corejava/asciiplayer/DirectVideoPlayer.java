package com.hackbulgaria.corejava.asciiplayer;

import java.awt.image.BufferedImage;
import java.util.List;

public class DirectVideoPlayer extends VideoPlayer {
    public DirectVideoPlayer(String path) {
        super(path);
    }

    @Override
    public List<String> getMedia() {
        // Do nothing; this is a direct video player: it does not buffer the
        // video file
        return null;
    }

    @Override
    public void play(long millisecondsBetweenFrames) {
        frameDecoder = new FrameDecoder(file.getPath(), millisecondsBetweenFrames * 1000) {
            @Override
            public void onFrameCaptured(BufferedImage frame) {
                
                new ImagePlayer(frame).play();
            }
        };

        frameDecoder.start();
    }

    @Override
    public void play() {
        // TODO Auto-generated method stub
        play(MILLISECONDS_BETWEEN_FRAMES * 1000);
    }
}
