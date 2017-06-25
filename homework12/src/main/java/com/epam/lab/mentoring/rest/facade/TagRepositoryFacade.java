package com.epam.lab.mentoring.rest.facade;

import com.epam.lab.mentoring.domain.Tag;
import com.epam.lab.mentoring.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagRepositoryFacade {

    @Autowired
    private TagRepository tagRepository;

    public Tag addTag(Tag tag) {
        Tag found = tagRepository.findByTag(tag.getTag().trim());
        if (null == found) {
            tag.setTag(tag.getTag().trim()); // remove spaces
            return tagRepository.insert(tag);
        }
        return found;
    }
}
