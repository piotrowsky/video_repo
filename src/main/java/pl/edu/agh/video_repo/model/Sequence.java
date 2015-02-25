package pl.edu.agh.video_repo.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Sequence extends RepositoryEntity {
    // maping przez SequenceELement
    @OneToMany
    private List<SequenceElement> elements;

    public List<SequenceElement> getElements() {
        return elements;
    }

    public void setElements(List<SequenceElement> elements) {
        this.elements = elements;
    }
    
}
