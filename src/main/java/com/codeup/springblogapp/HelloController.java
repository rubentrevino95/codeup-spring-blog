package com.codeup.springblogapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
    public class HelloController {

//    @GetMapping("/") // mapping annotations maps the URL
//    @ResponseBody // Initiates the method
//    public String index() {
//        return "Index Page";
//    }

    @PostMapping("/upload")
    public void upload() {

    }

    @GetMapping("/hello/{name}")
    @ResponseBody
        public String sayHello(@PathVariable String name) {
            return "Hello " + name;
    }

    @GetMapping("/hi")
    @ResponseBody
    public String hello() {
        return "Hello";
    }

    @GetMapping("/hi/{name}")
    @ResponseBody
    public String sayHi(@PathVariable String name) {
        return "Hi, " + name;
    }

}
