package com.codeup.springblogapp.repositories;

import com.codeup.springblogapp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findByTitle(String title);

    Post findById(long id);

    Post findAll(int i);
}
