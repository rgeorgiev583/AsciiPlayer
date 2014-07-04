package com.hackbulgaria.corejava.asciiplayer;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class SequencePlayer extends FilePlayer {
	public static final long SLEEP_MS = 50;
	protected List<BufferedImage> sequence;
	
	public SequencePlayer(List<BufferedImage> _sequence) {
		sequence = _sequence;
	}
	
	public SequencePlayer(String path) {
		super(path);
	}
	
	public List<String> played() {
		List<String> enc_sequence = new ArrayList<String>();
		
		for (BufferedImage item: sequence) {
			enc_sequence.add((new ImagePlayer(item)).played());
		}
		
		return enc_sequence;
	}
	
	public void play(long sleep_ms) {
		for (BufferedImage item: sequence) {
			try {
				Thread.sleep(sleep_ms);
			} catch (InterruptedException e) {
				System.out.println("OOPS! Something went terribly wrong while sleeping between frames.");
				Thread.currentThread().interrupt();
			}
			
			(new ImagePlayer(item)).play();
		}
	}
	
	public void play() {
		play(SLEEP_MS);
	}
}
