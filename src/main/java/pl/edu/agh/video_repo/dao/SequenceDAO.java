package pl.edu.agh.video_repo.dao;


import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import pl.edu.agh.video_repo.model.Sequence;

public class SequenceDAO extends AbstractDAO<Sequence>{

    public SequenceDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Sequence findById(Long id) {
        return get(id);
    }

    public long create(Sequence sequence) {
        return persist(sequence).getGlobalId();
    }
}
