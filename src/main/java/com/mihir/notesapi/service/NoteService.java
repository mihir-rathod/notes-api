package com.mihir.notesapi.service;

import com.mihir.notesapi.Note;
import com.mihir.notesapi.dto.NoteResponseDTO;
import com.mihir.notesapi.repository.NoteRepository;
import com.mihir.notesapi.dto.NoteRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Note getNoteById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note not found with id: " + id));
    }

    public Note createNote(NoteRequestDTO dto) {
        Note note = new Note();
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        return noteRepository.save(note);
    }

    public Note updateNote(Long id, NoteRequestDTO dto) {
        Note note = getNoteById(id);
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        return noteRepository.save(note);
    }

    public void deleteNote(Long id) {
        Note note = getNoteById(id);
        noteRepository.delete(note);
    }

    public List<Note> searchNotesByTitle(String title) {
        return noteRepository.findByTitleContainingIgnoreCase(title);
    }

    private NoteResponseDTO mapToResponseDTO(Note note) {
        NoteResponseDTO dto = new NoteResponseDTO();
        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setContent(note.getContent());
        dto.setCreatedAt(note.getCreatedAt());
        dto.setUpdatedAt(note.getUpdatedAt());
        return dto;
    }
}
