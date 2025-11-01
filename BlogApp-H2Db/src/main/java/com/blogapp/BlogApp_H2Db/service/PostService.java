package com.blogapp.BlogApp_H2Db.service;

import com.blogapp.BlogApp_H2Db.model.Post;
import com.blogapp.BlogApp_H2Db.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository; // must be final

    public PostService(PostRepository postRepository) { // Spring injects this
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }
}
