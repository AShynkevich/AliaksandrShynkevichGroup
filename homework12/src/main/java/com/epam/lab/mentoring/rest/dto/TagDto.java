package com.epam.lab.mentoring.rest.dto;

public class TagDto {
    private String tagId;
    private String tag;

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "TagDto{" +
                "tagId='" + tagId + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
