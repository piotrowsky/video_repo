package pl.edu.agh.video_repo.dao;


import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import pl.edu.agh.video_repo.model.Bag;
import pl.edu.agh.video_repo.model.BagElement;

public class BagElementDAO extends AbstractDAO<BagElement>{

    public BagElementDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public BagElement findById(Long id) {
        return get(id);
    }

    public long createOrUpdate(BagElement bagElement) {
        return persist(bagElement).getId();
    }
}
