package pl.edu.agh.video_repo.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class MapProperty implements Serializable {
    
    @Id
    private String id;

    //   @Column(value = "test", nullable = false)
    @Lob
    @Column(nullable = false)
    private byte[] value;

    public MapProperty() {
    } 
    
    public MapProperty(byte[] value) {
        this.value = value;
    }
    
    public MapProperty(String id, byte[] value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] name) {
        this.value = name;
    }
    
}
