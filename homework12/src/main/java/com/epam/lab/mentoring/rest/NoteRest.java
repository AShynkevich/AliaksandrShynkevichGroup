package com.epam.lab.mentoring.rest;

import com.epam.lab.mentoring.domain.Note;
import com.epam.lab.mentoring.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/notes-rest")
public class NoteRest {

    @Autowired
    private NoteRepository noteRepository;

    @GetMapping("/search/owner")
    public List<Note> findNotesByOwner(@RequestParam("owner") String owner) {
        return null;
    }

    @GetMapping("/search/all")
    public List<Note> findNotes() {
        return null;
    }

    @GetMapping("/search/tags")
    public List<Note> findNotesByTags(@RequestParam("tag") List<String> tags) {
        return null;
    }

    @GetMapping("/search/noteId")
    public Note findNoteById(@PathVariable String noteId) {
        return null;
    }

    @PutMapping("/note")
    public Note updateNote(Note note) {
        return null;
    }

    @DeleteMapping("/note")
    public void deleteNote(@RequestParam("id") String id) {

    }

    // full text search
    @GetMapping("/search")
    public List<Note> searchNotes(@RequestParam("criteria") String searchCriteria) {
        return null;
    }
}
