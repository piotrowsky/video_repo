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

@Path("/bag")
public class BagResource {

    private final BagDAO bagDAO;
    private final ResourceDAO resourceDAO;
    private final BagElementDAO bagElementDAO;


    public BagResource(ResourceDAO resourceDAO, BagDAO bagDAO, BagElementDAO bagElementDAO) {
        this.resourceDAO = resourceDAO;
        this.bagDAO = bagDAO;
        this.bagElementDAO = bagElementDAO;
    }

    @POST
    @UnitOfWork
    @Path("/create")
    public Response create() {
        long id;
        try {
            Bag bag = new Bag();
            bag.setType(RepositoryEntityType.BAG);
            id = bagDAO.createOrUpdate(bag);

        } catch (HibernateException ex) {
            return Response.status(500).entity("Failed persiting Bag entity").build();
        }

        return Response.status(200).entity("Bag with id " + id + " created").build();
    }

    @POST
    @UnitOfWork
    @Path("/addResource")
    public Response addFrame(@FormParam("resourceID") long resourceID, @FormParam("bagID") long bagID) {

        try {
            Bag bag = bagDAO.findById(bagID);
            Resource resource = resourceDAO.findById(resourceID);
            BagElement bagElement = new BagElement();
            bagElement.setChild(resource);
            bagElement.setParent(bag);
            bagElementDAO.createOrUpdate(bagElement);
            bag.getElements().add(bagElement);

        } catch (HibernateException ex) {
            return Response.status(500).entity("Failed adding frame to sequence").build();
        }

        return Response.status(200).entity("Added resource to bag").build();
    }

    @GET
    @UnitOfWork
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Long> getBagIds(@PathParam("id") Long id) {
        List<Long> ids = new LinkedList<>();
        try {
            Bag bag = bagDAO.findById(id);
            Set<BagElement> bagElements = bag.getElements();
            for(BagElement bagElement : bagElements){
                ids.add(bagElement.getChild().getGlobalId());
            }
        } catch (HibernateException ex) {
            return new LinkedList<>();
        }

        return ids;
    }

}
