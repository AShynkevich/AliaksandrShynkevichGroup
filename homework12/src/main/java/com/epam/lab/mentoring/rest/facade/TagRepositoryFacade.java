package com.epam.lab.mentoring.rest.facade;

import com.epam.lab.mentoring.domain.Tag;
import com.epam.lab.mentoring.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagRepositoryFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagRepositoryFacade.class);

    @Autowired
    private TagRepository tagRepository;

    public Tag addTag(Tag tag) {
        Tag found = tagRepository.findByTag(tag.getTag().trim());
        if (null == found) {
            tag.setTag(tag.getTag().trim()); // remove spaces
            return tagRepository.insert(tag);
        }
        return new Tag();
    }
}
