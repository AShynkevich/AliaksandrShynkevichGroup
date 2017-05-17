package com.epam.lab.mentoring.rest;

import com.epam.lab.mentoring.repository.filesystem.IBooksFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController("/books-rest")
public class BooksWebController {

    @Autowired
    private IBooksFileRepository fileRepository;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                fileRepository.writeFile(file.getName(), file.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/books-home";
    }

}
