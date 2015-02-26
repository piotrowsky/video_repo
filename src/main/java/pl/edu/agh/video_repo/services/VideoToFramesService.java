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

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.ToolFactory;
import java.awt.image.BufferedImage;
import java.io.File;
import pl.edu.agh.video_repo.services.helpers.ImageSnapListener;

public class VideoToFramesService  {
 public static void Convert(double secondsBetweenFrame, String inputFilePath, String outputDir){
        
        File file = new File(outputDir);
        file.mkdir();
        
        IMediaReader mediaReader = ToolFactory.makeReader(inputFilePath);
        mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
        mediaReader.addListener(new ImageSnapListener(secondsBetweenFrame, outputDir + "\\"));
        while (mediaReader.readPacket() == null) ;

    }
}
