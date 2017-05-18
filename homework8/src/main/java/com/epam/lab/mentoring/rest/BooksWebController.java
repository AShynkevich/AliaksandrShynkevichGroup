package com.epam.lab.mentoring.rest;

import com.epam.lab.mentoring.repository.filesystem.IBooksFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
public class BooksWebController {

    @Autowired
    private IBooksFileRepository fileRepository;

    @PostMapping("/upload")
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                fileRepository.writeFile(file.getName(), file.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/books-home");
    }

}
