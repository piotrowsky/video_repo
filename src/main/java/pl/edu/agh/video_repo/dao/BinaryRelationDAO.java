package pl.edu.agh.video_repo.dao;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import pl.edu.agh.video_repo.model.BinaryRelation;

public class BinaryRelationDAO extends AbstractDAO<BinaryRelation> {

    public BinaryRelationDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    
    public long createOrUpdate(BinaryRelation relation) {
        return persist(relation).getGlobalId();
    }
    
    public void delete(long id) {
        int result = currentSession()
                    .createQuery("DELETE BinaryRelation WHERE id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
        if(result <= 0) {
            throw new HibernateException("");
        }
    }
    
    public BinaryRelation findById(long id) {
        return get(id);
    }
}
