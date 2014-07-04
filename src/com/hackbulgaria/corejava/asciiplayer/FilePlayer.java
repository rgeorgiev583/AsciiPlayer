package com.hackbulgaria.corejava.asciiplayer;

import java.io.File;
import java.io.IOException;

/**
 * Created by kimusubi on 14-7-3.
 */
public abstract class FilePlayer implements IPlayer {
	protected static final String newline = System.getProperty("line.separator");
    protected File file;

    public FilePlayer() {
    }
    
    public FilePlayer(String path) {
        file = new File(path);
    }
    
    protected void handleIOException(IOException e) {
    	System.out.println("OOPS! Something went terribly wrong while reading from the media file! We are really sorry :(");
    }
}
