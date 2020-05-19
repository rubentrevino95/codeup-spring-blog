package com.codeup.springblogapp.controllers;

import com.codeup.springblogapp.model.Post;
import com.codeup.springblogapp.model.User;
import com.codeup.springblogapp.repositories.PostRepository;
import com.codeup.springblogapp.repositories.UserRepository;
import com.codeup.springblogapp.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String showCreateForm() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser != null) {
            return "posts/create";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/posts/create")
    public String submitCreatePost(@RequestParam String title, @RequestParam String description) {
        Post createdPost = new Post();
        createdPost.setTitle(title);
        createdPost.setDescription(description);
        //below is new with authentication exercise
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        createdPost.setUser(loggedInUser);
        //createdPost.setUser(userDao.getOne(1l)); //this was before creating dynamic user
        postDao.save(createdPost);
        return "redirect:/posts";
}

//  EDIT    //
    @GetMapping("/posts/{id}/edit")
    public String getEditPostForm(@PathVariable long id, Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser.getId() == postDao.getOne(id).getUser().getId()) {
            Post postToEdit = postDao.getOne(id);
            model.addAttribute("post", postToEdit);
            return "posts/edit";
        } else {
            return "redirect:/posts";
        }
    }

    @PostMapping("/posts/{id}/edit")
    public String savePostEdit(@PathVariable long id, @RequestParam String title, @RequestParam String description) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser.getId() == postDao.getOne(id).getUser().getId()) {
            postDao.deleteById(id);
        } //can add an else notification if have time
        return "redirect:/posts";
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
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser.getId() == postDao.getOne(id).getUser().getId()) {
            postDao.deleteById(id);
        } //can add an else notification if have time
        return "redirect:/posts";
    }
//  SEARCH  //
    @GetMapping("/posts/search")
    public String searchPost(Model model) {
        Post post = postDao.findByTitle("tangible");
        model.addAttribute("post", post);
        return "posts/search";
    }

}
