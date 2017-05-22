package com.epam.lab.mentoring.rest;

import com.epam.lab.mentoring.repository.filesystem.IBooksFileRepository;
import com.epam.lab.mentoring.repository.web.IBooksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
public class BooksRepositoryRestController {
    private static final Logger log = LoggerFactory.getLogger(BooksRepositoryRestController.class);

    @Autowired
    private IBooksFileRepository booksFileRepository;

    @DeleteMapping("/books/{id}")
    public @ResponseBody boolean delete(@PathVariable Long id) {
        log.info("Attempt to delete book by id: [{}]", id);
        booksFileRepository.delete(id);
        return true;
    }
}
