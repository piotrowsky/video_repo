package pl.edu.agh.video_repo.resources;

import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.HibernateException;
import pl.edu.agh.video_repo.dao.RepositoryEntityDAO;
import pl.edu.agh.video_repo.dao.ResourceDAO;
import pl.edu.agh.video_repo.dao.SequenceDAO;
import pl.edu.agh.video_repo.dao.SequenceElementDAO;
import pl.edu.agh.video_repo.model.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Path("/sequence")
public class SequenceResource {

    private final SequenceDAO sequenceDAO;
    private final RepositoryEntityDAO repositoryEntityDAO;
    private final SequenceElementDAO sequenceElementDAO;

    public SequenceResource(SequenceDAO sequenceDAO, RepositoryEntityDAO repositoryEntityDAO, SequenceElementDAO sequenceElementDAO) {
        this.sequenceDAO = sequenceDAO;
        this.repositoryEntityDAO = repositoryEntityDAO;
        this.sequenceElementDAO = sequenceElementDAO;
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
            id = sequenceDAO.create(sequence);

        } catch (HibernateException ex) {
            return Response.status(500).entity("Failed persiting Sequence entity").build();
        }

        return Response.status(200).entity("Sequence with id " + id + " created").build();
    }

    @POST
    @UnitOfWork
    @Path("/addRepositoryEntity")
    public Response addRepositoryEntity(@FormParam("repoEntityID") long repoEntityID, @FormParam("sequenceID") long sequenceID,
                             @FormParam("beforeWhichOne") long beforeWhichOne) {

        try {
            Sequence sequence = sequenceDAO.findById(sequenceID);
            RepositoryEntity repositoryEntity = repositoryEntityDAO.findById(repoEntityID);
            SequenceElement sequenceElement = new SequenceElement();
            sequenceElement.setSequenceNumber(beforeWhichOne);
            sequenceElement.setChild(repositoryEntity);
            sequenceElement.setParent(sequence);
            updateSequenceElements(sequence.getElements(), beforeWhichOne);
            sequenceElementDAO.createOrUpdate(sequenceElement);
            sequence.getElements().add(sequenceElement);

        } catch (HibernateException ex) {
            return Response.status(500).entity("Failed adding frame to sequence").build();
        }

        return Response.status(200).entity("Repository Entity added to sequence").build();
    }

    @GET
    @UnitOfWork
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Long> getSequenceIds(@PathParam("id") Long id) {
        List<Long> ids = new LinkedList<>();
        try {
            Sequence sequence = sequenceDAO.findById(id);
            List<SequenceElement> sequenceElements = sequence.getElements();
            Collections.sort(sequenceElements,
                    (SequenceElement e1, SequenceElement e2) -> e1.getSequenceNumber().compareTo(e1.getSequenceNumber()));
            for(SequenceElement sequenceElement : sequenceElements){
                ids.add(sequenceElement.getChild().getGlobalId());
            }
        } catch (HibernateException ex) {
            return new LinkedList<>();
        }

        return ids;
    }

    private void updateSequenceElements(List<SequenceElement> elements, long beforeWhichOne) {
        for(SequenceElement element : elements) {
            if(element.getSequenceNumber() >= beforeWhichOne) {
                element.setSequenceNumber(element.getSequenceNumber()+1);
                sequenceElementDAO.createOrUpdate(element);
            }
        }
    }

}
