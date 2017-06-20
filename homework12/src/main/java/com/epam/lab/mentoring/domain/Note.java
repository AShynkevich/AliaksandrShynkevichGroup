package com.epam.lab.mentoring.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

public @Data
class Note {

    @Id
    private String noteId;
    private String owner;
    private String name;
    private String text;
    private List<String> tags;
    private LocalDate date;
}
