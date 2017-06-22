package com.epam.lab.mentoring.repository.search;

import com.epam.lab.mentoring.domain.Note;

import java.util.List;

public interface ISearchRepository {
    List<Note> findNotesByCriteria(String searchCriteria);
}
