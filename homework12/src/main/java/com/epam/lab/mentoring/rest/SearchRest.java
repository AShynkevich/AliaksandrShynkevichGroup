package com.epam.lab.mentoring.rest;

import com.epam.lab.mentoring.domain.Note;
import com.epam.lab.mentoring.domain.Tag;
import com.epam.lab.mentoring.repository.NoteRepository;
import com.epam.lab.mentoring.repository.TagRepository;
import com.epam.lab.mentoring.repository.search.ISearchRepository;
import com.epam.lab.mentoring.rest.dto.TagDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchRest.class);

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ISearchRepository searchRepository;

    // owner -> tag -> all
    @GetMapping("/notes-rest/search")
    public List<Note> findNotesByOwner(@RequestParam(value = "owner", required = false) String owner,
                                       @RequestParam(value = "tag", required = false) List<String> tags) {
        if (owner != null) {
            LOGGER.info("Attempt to find all notes for owner: [{}].", owner);
            return noteRepository.findNotesAndTagsByOwner(owner);
        } else if (tags != null && !tags.isEmpty())  {
            LOGGER.info("Attempt to find notes by corresponding tags: [{}].", tags);
            return noteRepository.findNotesByTagsNameIn(tags);
        } else {
            LOGGER.info("Attempt to find all notes.");
            return noteRepository.findAll();
        }
    }

    // full text search
    @GetMapping("/notes-rest/search/custom")
    public List<Note> searchNotes(@RequestParam("criteria") String searchCriteria) {
        LOGGER.info("Attempt to perform full text search by criteria: [{}].", searchCriteria);
        return searchRepository.findNotesByCriteria(searchCriteria);
    }

    @GetMapping("/tags-rest/search")
    public List<Tag> findAll() {
        LOGGER.info("Attempt to find all tags.");
        return tagRepository.findAll();
    }
}
