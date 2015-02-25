package pl.edu.agh.video_repo.dao;


import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import pl.edu.agh.video_repo.model.Property;
import pl.edu.agh.video_repo.model.PropertyKey;

public class PropertyKeyDAO extends AbstractDAO<PropertyKey>{

    public PropertyKeyDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public PropertyKey findById(Long id) {
        return get(id);
    }

    public long createOrUpdate(PropertyKey propertyKey) {
        return persist(propertyKey).getId();
    }
}
