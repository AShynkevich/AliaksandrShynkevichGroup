package com.epam.lab.mentoring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/notes")
    public String index() {
        return "index.html";
    }

    @GetMapping("/components")
    public String getComponent(String component) {
        // TODO: serve it with data already maybe?
        return "/components/" + component;
    }
}
