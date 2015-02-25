package pl.edu.agh.video_repo.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
public class Property implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    private PropertyKey key;
    
    private String value;
    
    @OneToMany
    private List<Property> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PropertyKey getKey() {
        return key;
    }

    public void setKey(PropertyKey key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Property> getChildren() {
        return children;
    }

    public void setChildren(List<Property> children) {
        this.children = children;
    }
    
}
