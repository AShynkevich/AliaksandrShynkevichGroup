package com.epam.lab.mentoring.repository;

import com.epam.lab.mentoring.domain.Note;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {
    List<Note> findNotesAndTagsByOwner(String owner);
    @Query("{ 'tags': { $elemMatch : { 'tag': { $in: ?0 } } } }")
    List<Note> findNotesByTagsNameIn(List<String> tags);
    List<Note> findAllBy(TextCriteria criteria);
}
