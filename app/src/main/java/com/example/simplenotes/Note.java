package com.example.simplenotes;

public class Note {
    private long id;
    private String title;
    private String content;
    private String storageType;

    public Note(long id, String title, String content, String storageType) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.storageType = storageType;
    }

    public Note(String title, String content, String storageType) {
        this(-1, title, content, storageType);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    @Override
    public String toString() {
        return title + "\n" + content + " (" + storageType + ")";
    }
}
