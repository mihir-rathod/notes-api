package com.mihir.notesapi;

import org.springframework.http.ResponseEntity;
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

    // create a PUT API endpoint to update the existing notes in the list of all nodes

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable int id, @RequestBody Note updatedNote) {
        for (Note note : notes){
            if (note.getId() == id){
                note.setTitle(updatedNote.getTitle());
                note.setContent(updatedNote.getContent());
                return ResponseEntity.ok(note); // HTTP 200 (ok)
            }
        }
        return ResponseEntity.notFound().build(); // HTTP 404
    }

    // create a DELETE API endpoint (delete note)

    @DeleteMapping("/{id}")
    public ResponseEntity<Note> deleteNote(@PathVariable int id){

        // using a flag if its true that the note of that id exists, then remove it. If not exists give an error that this id doesn't exist.

        boolean removed = notes.removeIf(note -> note.getId() == id);
        if(removed){
            return ResponseEntity.noContent().build(); // HTTP 204
        }else{
            return ResponseEntity.notFound().build(); // HTTP 404
        }
    }
}
