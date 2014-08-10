/** This abstract class describes how video frames are retrieved (decoded and captured) from a video file
 *    and provides an interface to the user for processing them as individual images. */

/*******************************************************************************
 * Copyright (c) 2008, 2010 Xuggle Inc.    All rights reserved.
 *    
 * This file is part of Xuggle-Xuggler-Main.
 *
 * Xuggle-Xuggler-Main is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Xuggle-Xuggler-Main is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Xuggle-Xuggler-Main.    If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
/// modified by radoslav

package com.hackbulgaria.corejava.asciiplayer;

import java.awt.image.BufferedImage;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.Global;

/**
 * Using {@link IMediaReader}, takes a media container, finds the first video
 * stream, decodes that stream, and then writes video frames out to a PNG image
 * file every 5 seconds, based on the video presentation timestamps.
 *
 * @author aclarke
 * @author trebor
 * @author radoslav
 */

public abstract class FrameDecoder extends MediaListenerAdapter {
    /** Time of last frame write. */

    private static long mLastPtsWrite = Global.NO_PTS;

    /**
     * The video stream index, used to ensure we display frames from one and
     * only one video stream from the media container.
     */

    private int mVideoStreamIndex = -1;

    /**
     * The number of microseconds between frames.
     */

    protected IMediaReader reader;
    protected long microsecondsBetweenFrames;

    /**
     * Construct a FrameDecoder which reads and captures frames from a video
     * file.
     * 
     * @param filename
     *            the name of the media file to read
     * @param microsecondsBetweenFrames
     *            number of microseconds between each frame of the video
     */

    public FrameDecoder(String filename, long microsecondsBetweenFrames) {
        this.microsecondsBetweenFrames = microsecondsBetweenFrames;

        // create a media reader for processing video

        reader = ToolFactory.makeReader(filename);

        // stipulate that we want BufferedImages created in BGR 24bit color
        // space
        reader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);

        // note that DecodeAndCaptureFrames is derived from
        // MediaReader. ListenerAdapter and thus may be added as a listener
        // to the MediaReader. DecodeAndCaptureFrames implements
        // onVideoPicture().

        reader.addListener(this);
    }

    /**
     * Starts the frame decoding and capturing process.
     */

    public void start() {
        // read out the contents of the media file, note that nothing else
        // happens here. action happens in the onVideoPicture() method
        // which is called when complete video pictures are extracted from
        // the media source

        while (reader.readPacket() == null)
            ;
    }

    /**
     * Called after a video frame has been decoded from a media stream.
     * Optionally a BufferedImage version of the frame may be passed if the
     * calling {@link IMediaReader} instance was configured to create
     * BufferedImages.
     * 
     * This method blocks, so return quickly. !!! Do not use in external
     * classes. This is an internal implementation event that fires the
     * onFrameCaptured event on its own.
     */

    public void onVideoPicture(IVideoPictureEvent event) {
        try {
            // if the stream index does not match the selected stream index,
            // then have a closer look

            if (event.getStreamIndex() != mVideoStreamIndex) {
                // if the selected video stream id is not yet set, go ahead an
                // select this lucky video stream

                if (mVideoStreamIndex == -1)
                    mVideoStreamIndex = event.getStreamIndex();

                // otherwise return, no need to show frames from this video
                // stream

                else
                    return;
            }

            // if uninitialized, backdate mLastPtsWrite so we get the very
            // first frame

            if (mLastPtsWrite == Global.NO_PTS)
                mLastPtsWrite = event.getTimeStamp() - microsecondsBetweenFrames;

            // if it's time to process the next frame

            if (event.getTimeStamp() - mLastPtsWrite >= microsecondsBetweenFrames) {
                // process frame

                onFrameCaptured(event.getImage());

                // update last write time

                mLastPtsWrite += microsecondsBetweenFrames;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The implementation of this method should process each frame (display it,
     * store it, etc.) in order for the whole video to be manipulated
     * frame-by-frame. Each image is passed as a BufferedImage instance.
     */

    public abstract void onFrameCaptured(BufferedImage frame);
}
