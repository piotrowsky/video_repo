/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.video_repo.resources;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import io.dropwizard.hibernate.UnitOfWork;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pl.edu.agh.video_repo.dao.ResourceDAO;
import pl.edu.agh.video_repo.dao.SequenceDAO;
import pl.edu.agh.video_repo.dao.SequenceElementDAO;
import pl.edu.agh.video_repo.model.RepositoryEntityType;
import pl.edu.agh.video_repo.model.Resource;
import pl.edu.agh.video_repo.model.Sequence;
import pl.edu.agh.video_repo.model.SequenceElement;
import pl.edu.agh.video_repo.services.VideoToFramesService;

/**
 *
 * @author Karol
 */
@Path("/video")
public class VideoResource {

    public static final String TEMP_DIR = "c://VideoRepo_Temp//";

    private final SequenceDAO sequenceDAO;
    private final ResourceDAO resourceDAO;
    private final SequenceElementDAO sequenceElementDAO;

    public VideoResource(SequenceDAO sequenceDAO, ResourceDAO resourceDAO, SequenceElementDAO sequenceElementDAO) {
        this.sequenceDAO = sequenceDAO;
        this.resourceDAO = resourceDAO;
        this.sequenceElementDAO = sequenceElementDAO;
    }
    /*
     To upload resource by curl: 
     curl -F "file=@(path to local file)" http://localhost:8080/repo/video/upload/{format_video}/{przerwy_pomiędzy_klatakami}
     curl -F "file=@video1.avi" http://localhost:8080/repo/video/upload/avi/0.5
     */

    @POST
    @UnitOfWork
    @Path("/upload/{extension}/{interval}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response create(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            @PathParam("extension") String extension,
            @PathParam("interval") double interval) {

        long sequenceId;

        try {
            //------------------------------
            // preparations
            //------------------------------
            String tempFolderName = UUID.randomUUID().toString();
            String path = TEMP_DIR + tempFolderName;
            String tempVideoPath = path + "//video." + extension;
            String tempFramesPath = path + "//frames";

            File tempFile = new File(path);
            tempFile.mkdirs();

            //------------------------------
            // temporary save movie on server
            //------------------------------
            OutputStream outputStream = new FileOutputStream(new File(tempVideoPath));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            if (outputStream != null) {
                outputStream.close();
            }

            //------------------------------
            // extract frames from movie
            //------------------------------
            VideoToFramesService.Convert(interval, tempVideoPath, tempFramesPath);

            //------------------------------
            // save each frame to database
            //------------------------------
            
            // create sequence
            Sequence sequence = new Sequence();
            sequence.setType(RepositoryEntityType.SEQUENCE);
            sequenceId = sequenceDAO.create(sequence);

            // add frames as resources to newly created sequence
            long frameNumber = 0;
            File tempFramesFolder = new File(tempFramesPath);
            for (final File fileEntry : tempFramesFolder.listFiles()) {
                if (!fileEntry.isDirectory()) {
                    // load file from input stream to byte array
                    byte[] bFile = new byte[(int) fileEntry.length()];
                    FileInputStream fileInputStream = new FileInputStream(fileEntry);
                    fileInputStream.read(bFile);
                    fileInputStream.close();
                    
                    Resource frameResource = new Resource(bFile);
                    resourceDAO.create(frameResource);
                    
                    SequenceElement sequenceElement = new SequenceElement();
                    sequenceElement.setSequenceNumber(frameNumber);
                    sequenceElement.setParent(sequence);
                    sequenceElement.setChild(frameResource);
                    
                    sequenceElementDAO.createOrUpdate(sequenceElement);
                    
                    frameNumber++;
                }
            }

        } catch (Exception ex) {
            return Response.status(500).entity("failed").build();
        }
        
        String msg = Objects.toString(sequenceId);
        
        return Response.status(200).entity(msg).build();
    }
}
