package com.epam.lab.mentoring.repository.filesystem;

import com.epam.lab.mentoring.domain.Book;
import com.epam.lab.mentoring.repository.web.IBooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class BooksFileRepository implements IBooksFileRepository {

    @Value("${repository.filesystem.path}")
    private String fileDirectory;

    @Autowired
    private IBooksRepository dbRepository;

    @PostConstruct
    public void init() {
        this.listFiles().forEach(filename -> {
            Book book = new Book();
            book.setName(filename);

            dbRepository.save(book);
        });
    }

    @Override
    public List<String> listFiles() {
        return FileSystemUtils.listFiles(fileDirectory);
    }

    @Override
    public void writeFile(String filename, byte[] bytes) {
        Book book = new Book();
        book.setName(filename);

        dbRepository.save(book);

        FileSystemUtils.createFile(fileDirectory, filename, bytes);
    }
}
