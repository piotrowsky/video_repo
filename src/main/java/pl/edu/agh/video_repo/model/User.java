package pl.edu.agh.video_repo.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
//@Table(name = "user")
public class User implements Serializable {

    @Id
    private String id;

    //   @Column(name = "test", nullable = false)
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String password;
//    @OneToMany
//    private Collection<>
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Collection<Sequence> sequences;
    
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Sequence> getSequences() {
        return sequences;
    }

    public void setSequences(Collection<Sequence> sequences) {
        this.sequences = sequences;
    }

}
