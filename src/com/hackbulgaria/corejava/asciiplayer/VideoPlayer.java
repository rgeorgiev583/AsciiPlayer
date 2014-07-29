package com.hackbulgaria.corejava.asciiplayer;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;

public class VideoPlayer extends SequencePlayer {
	public VideoPlayer(String path) throws FrameGrabber.Exception {
		super(path);
		
		FrameGrabber g = new FFmpegFrameGrabber(file);
		
		g.start();

		while (g.) {
		    sequence.add(g.grab().getBufferedImage());
		}                                                                                                      

		g.stop();
	}
}
