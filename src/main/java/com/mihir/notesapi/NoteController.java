package com.mihir.notesapi;

import com.mihir.notesapi.dto.NoteRequestDTO;
import com.mihir.notesapi.dto.NoteResponseDTO;
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

    @GetMapping
    public ResponseEntity<List<NoteResponseDTO>> getAllNotes() {
        List<Note> notes = noteRepository.findAll();
        List<NoteResponseDTO> dtos = notes.stream().map(this::mapToDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    // GET API endpoint to fetch the note by ID

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> getNoteById(@PathVariable Long id) {
        return noteRepository.findById(id)
                .map(this::mapToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST API endpoint to add new notes in the list of all nodes

    @PostMapping
    public ResponseEntity<NoteResponseDTO> addNote(@Valid @RequestBody NoteRequestDTO noteRequest) {
        Note note = new Note();
        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());
        Note savedNote = noteRepository.save(note);
        return ResponseEntity.ok(mapToDTO(savedNote));
    }

    // PUT API endpoint to update an existing note by ID in the list of all nodes

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> updateNote(@PathVariable Long id, @Valid @RequestBody NoteRequestDTO updatedNoteRequest) {
        return noteRepository.findById(id)
                .map(existingNote -> {
                    existingNote.setTitle(updatedNoteRequest.getTitle());
                    existingNote.setContent(updatedNoteRequest.getContent());
                    Note updated = noteRepository.save(existingNote);
                    return ResponseEntity.ok(mapToDTO(updated));
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

    @GetMapping("/search")
    public ResponseEntity<List<NoteResponseDTO>> searchNotesByTitle(@RequestParam String title) {
        List<Note> results = noteRepository.findByTitleContainingIgnoreCase(title);
        if(results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<NoteResponseDTO> dtos = results.stream().map(this::mapToDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    private NoteResponseDTO mapToDTO(Note note) {
        NoteResponseDTO dto = new NoteResponseDTO();
        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setContent(note.getContent());
        dto.setCreatedAt(note.getCreatedAt());
        dto.setUpdatedAt(note.getUpdatedAt());
        return dto;
    }
}
