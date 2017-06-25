package com.epam.lab.mentoring.repository;

import com.epam.lab.mentoring.domain.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TagRepository extends MongoRepository<Tag, String> {
    Tag findByTag(String tag);
}
