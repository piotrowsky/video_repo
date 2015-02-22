package pl.edu.agh.video_repo.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Bag extends RepositoryEntity {
    // maping przez BagELement
    @OneToMany
    private Set<RepositoryEntity> elements;

    public Set<RepositoryEntity> getElements() {
        return elements;
    }

    public void setElements(Set<RepositoryEntity> elements) {
        this.elements = elements;
    }
}
