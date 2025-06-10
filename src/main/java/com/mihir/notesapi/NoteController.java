package com.mihir.notesapi;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    // a simple in-memory list to store notes (will replace this later with a database)
    private List<Note> notes = new ArrayList<>();

    // created a GET API endpoint to the list of all notes

    @GetMapping // responds to HTTP GET at /notes and returns all notes
    public List<Note> getAllNotes() {
        return notes;
    }

    // created a POST API endpoint to add new notes in the list of all nodes

    @PostMapping // responds to HTTP POST at /notes, reads a Note from the request body (JSON), adds it to the list, and returns it
    public Note addNote(@RequestBody Note note) {
        notes.add(note);
        return note;
    }
}
