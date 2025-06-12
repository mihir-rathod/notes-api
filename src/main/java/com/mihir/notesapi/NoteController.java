package com.mihir.notesapi;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/notes")
public class NoteController {

    // a simple in-memory list to store notes (will replace this later with a database)
    private List<Note> notes = new ArrayList<>();

    // GET API endpoint to display the list of all notes

    @GetMapping // responds to HTTP GET at /notes and returns all notes
    public List<Note> getAllNotes() {
        return notes;
    }

    // GET API endpoint to fetch the note by ID

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable int id) {
        for (Note note : notes){
            if (note.getId() == id){
                return ResponseEntity.ok(note);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // POST API endpoint to add new notes in the list of all nodes

    @PostMapping // responds to HTTP POST at /notes, reads a Note from the request body (JSON), adds it to the list, and returns it
    public Note addNote(@Valid @RequestBody Note note) {
        notes.add(note);
        return note;
    }

    // PUT API endpoint to update an existing note by ID in the list of all nodes

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable int id, @Valid @RequestBody Note updatedNote) {
        for (Note note : notes){
            if (note.getId() == id){
                note.setTitle(updatedNote.getTitle());
                note.setContent(updatedNote.getContent());
                return ResponseEntity.ok(note); // HTTP 200 (ok)
            }
        }
        return ResponseEntity.notFound().build(); // HTTP 404
    }

    // DELETE API endpoint to delete a note

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

    // GET API endpoint which can fetch a list of notes with matching titles of the notes.

    @GetMapping("/search") //
    public ResponseEntity<List<Note>> searchNotesByTitle(@RequestParam String title) {
        List<Note> searchResults = new ArrayList<>();
        for (Note note : notes){
            if (note.getTitle().toLowerCase().contains(title.toLowerCase())){
                searchResults.add(note);
            }
        }
        if (searchResults.isEmpty()){
            return ResponseEntity.noContent().build(); // HTTP 204 if nothing found
        }
        return ResponseEntity.ok(searchResults);
    }
}
