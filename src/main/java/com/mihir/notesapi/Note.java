package com.mihir.notesapi;

public class Note {
    // private means only this Note class can access or change this variable directly

    // this is like the meta data of any Note
    private long id;
    private String title;
    private String content;

    // Constructor - it is a special method to create and initialize objects in a class

    public Note (long id, String title, String content){
        this.id = id;
        this.title = title;
        this.content = content;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId() {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle() {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent() {
        this.content = content;
    }

}
