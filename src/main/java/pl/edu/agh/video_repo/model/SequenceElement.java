package pl.edu.agh.video_repo.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SequenceElement implements Serializable {
    @Id
    private Long id;
    
    private Sequence parent;
    
    private RepositoryEntity child;
    
    private Long sequenceNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sequence getParent() {
        return parent;
    }

    public void setParent(Sequence parent) {
        this.parent = parent;
    }

    public RepositoryEntity getChild() {
        return child;
    }

    public void setChild(RepositoryEntity child) {
        this.child = child;
    }

    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
    
}
