package com.epam.lab.mentoring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/notes")
    public String index() {
        return "index";
    }

}
