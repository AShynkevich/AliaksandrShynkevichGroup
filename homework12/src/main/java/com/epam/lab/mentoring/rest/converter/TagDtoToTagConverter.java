package com.epam.lab.mentoring.rest.converter;

import com.epam.lab.mentoring.domain.Tag;
import com.epam.lab.mentoring.rest.dto.TagDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TagDtoToTagConverter implements Converter<TagDto, Tag> {

    @Override
    public Tag convert(TagDto source) {
        Tag tag = new Tag();
        tag.setTag(source.getTag());
        tag.setTagId(source.getTagId());
        return tag;
    }
}
