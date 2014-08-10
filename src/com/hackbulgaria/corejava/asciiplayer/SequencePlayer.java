package com.hackbulgaria.corejava.asciiplayer;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class SequencePlayer extends FilePlayer {
    public static final long MILLISECONDS_BETWEEN_FRAMES = 50;
    protected List<BufferedImage> sequence;

    public SequencePlayer(List<BufferedImage> sequence) {
        this.sequence = sequence;
    }

    public SequencePlayer(String path) {
        super(path);
    }

    public List<String> getMedia() {
        List<String> encodedSequence = new ArrayList<String>();

        for (BufferedImage item : sequence) {
            encodedSequence.add((new ImagePlayer(item)).getMedia());
        }

        return encodedSequence;
    }

    public void play(long millisecondsBetweenFrames) {
        for (BufferedImage item : sequence) {
            try {
                Thread.sleep(millisecondsBetweenFrames);
            } catch (InterruptedException e) {
                System.out.println("OOPS! Something went terribly wrong while sleeping between frames.");
                Thread.currentThread().interrupt();
            }

            
            new ImagePlayer(item).play();
        }
    }

    public void play() {
        play(MILLISECONDS_BETWEEN_FRAMES);
    }
}
