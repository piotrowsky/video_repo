package pl.edu.agh.video_repo.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Relation implements Serializable {
    
    @Id
    private String id;
    
    @Embedded
    private Properties properties;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sequence_id")
    private Sequence sequence;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "child")
    private Collection<Data> parents;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
    private Collection<Data> children;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dataset_id")
    private DataSet dataSet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public Collection<Data> getParents() {
        return parents;
    }

    public void setParents(Collection<Data> parents) {
        this.parents = parents;
    }

    public Collection<Data> getChildren() {
        return children;
    }

    public void setChildren(Collection<Data> children) {
        this.children = children;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }
    
    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
    
}
