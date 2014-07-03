package com.hackbulgaria.corejava.asciiplayer;

import java.io.File;

/**
 * Created by kimusubi on 14-7-3.
 */
public abstract class FilePlayer implements IPlayer {
    protected File file;

    public FilePlayer(String path) {
        file = new File(path);
    }
}
