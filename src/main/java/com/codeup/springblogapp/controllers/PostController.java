package com.codeup.springblogapp.controllers;

import com.codeup.springblogapp.model.Post;
import com.codeup.springblogapp.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// GET = displaying info
// POST = submitting info

@Controller
public class PostController {

    private PostRepository postRepo;


//  CONSTRUCTOR //
    public PostController(PostRepository postRepo) {

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
    public String showPostsIndexPage(Model model) {
        List<Post> postList = postRepo.findAll();
        model.addAttribute("posts", postList);
        return "posts/index";
    }

//  SINGLE POST PAGE    //
    @GetMapping("posts/{id}")
    public String individualPost(Model model, @PathVariable long id) {
        Post aPost = postRepo.getOne(id);
        model.addAttribute("title","View a Single Post");
        model.addAttribute("post", aPost);
        return "posts/show";
    }


//  VIEW CREATE POST PAGE   //
    @GetMapping("/posts/create")
    public String viewCreatePost() {
        return "posts/create";
    }

//  SUBMIT CREATE POSTS PAGE   //
    @PostMapping("/posts/create")
    public String submitCreatePost(@RequestParam(name = "title") String title, @RequestParam(name = "description") String description) {
        Post post = new Post();
        post.setTitle(title);
        post.setDescription(description);
        this.postRepo.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}/edit")
    public String getEditPostForm(@PathVariable long id, Model model) {
        Post aPost = postRepo.getOne(id);
        model.addAttribute("post", aPost);
        return "posts/edit";
    }

    @PostMapping("/posts/{id}/edit")
    public String savePostEdit(@PathVariable long id, @RequestParam String title, @RequestParam String description, Model model) {
        Post editPost = postRepo.getOne(id);
        editPost.setTitle(title);
        editPost.setDescription(description);
        postRepo.save(editPost);
        model.addAttribute("post", editPost);
        return "redirect:/posts/" + id;
    }

}
