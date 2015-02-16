package pl.edu.agh.video_repo.model;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Sequence extends PropertyAbstract {
    
    @Id
    private String id;
    
    @Column(nullable = false)
    private Long position;
//    @JoinColumn(name = "sequence_id", referencedColumnName="id")
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sequence")
    private Collection<Data> data;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sequence")
    private Collection<Relation> relations;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dataset_id")
    private DataSet dataSet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public Collection<Data> getData() {
        return data;
    }

    public void setData(Collection<Data> data) {
        this.data = data;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<Relation> getRelations() {
        return relations;
    }

    public void setRelations(Collection<Relation> relations) {
        this.relations = relations;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }
    
}
