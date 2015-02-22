package pl.edu.agh.video_repo.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class RepositoryEntity implements Serializable {
    // Global id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    protected Long globalId;
    
    @OneToMany
    protected List<Property> properties;
    
    @OneToOne
    protected RepositoryEntityType type;

    public Long getGlobalId() {
        return globalId;
    }

    public void setGlobalId(Long globalId) {
        this.globalId = globalId;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public RepositoryEntityType getType() {
        return type;
    }

    public void setType(RepositoryEntityType type) {
        this.type = type;
    }
    
}
