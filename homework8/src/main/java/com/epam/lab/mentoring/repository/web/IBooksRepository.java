package com.epam.lab.mentoring.repository.web;

import com.epam.lab.mentoring.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface IBooksRepository extends CrudRepository<Book, Long> {
}
