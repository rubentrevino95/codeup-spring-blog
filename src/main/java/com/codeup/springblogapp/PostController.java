package com.codeup.springblogapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostController {

    @GetMapping("posts")
    @ResponseBody
    public String posts() {
        return "posts index page";
    }

    @GetMapping("posts/{id}")
    @ResponseBody
    public String individualPost(@PathVariable long id) {
        return "showing Post" + id;
    }

    @GetMapping("posts/create")
    @ResponseBody
    public String createPost() {
        return "Viewing form for Post";
    }

    @PostMapping("posts/create")
    @ResponseBody
    public String submitCreatePost() {
        return "Create a new post";
    }

}
