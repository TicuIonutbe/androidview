package com.wave.dagger.model;

public class Document {

    private int Id;


    private String name;

    private Member ownerId;

    private String type;

    private boolean visibility;

    private String path;

    public Document() {
    }

    public Document(int id, String name, Member ownerId, String type, boolean visibility, String path) {
        Id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.type = type;
        this.visibility = visibility;
        this.path = path;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Member getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Member ownerId) {
        this.ownerId = ownerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Document{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", ownerId=" + ownerId +
                ", type='" + type + '\'' +
                ", visibility=" + visibility +
                ", path='" + path + '\'' +
                '}';
    }
}
