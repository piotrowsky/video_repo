package pl.edu.agh.video_repo.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class BagElement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    private Bag parent;
    
    @OneToOne
    private RepositoryEntity child;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bag getParent() {
        return parent;
    }

    public void setParent(Bag parent) {
        this.parent = parent;
    }

    public RepositoryEntity getChild() {
        return child;
    }

    public void setChild(RepositoryEntity child) {
        this.child = child;
    }
    
}
