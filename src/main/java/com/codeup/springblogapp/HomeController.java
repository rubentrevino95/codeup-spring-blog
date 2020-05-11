package com.codeup.springblogapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/") // mapping annotations maps the URL
    @ResponseBody // Initiates the method
    public String getMapping() {
        return "This is the landing page!";
    }

}
