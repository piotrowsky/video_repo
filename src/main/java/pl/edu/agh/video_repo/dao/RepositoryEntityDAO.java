package pl.edu.agh.video_repo.dao;


import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import pl.edu.agh.video_repo.model.BagElement;
import pl.edu.agh.video_repo.model.RepositoryEntity;

public class RepositoryEntityDAO extends AbstractDAO<RepositoryEntity>{

    public RepositoryEntityDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public RepositoryEntity findById(Long id) {
        return get(id);
    }

    public long createOrUpdate(RepositoryEntity repositoryEntity) {
        return persist(repositoryEntity).getGlobalId();
    }
}
