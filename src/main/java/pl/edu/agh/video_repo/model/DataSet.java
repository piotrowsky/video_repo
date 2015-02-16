package pl.edu.agh.video_repo.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class DataSet extends PropertyAbstract {
    
    @Id
    private String id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dataSet")
    private Set<Data> data;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dataSet")
    private Collection<Relation> relations;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dataSet")
    private Collection<Sequence> sequences;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<Data> getData() {
        return data;
    }

    public void setData(Set<Data> data) {
        this.data = data;
    }

    public Collection<Relation> getRelations() {
        return relations;
    }

    public void setRelations(Collection<Relation> relations) {
        this.relations = relations;
    }

    public Collection<Sequence> getSequences() {
        return sequences;
    }

    public void setSequences(Collection<Sequence> sequences) {
        this.sequences = sequences;
    }
    
}
