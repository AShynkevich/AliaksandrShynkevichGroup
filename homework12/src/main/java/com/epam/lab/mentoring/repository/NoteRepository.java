package com.epam.lab.mentoring.repository;

import com.epam.lab.mentoring.domain.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteRepository extends MongoRepository<Note, String> {
}
