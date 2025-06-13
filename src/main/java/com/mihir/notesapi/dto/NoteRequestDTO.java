package com.mihir.notesapi.dto;

import jakarta.validation.constraints.NotBlank;

public class NoteRequestDTO {

    @NotBlank(message = "Title cannot be blank")
    private String title;     // private means only this Note class can access or change this variable directly

    @NotBlank(message = "Content cannot be blank")
    private String content;

    // Getters and Setters
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
