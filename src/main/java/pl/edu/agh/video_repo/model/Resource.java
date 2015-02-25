package pl.edu.agh.video_repo.model;

import javax.persistence.Entity;

@Entity
public class Resource extends RepositoryEntity {
    // to jest Data
    private byte[] content;

    private String fileName;

    public Resource() {}

    public Resource(byte[] content) {
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] data) {
        this.content = data;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
