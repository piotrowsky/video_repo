package pl.edu.agh.video_repo.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class BinaryRelation extends RepositoryEntity {
    @OneToOne
    private RepositoryEntity subject;
    
    @OneToOne
    private RepositoryEntity object;

    public RepositoryEntity getSubject() {
        return subject;
    }

    public void setSubject(RepositoryEntity subject) {
        this.subject = subject;
    }

    public RepositoryEntity getObject() {
        return object;
    }

    public void setObject(RepositoryEntity object) {
        this.object = object;
    }
    
}
