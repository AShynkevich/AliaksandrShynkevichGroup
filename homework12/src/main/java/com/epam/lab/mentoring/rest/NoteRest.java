package com.epam.lab.mentoring.rest;

import com.epam.lab.mentoring.domain.Note;
import com.epam.lab.mentoring.repository.NoteRepository;
import com.epam.lab.mentoring.rest.dto.NoteDto;
import com.epam.lab.mentoring.rest.facade.NoteRepositoryFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

@RestController
public class NoteRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoteRest.class);

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NoteRepositoryFacade noteRepositoryFacade;

    @Autowired
    private ConversionService conversionService;

    @GetMapping("/notes-rest/note/{noteId}")
    public Note findNoteById(@PathVariable String noteId) {
        LOGGER.info("Attempt to find note by id: [{}].", noteId);
        return noteRepository.findOne(noteId);
    }

    @PostMapping("/notes-rest/note")
    public Note addNote(@RequestBody NoteDto noteDto) {
        LOGGER.info("Attempt to insert note: [{}].", noteDto);
        return noteRepositoryFacade.addNote(conversionService.convert(noteDto, Note.class));
    }

    @PutMapping("/notes-rest/note")
    public Note updateNote(@RequestBody NoteDto noteDto) {
        LOGGER.info("Attempt to update note: [{}].", noteDto.getTags());
        return noteRepository.save(conversionService.convert(noteDto, Note.class));
    }

    @DeleteMapping("/notes-rest/note/{noteId}")
    public void deleteNote(@PathVariable String noteId) {
        LOGGER.info("Attempt to delete note by id: [{}].", noteId);
        noteRepository.delete(noteId);
    }
}
