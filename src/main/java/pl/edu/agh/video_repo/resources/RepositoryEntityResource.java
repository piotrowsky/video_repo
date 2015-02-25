package pl.edu.agh.video_repo.resources;

import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.HibernateException;
import pl.edu.agh.video_repo.dao.*;
import pl.edu.agh.video_repo.model.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Path("/repositoryEntity")
public class RepositoryEntityResource {

    private final RepositoryEntityDAO repositoryEntityDAO;
    private final PropertyKeyDAO propertyKeyDAO;
    private final PropertyDAO propertyDAO;


    public RepositoryEntityResource(RepositoryEntityDAO repositoryEntityDAO, PropertyKeyDAO propertyKeyDAO, PropertyDAO propertyDAO) {
        this.repositoryEntityDAO = repositoryEntityDAO;
        this.propertyKeyDAO = propertyKeyDAO;
        this.propertyDAO = propertyDAO;
    }

    @POST
    @UnitOfWork
    @Path("/addProperty")
    public Response addProperty(@FormParam("repoEntityID") long repositoryID, @FormParam("keyName") String keyName,
                           @FormParam("dataType") String dataType, @FormParam("value") String value) {
        try {
            RepositoryEntity repositoryEntity = repositoryEntityDAO.findById(repositoryID);

            PropertyKey propertyKey = new PropertyKey();
            propertyKey.setName(keyName);
            propertyKey.setDatatype(dataType);
            propertyKeyDAO.createOrUpdate(propertyKey);

            Property property = new Property();
            property.setValue(value);
            property.setKey(propertyKey);
            propertyDAO.createOrUpdate(property);

            repositoryEntity.getProperties().add(property);
            repositoryEntityDAO.createOrUpdate(repositoryEntity);
        } catch (HibernateException ex) {
            return Response.status(500).entity("Failed persiting Property entity").build();
        }

        return Response.status(200).entity("Added property").build();
    }

    @GET
    @UnitOfWork
    @Path("/getProperty/{repoEntityID}/{key}")
    public String getProperty(@PathParam("repoEntityID") long repositoryID, @PathParam("key") String key) {
        String value = "";
        try {
            RepositoryEntity repositoryEntity = repositoryEntityDAO.findById(repositoryID);
            List<Property> properties = repositoryEntity.getProperties();
            for (Property property : properties) {
                if(property.getKey().getName().equals(key)){
                    value = property.getValue();
                    break;
                }
            }
        } catch (HibernateException ex) {
            return "Error";
        }

        return value;
    }

    @GET
    @UnitOfWork
    @Path("/getKeys/{repoEntityID}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getKeys(@PathParam("repoEntityID") long repositoryID) {
        List<String> keys = new LinkedList<>();
        try {
            RepositoryEntity repositoryEntity = repositoryEntityDAO.findById(repositoryID);
            List<Property> properties = repositoryEntity.getProperties();
            for (Property property : properties) {
                keys.add(property.getKey().getName());
            }
        } catch (HibernateException ex) {
            List<String> error = new LinkedList<>();
            error.add(ex.getMessage());
            return error;
        }

        return keys;
    }

}
