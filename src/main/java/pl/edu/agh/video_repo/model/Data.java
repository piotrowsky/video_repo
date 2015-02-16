package pl.edu.agh.video_repo.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Data extends PropertyAbstract {

    @Id
    private String id;

    @Lob
    private byte[] data;

//    @ElementCollection   
////    @OneToMany(cascade = CascadeType.ALL)
//    @MapKey(name = "key")
//    @Column(nullable = true, name = "value")
    //@CollectionOfElements(fetch = FetchType.LAZY)
//    @JoinTable(name = "JOINTABLE_NAME",
//        joinColumns = @JoinColumn(name = "id"))
//    @MapKey(name = "key_z_dupy")
//    @Column(name = "chuj")
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sequence_id")
    private Sequence sequence;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Relation parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    private Relation child;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dataset_id")
    private DataSet dataSet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public Relation getParent() {
        return parent;
    }

    public void setParent(Relation parent) {
        this.parent = parent;
    }

    public Relation getChild() {
        return child;
    }

    public void setChild(Relation child) {
        this.child = child;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }
    
}
