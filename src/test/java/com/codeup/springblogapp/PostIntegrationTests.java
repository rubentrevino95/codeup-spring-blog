package com.codeup.springblogapp;

import com.codeup.springblogapp.model.Post;
import com.codeup.springblogapp.model.User;
import com.codeup.springblogapp.repositories.PostRepository;
import com.codeup.springblogapp.repositories.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import javax.servlet.http.HttpSession;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
    @SpringBootTest(classes = SpringblogApplication.class)
    @AutoConfigureMockMvc
    public class PostIntegrationTests {

        private User testUser;
        private HttpSession httpSession;

        @Autowired
        private MockMvc mvc;

        @Autowired
        UserRepository userDao;

        @Autowired
        PostRepository postDao;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Before
        public void setup() throws Exception {

            testUser = userDao.findByUsername("testUser");

            // Creates the test user if not exists
            if(testUser == null){
                User newUser = new User();
                newUser.setUsername("testUser");
                newUser.setPassword(passwordEncoder.encode("pass"));
                newUser.setEmail("testUser@codeup.com");
                testUser = userDao.save(newUser);
            }

            // Throws a Post request to /login and expect a redirection to the Ads index page after being logged in
            httpSession = this.mvc.perform(post("/login").with(csrf())
                    .param("username", "testUser")
                    .param("password", "pass"))
                    .andExpect(status().is(HttpStatus.FOUND.value()))
                    .andExpect(redirectedUrl("/posts"))
                    .andReturn()
                    .getRequest()
                    .getSession();
        }

// SHOW
        @Test
        public void testShowPost() throws Exception {
            Post existingPost = postDao.findAll().get(0);

            this.mvc.perform(get("/posts/" + existingPost.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString(existingPost.getTitle())));

        }
//  CREATE
        @Test
        public void testCreatePost() throws Exception {
            // Makes a Post request to /posts/create and expect a redirection to the Ad
            this.mvc.perform(
                    post("/posts/create").with(csrf())
                            .session((MockHttpSession) httpSession)
                            // Add all the required parameters to your request like this
                            .param("title", "test")
                            .param("description", "post"))
                    .andExpect(status().is3xxRedirection());
        }
//  INDEX
        @Test
        public void testPostIndex() throws Exception {
            Post existingPost = postDao.findAll().get(0);

            // Makes a Get request to /posts and verifies that we get some of the static text of the ads/index.html template and at least the title from the first Post is present in the template.
            this.mvc.perform(get("/posts"))
                    .andExpect(status().isOk())
                    // Test the static content of the page
                    .andExpect(content().string(containsString("Latest posts")))
                    // Test the dynamic content of the page
                    .andExpect(content().string(containsString(existingPost.getTitle())));
        }
//  EDIT
        @Test
        public void testEditAd() throws Exception {
            // Gets the first Ad for tests purposes
            Post existingPost = postDao.findAll().get(0);

            // Makes a Post request to /ads/{id}/edit and expect a redirection to the Ad show page
            this.mvc.perform(
                    post("/posts/" + existingPost.getId() + "/edit").with(csrf())
                            .session((MockHttpSession) httpSession)
                            .param("title", "edited title")
                            .param("description", "edited description"))
                            .andExpect(status().is3xxRedirection());

            // Makes a GET request to /ads/{id} and expect a redirection to the Ad show page
            this.mvc.perform(get("/posts/" + existingPost.getId()))
                    .andExpect(status().isOk())
                    // Test the dynamic content of the page
                    .andExpect(content().string(containsString("edited title")))
                    .andExpect(content().string(containsString("edited description")));
        }
//  DELETE
        @Test
        public void testDeletePost() throws Exception {
            // Creates a test Post to be deleted
            this.mvc.perform(
                    post("/posts/create").with(csrf())
                            .session((MockHttpSession) httpSession)
                            .param("title", "post to be deleted")
                            .param("description", "won't last long"))
                            .andExpect(status().is3xxRedirection());

            // Get the recent Ad that matches the title
            Post existingPost = postDao.findByTitle("ad to be deleted");

            // Makes a Post request to /ads/{id}/delete and expect a redirection to the Ads index
            this.mvc.perform(
                    post("/posts/" + existingPost.getId() + "/delete").with(csrf())
                            .session((MockHttpSession) httpSession)
                            .param("id", String.valueOf(existingPost.getId())))
                            .andExpect(status().is3xxRedirection());
        }
    }
