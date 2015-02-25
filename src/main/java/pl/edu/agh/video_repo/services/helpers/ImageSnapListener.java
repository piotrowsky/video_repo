/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.video_repo.services.helpers;

import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.Global;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Karol on 25.02.15.
 */
public class ImageSnapListener extends MediaListenerAdapter {

    // The video stream index, used to ensure we display frames from one and
    // only one video stream from the media container.
    private static int mVideoStreamIndex = -1;

    // Time of last frame write
    private static long mLastPtsWrite = Global.NO_PTS;

    private long microSecondsBetweenFrames;
    private String outputFilePrefix;

    public ImageSnapListener(double  secondsBetweenFrame, String outputFilePrefix){
        this.microSecondsBetweenFrames = (long)(Global.DEFAULT_PTS_PER_SECOND * secondsBetweenFrame);
        this.outputFilePrefix = outputFilePrefix;
    }

    public void onVideoPicture(IVideoPictureEvent event) {

        if (event.getStreamIndex() != mVideoStreamIndex) {
            // if the selected video stream id is not yet set, go ahead an
            // select this lucky video stream
            if (mVideoStreamIndex == -1)
                mVideoStreamIndex = event.getStreamIndex();
                // no need to show frames from this video stream
            else
                return;
        }

        // if uninitialized, back date mLastPtsWrite to get the very first frame
        if (mLastPtsWrite == Global.NO_PTS)
            mLastPtsWrite = event.getTimeStamp() - microSecondsBetweenFrames;

        // if it's time to write the next frame
        if (event.getTimeStamp() - mLastPtsWrite >=
                microSecondsBetweenFrames) {

            String outputFilename = dumpImageToFile(event.getImage());

            // indicate file written
            double seconds = ((double) event.getTimeStamp()) /
                    Global.DEFAULT_PTS_PER_SECOND;
//            System.out.printf(
//                    "at elapsed time of %6.3f seconds wrote: %s\n",
//                    seconds, outputFilename);

            // update last write time
            mLastPtsWrite += microSecondsBetweenFrames;
        }

    }

    private String dumpImageToFile(BufferedImage image) {
        try {
            String outputFilename = outputFilePrefix +
                    System.currentTimeMillis() + ".jpg";
            ImageIO.write(image, "jpg", new File(outputFilename));
            return outputFilename;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
