package pl.edu.agh.video_repo.resources;

import com.google.common.io.ByteStreams;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import io.dropwizard.hibernate.UnitOfWork;
import java.io.IOException;
import java.io.InputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.HibernateException;
import pl.edu.agh.video_repo.dao.ResourceDAO;
import pl.edu.agh.video_repo.model.Resource;

@Path("/resource")
public class ResourceEntityResource {

    private final ResourceDAO dao;

    public ResourceEntityResource(ResourceDAO dao) {
        this.dao = dao;
    }

    /*
        To upload resource by curl: 
        curl -F "file=@(path to local file)" http://localhost:8080/repo/resource/create
    */
    @POST
    @UnitOfWork
    @Path("/create")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response create(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {

        long id;
        
        try {
            Resource resource = new Resource(ByteStreams.toByteArray(uploadedInputStream));
            id = dao.create(resource);
        } catch(IOException ex) {
            return Response.status(500).entity("Failed converting Resource to byte array").build();
        } catch(HibernateException ex) {
            return Response.status(500).entity("Failed persiting Resource entity").build();
        }
        
        return Response.status(200).entity(Long.toString(id)).build();
    }

}
