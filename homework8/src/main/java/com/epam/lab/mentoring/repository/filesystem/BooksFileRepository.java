package com.epam.lab.mentoring.repository.filesystem;

import com.epam.lab.mentoring.domain.Book;
import com.epam.lab.mentoring.repository.web.IBooksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
public class BooksFileRepository implements IBooksFileRepository {
    private static final Logger log = LoggerFactory.getLogger(BooksFileRepository.class);

    @Value("${repository.filesystem.path}")
    private String fileDirectory;

    @Autowired
    private IBooksRepository dbRepository;

    @PostConstruct
    public void init() {
        log.info("Initializing repository [{}] for the first time.", fileDirectory);
        List<String> newFiles = this.listFiles();
        newFiles.forEach(filename -> {
            dbRepository.save(new Book(filename));
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
