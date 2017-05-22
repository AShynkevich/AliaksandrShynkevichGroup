package com.epam.lab.mentoring.repository.filesystem;

import com.epam.lab.mentoring.domain.Book;
import com.epam.lab.mentoring.mail.SendMailService;
import com.epam.lab.mentoring.repository.web.IBooksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Repository
public class BooksFileRepository implements IBooksFileRepository {
    private static final Logger log = LoggerFactory.getLogger(BooksFileRepository.class);

    @Value("${repository.filesystem.path}")
    private String fileDirectory;

    @Value("${mail.from}")
    private String from;

    @Value("${mail.to}")
    private String to;

    @Autowired
    private IBooksRepository dbRepository;

    @Autowired
    private SendMailService mailService;

    @PostConstruct
    public void init() {
        log.info("Initializing repository [{}] for the first time.", fileDirectory);
        List<String> newFiles = listFiles();
        newFiles.forEach(filename -> {
            dbRepository.save(new Book(filename));
        });
    }

    @Override
    public void writeFile(String filename, byte[] bytes) {
        if (FileSystemUtils.isFileExist(fileDirectory, filename)) {
            log.error("File [{}] already exist in directory [{}].", filename, fileDirectory);
            return;
        }

        Book book = new Book();
        book.setName(filename);

        dbRepository.save(book);
        FileSystemUtils.createFile(fileDirectory, filename, bytes);
        mailService.notifyUser(Collections.singletonList(filename), from, to);
    }

    @Override
    public List<String> listFiles() {
        return FileSystemUtils.listFiles(fileDirectory);
    }

    @Override
    public void delete(Long aLong) {
        Book book = dbRepository.findOne(aLong);
        if (null != book) {
            dbRepository.delete(book);
            FileSystemUtils.deleteFile(fileDirectory, book.getName());
        }
    }
}
