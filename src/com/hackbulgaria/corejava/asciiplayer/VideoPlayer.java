package com.hackbulgaria.corejava.asciiplayer;

public abstract class VideoPlayer extends SequencePlayer {
    protected static FrameDecoder frameDecoder;

    public VideoPlayer(String path) {
        super(path);
    }
}
