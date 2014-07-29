package com.hackbulgaria.corejava.asciiplayer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

public class GifPlayer extends SequencePlayer {
	public GifPlayer(String path) {
		super(path);
		
	    ImageReader ir = (ImageReader) ImageIO.getImageReadersByFormatName("gif").next();
	    
	    try {
			ir.setInput(ImageIO.createImageInputStream(file));
			
			for (int i = 0; i < ir.getNumImages(true); i++) {
				sequence.add(ir.read(i));
			}
		} catch (IOException e) {
			handleIOException(e);
		}
	}
}
