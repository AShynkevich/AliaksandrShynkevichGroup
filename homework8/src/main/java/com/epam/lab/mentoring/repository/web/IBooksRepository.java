package com.epam.lab.mentoring.repository.web;

import com.epam.lab.mentoring.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface IBooksRepository extends CrudRepository<Book, Long> {
}
