package com.epam.lab.mentoring.rest;

import com.epam.lab.mentoring.domain.Note;
import com.epam.lab.mentoring.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class NoteRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoteRest.class);

    @Autowired
    private NoteRepository noteRepository;

    @GetMapping("/notes-rest/note/{noteId}")
    public Note findNoteById(@PathVariable String noteId) {
        LOGGER.info("Attempt to find note by id: [{}].", noteId);
        return noteRepository.findOne(noteId);
    }

    @PostMapping("/notes-rest/note")
    public void addNote(@RequestBody Note note) {
        LOGGER.info("Attempt to insert note: [{}].", note);
        noteRepository.insert(note);
    }

    @PutMapping("/notes-rest/note")
    public Note updateNote(@RequestBody Note note) {
        LOGGER.info("Attempt to update note: [{}].", note);
        return noteRepository.save(note);
    }

    @DeleteMapping("/notes-rest/note/{noteId}")
    public void deleteNote(@PathVariable String noteId) {
        LOGGER.info("Attempt to delete note by id: [{}].", noteId);
        noteRepository.delete(noteId);
    }
}
