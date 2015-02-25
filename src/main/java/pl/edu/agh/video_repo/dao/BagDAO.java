package pl.edu.agh.video_repo.dao;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import pl.edu.agh.video_repo.model.Bag;

public class BagDAO extends AbstractDAO<Bag>{

    public BagDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Bag findById(Long id) {
        return get(id);
    }

    public long createOrUpdate(Bag bag) {
        return persist(bag).getGlobalId();
    }
}
