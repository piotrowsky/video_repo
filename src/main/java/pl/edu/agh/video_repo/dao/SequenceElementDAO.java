package pl.edu.agh.video_repo.dao;


import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import pl.edu.agh.video_repo.model.Sequence;
import pl.edu.agh.video_repo.model.SequenceElement;

public class SequenceElementDAO extends AbstractDAO<SequenceElement>{

    public SequenceElementDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public SequenceElement findById(Long id) {
        return get(id);
    }

    public long createOrUpdate(SequenceElement sequenceElement) {
        return persist(sequenceElement).getId();
    }
}
