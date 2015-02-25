package pl.edu.agh.video_repo.dao;


import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import pl.edu.agh.video_repo.model.Property;

public class PropertyDAO extends AbstractDAO<Property>{

    public PropertyDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Property findById(Long id) {
        return get(id);
    }

    public long createOrUpdate(Property property) {
        return persist(property).getId();
    }
}
