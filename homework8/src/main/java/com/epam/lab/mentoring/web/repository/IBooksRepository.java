package com.epam.lab.mentoring.web.repository;

import com.epam.lab.mentoring.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "books", path = "books-resource")
public interface IBooksRepository extends CrudRepository<Book, Long> {
}
