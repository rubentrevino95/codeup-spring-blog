package com.codeup.springblogapp.controllers;

import com.codeup.springblogapp.model.Post;
import com.codeup.springblogapp.model.User;
import com.codeup.springblogapp.repositories.PostRepository;
import com.codeup.springblogapp.repositories.UserRepository;
import com.codeup.springblogapp.services.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// GET = displaying info
// POST = submitting info
// Keys are referred in html tags
@Controller
public class PostController {

    private PostRepository postDao;
    private UserRepository userDao;
    private EmailService emailService;

    public PostController(UserRepository userDao, PostRepository postDao) {
        this.userDao = userDao;
        this.postDao = postDao;
    }


//  VIEW POSTS   //
    @GetMapping("/posts")
    public String index(Model model) {
        model.addAttribute("posts", postDao.findAll());
        return "posts/index";
    }

    @GetMapping("/show")
    public String showPost(@PathVariable long id, Model model) {
        model.addAttribute("post", postDao.getOne(1L));
        return "posts/show";
    }

    @GetMapping("posts/{id}")
    public String individualPost(Model model, @PathVariable long id) {
        Post aPost = postDao.getOne(id);

        model.addAttribute("title","View a Single Post");
        model.addAttribute("post", aPost);
        return "posts/show";
    }

//  CREATE  //
    @GetMapping("/posts/create")
    public String showCreateForm(Model model) {
        Post newPost = new Post();
        model.addAttribute("newPost", newPost);
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String submitCreatePost(@RequestParam String title, @RequestParam String description) {
        User user = userDao.getOne(1L);
        Post newPost = new Post();
        newPost.setTitle(title);
        newPost.setDescription(description);
        newPost.setUser(user);
        postDao.save(newPost);
        emailService.prepareAndSend(newPost,"You have created a new post.","Your post \""+newPost.getTitle());
        return "redirect:/posts";
}

//  EDIT    //
    @GetMapping("/posts/{id}/edit")
    public String getEditPostForm(@PathVariable long id, Model model) {
        Post aPost = postDao.getOne(id);
        model.addAttribute("post", aPost);
        return "posts/edit";
    }

    @PostMapping("/posts/{id}/edit")
    public String savePostEdit(@PathVariable long id, @RequestParam String title, @RequestParam String description) {
        Post editPost = postDao.getOne(id);
        User user = editPost.getUser();
        editPost.setTitle(title);
        editPost.setDescription(description);
        editPost.setUser(user);
        postDao.save(editPost);
        return "redirect:/posts/";
    }

//  DELETE  //
    @GetMapping("/posts/{id}/delete")
    public String getDeletePostForm(@PathVariable long id, Model model) {
        Post aPost = postDao.getOne(id);
        model.addAttribute("post", aPost);
        return "posts/delete";
        }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable long id) {
        Post aPost = postDao.getOne(id);
        return "redirect:/posts/" + id;
    }
//  SEARCH  //
    @GetMapping("/posts/search")
    public String searchPost(Model model) {
        Post post = postDao.findByTitle("tangible");
        model.addAttribute("post", post);
        return "posts/search";
    }

}
