package com.blogapp.BlogApp_H2Db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.BlogApp_H2Db.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
