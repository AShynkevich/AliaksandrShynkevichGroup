package com.epam.lab.mentoring.repository;

import com.epam.lab.mentoring.domain.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {
    List<Note> findByOwner(String owner);
    List<Note> findByTags(List<String> tags);
}
