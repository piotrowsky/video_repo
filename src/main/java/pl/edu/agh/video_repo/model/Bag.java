package pl.edu.agh.video_repo.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Bag extends RepositoryEntity {
    // maping przez BagELement
    @OneToMany
    private Set<BagElement> elements;

    public Set<BagElement> getElements() {
        return elements;
    }

    public void setElements(Set<BagElement> elements) {
        this.elements = elements;
    }
}
