/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.video_repo.resources;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import io.dropwizard.hibernate.UnitOfWork;
import java.io.InputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pl.edu.agh.video_repo.services.VideoToFramesService;

/**
 *
 * @author Karol
 */
@Path("/video")
public class VideoResource {

    public VideoResource(){

    }
    /*
     To upload resource by curl: 
     curl -F "file=@(path to local file)" http://localhost:8080/repo/video/upload
     curl -F "file=@(video1.avi)" http://localhost:8080/repo/video/upload
     */
    @POST
    @UnitOfWork
    @Path("/create")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response create(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {

        try {

            VideoToFramesService.Convert(0.04, uploadedInputStream, "frames");

//            
//            Resource resource = new Resource(ByteStreams.toByteArray(uploadedInputStream));
//            dao.create(resource);
        } catch (Exception ex) {
            return Response.status(500).entity("fail").build();
        }

        return Response.status(200).entity("OK").build();
    }
}
