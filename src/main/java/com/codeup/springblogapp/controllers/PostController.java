package com.codeup.springblogapp.controllers;

import com.codeup.springblogapp.model.Post;
import com.codeup.springblogapp.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// GET = displaying info
// POST = submitting info

@Controller
public class PostController {
    private PostRepository postRepo;


//  CONSTRUCTOR //
    public PostController(@Qualifier("postRepository") PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/show")
    public String show() {
        return "show";
    }

//    @GetMapping("/posts")
//    @ResponseBody
//    public String getPosts() {
//        String posts = "<ul>";
//        for (Post post : this.postRepo.findAll()) {
//            posts += "<li>"+post.getTitle() + " by " + post.getTitle() + "</li>";
//        }
//        posts += "</ul>";
//        return posts;
//    }

//  ALL POSTS   //
    @GetMapping("/posts")
    public String posts(Model model) {
        model.addAttribute("posts", postRepo.findAll());
        return "posts/index";
    }

//  SINGLE POST PAGE    //
    @GetMapping("posts/{id}")
    public String individualPost(Model model, @PathVariable long id) {
        model.addAttribute("post", postRepo.findById(id));
        return "posts/show";
    }

//  CREATE POSTS PAGE   //
    @PostMapping("/posts/create")
    public String createPost(@RequestParam(name = "title") String title, @RequestParam(name = "description") String description) {
        Post post = new Post();
        post.setTitle(title);
        post.setDescription(description);
        this.postRepo.save(post);
        return "redirect:/posts";
    }

}
