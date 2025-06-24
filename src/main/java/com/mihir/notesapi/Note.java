package com.mihir.notesapi;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Title cannot be blank")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Content cannot be blank")
    @Column(nullable = false)
    private String content;

    // Constructor - it is a special method to create and initialize objects in a class

    public Note(){
    }

    // Getters and Setters
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

}
