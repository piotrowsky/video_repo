/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.video_repo.services;

/**
 *
 * @author Karol
 */

import java.awt.image.BufferedImage;
import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.IContainer;
import java.io.File;
import java.io.InputStream;
import pl.edu.agh.video_repo.services.helpers.ImageSnapListener;

public class VideoToFramesService  {
//
//    public static void Convert(double secondsBetweenFrame, String inputFilePath, String outputFilePrefix){
//        IMediaReader mediaReader = ToolFactory.makeReader(inputFilePath);
//        mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
//        mediaReader.addListener(new ImageSnapListener(secondsBetweenFrame, outputFilePrefix));
//        while (mediaReader.readPacket() == null) ;
//
//    }

    public static void Convert(double secondsBetweenFrame, InputStream videoStream, String outputDir){
        
        File file = new File(outputDir);
        file.mkdir();
        
        IContainer container = IContainer.make();
        container.open(videoStream, null);
        
        IMediaReader mediaReader = ToolFactory.makeReader(container);
        mediaReader.addListener(new ImageSnapListener(secondsBetweenFrame, outputDir + "\\"));
        while (mediaReader.readPacket() == null);
    }
    
//    public static void main(String[] args) {
//        VideoToFramesService.Convert(0.04, "avi.avi", "frames");
//
//    }
}
