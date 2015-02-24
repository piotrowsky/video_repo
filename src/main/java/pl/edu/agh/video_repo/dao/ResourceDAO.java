package pl.edu.agh.video_repo.dao;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import pl.edu.agh.video_repo.model.Resource;

public class ResourceDAO extends AbstractDAO<Resource> {

    public ResourceDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public long create(Resource resource) {
        return persist(resource).getGlobalId();
    }
}
