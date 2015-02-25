package pl.edu.agh.video_repo.resources;

import com.google.common.io.ByteStreams;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.HibernateException;
import pl.edu.agh.video_repo.dao.ResourceDAO;
import pl.edu.agh.video_repo.model.Resource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

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
            resource.setFileName(fileDetail.getFileName());
            id = dao.create(resource);
        } catch (IOException ex) {
            return Response.status(500).entity("Failed converting Resource to byte array").build();
        } catch (HibernateException ex) {
            return Response.status(500).entity("Failed persiting Resource entity").build();
        }

        return Response.status(200).entity(Long.toString(id)).build();
    }

    @GET
    @UnitOfWork
    @Path("/download/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getResource(@PathParam("id") long id) throws Exception {
        Resource resource = dao.findById(id);
        byte[] buffer2 = resource.getContent();
        String fileName = resource.getFileName();
        return Response.ok(buffer2, MediaType.APPLICATION_OCTET_STREAM)
                .header("content-disposition", "attachment; filename = " + fileName)
                .build();
    }
}
