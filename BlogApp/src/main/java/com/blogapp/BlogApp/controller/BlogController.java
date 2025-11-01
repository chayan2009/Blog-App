package com.blogapp.BlogApp.controller;

import com.blogapp.BlogApp.bean.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @GetMapping("/post")
    public ResponseEntity<Post> getPost() {
        Post _Post = new Post(1L, "First Post", "This is the content of the first post.", "Admin");
        return ResponseEntity.ok().header("custom-header", "cc").body(_Post);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPost() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1L, "Tech News", "AI is transforming the software industry at scale.", "TechWriter"));
        posts.add(new Post(2L, "Weekly Report", "Summary of all activities completed this week.", "Manager"));
        posts.add(new Post(3L, "Event Announcement", "Join us for the developer conference next month.", "Admin"));
        posts.add(new Post(4L, "Product Release", "Version 2.0 is now live with enhanced security!", "ProductTeam"));
        posts.add(new Post(5L, "Maintenance Notice", "Scheduled downtime on Sunday 2 AM - 4 AM.", "OpsTeam"));
        return ResponseEntity.ok(posts);
    }

    @PostMapping(path = "/post", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<String> updatePost(@PathVariable Long id, @RequestBody Post post) {
        return ResponseEntity.ok("Post updated successfully");
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        return ResponseEntity.ok("Post deleted successfully");
    }
}
