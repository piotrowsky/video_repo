package pl.edu.agh.video_repo.model;

import java.io.Serializable;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

public class PropertyAbstract implements Serializable 
{
    @OneToMany(cascade = CascadeType.ALL)
//    @MapKey(name = "value")
    @MapKey(name = "id")
    @Column(nullable = true/*, name = "property"*/)
    private Map<String, MapProperty> properties;

    public Map<String, MapProperty> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, MapProperty> properties) {
        this.properties = properties;
    }
    
}
