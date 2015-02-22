package pl.edu.agh.video_repo.model;

import javax.persistence.Entity;

@Entity
public class Resource extends RepositoryEntity {
    // to jest Data
    private Byte[] data;

    public Byte[] getData() {
        return data;
    }

    public void setData(Byte[] data) {
        this.data = data;
    }
    
}
