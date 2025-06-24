package com.mihir.notesapi;

import com.mihir.notesapi.dto.NoteRequestDTO;
import com.mihir.notesapi.repository.NoteRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

//    // a simple in-memory list to store notes (will replace this later with a database)
//    private List<Note> notes = new ArrayList<>();
//
    private final NoteRepository noteRepository;

    public NoteController(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    // GET API endpoint to display the list of all notes

    @GetMapping // responds to HTTP GET at /notes and returns all notes
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    // GET API endpoint to fetch the note by ID

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        return noteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST API endpoint to add new notes in the list of all nodes

    @PostMapping // responds to HTTP POST at /notes, reads a Note from the request body (JSON), adds it to the list, and returns it
    public ResponseEntity<Note> addNote(@Valid @RequestBody NoteRequestDTO noteRequest) {
        Note note = new Note();
        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());
        Note savedNote = noteRepository.save(note);
        return ResponseEntity.ok(savedNote);
    }

    // PUT API endpoint to update an existing note by ID in the list of all nodes

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @Valid @RequestBody NoteRequestDTO updatedNoteRequest) {
        return noteRepository.findById(id)
                .map(existingNote -> {
                    existingNote.setTitle(updatedNoteRequest.getTitle());
                    existingNote.setContent(updatedNoteRequest.getContent());
                    Note updated = noteRepository.save(existingNote);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE API endpoint to delete a note

    @DeleteMapping("/{id}")
    public ResponseEntity<Note> deleteNote(@PathVariable Long id){
        if(noteRepository.existsById(id)) {
            noteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // GET API endpoint which can fetch a list of notes with matching titles of the notes.

    @GetMapping("/search") //
    public ResponseEntity<List<Note>> searchNotesByTitle(@RequestParam String title) {
        List<Note> results = noteRepository.findByTitleContainingIgnoreCase(title);
        if(results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(results);
    }
}
