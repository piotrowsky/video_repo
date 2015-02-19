package pl.edu.agh.video_repo.model;

import java.io.Serializable;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

@Embeddable
public class Properties implements Serializable {

    @OneToMany(cascade = CascadeType.ALL)
    @MapKey(name = "id")
    @Column(nullable = true)
    private Map<String, MapProperty> map;

    public Map<String, MapProperty> getMap() {
        return map;
    }

    public void setMap(Map<String, MapProperty> map) {
        this.map = map;
    }
    
}
