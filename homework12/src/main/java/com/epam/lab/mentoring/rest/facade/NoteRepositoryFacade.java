package com.epam.lab.mentoring.rest.facade;

import com.epam.lab.mentoring.domain.Note;
import com.epam.lab.mentoring.domain.Tag;
import com.epam.lab.mentoring.repository.NoteRepository;
import com.epam.lab.mentoring.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteRepositoryFacade {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private TagRepository tagRepository;

    public Note addNote(Note note) {
        List<Tag> noteTags = filterTags(note.getTags());
        note.setTags(noteTags);
        return noteRepository.insert(note);
    }

    public Note updateNote(Note note) {
        List<Tag> noteTags = filterTags(note.getTags());
        note.setTags(noteTags);
        return noteRepository.save(note);
    }

    private List<Tag> filterTags(List<Tag> tags) {
        return tags.stream()
                .map(tag -> { return tagRepository.findByTag(tag.getTag().trim()); })
                .filter(tag -> tag != null)
                .map(tag -> {
                    String tagName = tag.getTag().trim();
                    tag.setTag(tagName);
                    return tag;
                })
                .distinct()
                .collect(Collectors.toList());
    }
}
