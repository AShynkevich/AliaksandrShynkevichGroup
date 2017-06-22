package com.epam.lab.mentoring.repository.search;

import com.epam.lab.mentoring.domain.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SearchRepository implements ISearchRepository {

    @Autowired
    private MongoTemplate template;

    @Override
    public List<Note> findNotesByCriteria(String searchCriteria) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matching(searchCriteria);
        Query query = TextQuery.queryText(criteria);
        return template.find(query, Note.class);
    }
}
