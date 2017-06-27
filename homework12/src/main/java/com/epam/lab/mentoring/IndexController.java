package com.epam.lab.mentoring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/notes")
    public String index() {
        return "index.html";
    }

    @GetMapping("/tags")
    public String tags() {
        return "tag.html";
    }
}
