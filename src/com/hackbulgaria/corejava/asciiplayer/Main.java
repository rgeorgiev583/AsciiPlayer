package com.hackbulgaria.corejava.asciiplayer;

public class Main {
	public static void main(String[] args) {
		ImagePlayer ip = new ImagePlayer(args[0]);
		ip.play();
	}
}
