package com.hackbulgaria.corejava.asciiplayer;

import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

public class GifPlayer extends SequencePlayer {
    protected boolean isGifAnimated;

    public GifPlayer(String path) {
        super(path);

        ImageReader ir = ImageIO.getImageReadersByFormatName("gif").next();

        try {
            ir.setInput(ImageIO.createImageInputStream(file));
            int length = ir.getNumImages(true);
            isGifAnimated = length > 1;

            for (int i = 0; i < length; i++) {
                sequence.add(ir.read(i));
            }
        } catch (IOException e) {
            handleIOException(e);
        }
    }
}
