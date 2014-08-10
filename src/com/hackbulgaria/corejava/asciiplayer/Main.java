package com.hackbulgaria.corejava.asciiplayer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            if (new File(args[0]).isFile()) {
                String contentType = Files.probeContentType(Paths.get(args[0]));
                String destination = args.length > 1 ? args[1] : null;
                FilePlayer player;

                switch (contentType) {
                    case "image/jpeg":
                    case "image/tiff":
                    case "image/tiff-fx":
                    case "image/bmp":
                    case "image/x-bmp":
                    case "image/png":
                    case "image/webp":
                        player = new ImagePlayer(args[0]);
                        break;
                    case "image/gif":
                        player = new GifPlayer(args[0]);
                        break;
                    case "video/webm":
                    case "video/x-matroska":
                    case "video/x-flv":
                    case "video/mp4":
                    case "video/ogg":
                    case "video/vnd.avi":
                    case "video/avi":
                    case "video/msvideo":
                    case "video/x-msvideo":
                    case "video/quicktime":
                    case "video/x-ms-wmv":
                    case "video/x-ms-asf":
                    case "video/mpeg":
                    case "video/x-m4v":
                    case "video/3gpp":
                    case "video/3gpp2":
                        player = args.length > 2 && args[2].equals("-buff") ?
                                new BufferedVideoPlayer(args[0]) : new DirectVideoPlayer(args[0]);
                        break;
                    default:
                        throw new Exception();
                }

                if (destination != null) {
                    Iterator<String> sequence = ((List<String>) player.getMedia()).iterator();
                    
                    try (PrintWriter out = new PrintWriter(new File(destination))) {
                        while (sequence.hasNext()) {
                            out.println(sequence.next());
                        }
                    }
                } else {
                    player.play();
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
