package pl.edu.agh.video_repo.resources;

import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.HibernateException;
import pl.edu.agh.video_repo.dao.BagDAO;
import pl.edu.agh.video_repo.dao.ResourceDAO;
import pl.edu.agh.video_repo.dao.SequenceDAO;
import pl.edu.agh.video_repo.dao.SequenceElementDAO;
import pl.edu.agh.video_repo.model.*;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/bag")
public class BagResource {

    private final BagDAO bagDAO;
    private final ResourceDAO resourceDAO;


    public BagResource(ResourceDAO resourceDAO, BagDAO bagDAO) {
        this.resourceDAO = resourceDAO;
        this.bagDAO = bagDAO;
    }

    @POST
    @UnitOfWork
    @Path("/create")
    public Response create() {
        long id;
        try {
            Bag bag = new Bag();
            bag.setType(RepositoryEntityType.BAG);
            id = bagDAO.create(bag);

        } catch (HibernateException ex) {
            return Response.status(500).entity("Failed persiting Bag entity").build();
        }

        return Response.status(200).entity("Bag with id " + id + " created").build();
    }

}
