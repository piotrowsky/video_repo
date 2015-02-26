package pl.edu.agh.video_repo.resources;

import com.sun.media.jfxmedia.logging.Logger;
import io.dropwizard.hibernate.UnitOfWork;
import static java.awt.event.PaintEvent.UPDATE;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.hibernate.HibernateException;
import pl.edu.agh.video_repo.dao.BinaryRelationDAO;
import pl.edu.agh.video_repo.dao.RepositoryEntityDAO;
import pl.edu.agh.video_repo.model.BinaryRelation;
import pl.edu.agh.video_repo.model.RepositoryEntity;
import pl.edu.agh.video_repo.model.RepositoryEntityType;

@Path("/binaryRelation")
public class BinaryRelationResource {

    private final RepositoryEntityDAO repositoryEntityDAO;
    private final BinaryRelationDAO binaryRelationDAO;

    public BinaryRelationResource(RepositoryEntityDAO repositoryEntityDAO, BinaryRelationDAO binaryRelationDAO) {
        this.repositoryEntityDAO = repositoryEntityDAO;
        this.binaryRelationDAO = binaryRelationDAO;
    }

    /*
     To create BinaryRelation:
     curl -X POST -d "subjectID=X&objectID=Y" http://localhost:8080/repo/binaryRelation/create
     */
    @POST
    @UnitOfWork
    @Path("/create")
    public Response create(@FormParam("subjectID") long subjectID, @FormParam("objectID") long objectID) {

        long id;

        try {
            RepositoryEntity subject = repositoryEntityDAO.findById(subjectID);
            RepositoryEntity object = repositoryEntityDAO.findById(objectID);

            BinaryRelation binaryRelation = new BinaryRelation();
            binaryRelation.setSubject(subject);
            binaryRelation.setObject(object);
            binaryRelation.setType(RepositoryEntityType.BINARY_RELATION);
            id = binaryRelationDAO.createOrUpdate(binaryRelation);
        } catch (HibernateException ex) {
            return Response.status(500).entity("Failed persiting BinaryRelation entity").build();
        }

        return Response.status(200).entity(Long.toString(id)).build();
    }

    /*
     To delete BinaryRelation:
     curl -X DELETE -d "relationID=17" http://localhost:8080/repo/binaryRelation/delete
     */
    @DELETE
    @UnitOfWork
    @Path("/delete")
    public Response delete(@FormParam("relationID") long relationID) {

        long id;

        try {
            binaryRelationDAO.delete(relationID);
        } catch (HibernateException ex) {
            return Response.status(500).entity("Failed deleting BinaryRelation entity").build();
        }

        return Response.status(200).entity("OK").build();
    }

    @POST
    @UnitOfWork
    @Path("/update")
    public Response update(@FormParam("relationID") long relationID,
            @FormParam("subjectID") long subjectID, @FormParam("objectID") long objectID) {

        long id;

        try {
            RepositoryEntity subject = repositoryEntityDAO.findById(subjectID);
            RepositoryEntity object = repositoryEntityDAO.findById(objectID);

            BinaryRelation binaryRelation = binaryRelationDAO.findById(relationID);
            binaryRelation.setSubject(subject);
            binaryRelation.setObject(object);
            id = binaryRelationDAO.createOrUpdate(binaryRelation);
        } catch (HibernateException ex) {
            return Response.status(500).entity("Failed updating BinaryRelation entity").build();
        }

        return Response.status(200).entity(Long.toString(id)).build();
    }

}
