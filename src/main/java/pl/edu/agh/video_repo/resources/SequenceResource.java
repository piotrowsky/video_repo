package pl.edu.agh.video_repo.resources;

import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.HibernateException;
import pl.edu.agh.video_repo.dao.SequenceDAO;
import pl.edu.agh.video_repo.model.RepositoryEntity;
import pl.edu.agh.video_repo.model.RepositoryEntityType;
import pl.edu.agh.video_repo.model.Sequence;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.LinkedList;

@Path("/sequence")
public class SequenceResource {

    private final SequenceDAO dao;

    public SequenceResource(SequenceDAO dao) {
        this.dao = dao;
    }

    /*
        To create sequence by curl:
        curl http://localhost:8080/repo/sequence/create
    */
    @POST
    @UnitOfWork
    @Path("/create")
    public Response create() {
        long id;
        try {
            Sequence sequence = new Sequence();
            sequence.setType(RepositoryEntityType.SEQUENCE);
            sequence.setElements(new LinkedList<RepositoryEntity>());
            id = dao.create(sequence);

        } catch (HibernateException ex) {
            return Response.status(500).entity("Failed persiting Sequence entity").build();
        }

        return Response.status(200).entity("Sequence with id " + id + " created").build();
    }

}
