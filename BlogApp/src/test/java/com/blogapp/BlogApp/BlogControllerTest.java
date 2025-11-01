package com.blogapp.BlogApp;

import com.blogapp.BlogApp.bean.Post;
import com.blogapp.BlogApp.controller.BlogController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BlogController.class)
class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getPost_returnsSinglePost_withCustomHeader() throws Exception {
        mockMvc.perform(get("/blog/post"))
                .andExpect(status().isOk())
                .andExpect(header().string("custom-header", "cc"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("First Post"));
    }

    @Test
    void getAllPost_returnsList() throws Exception {
        mockMvc.perform(get("/blog/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].title", containsString("Tech News")));
    }

    @Test
    void createPost_returns201_andEchoesBody() throws Exception {
        Post req = new Post(99L, "New Post", "Hello", "Me");
        mockMvc.perform(post("/blog/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(99))
                .andExpect(jsonPath("$.title").value("New Post"));
    }

    @Test
    void updatePost_returns200() throws Exception {
        Post req = new Post(5L, "Updated", "Updated content", "Admin");
        mockMvc.perform(put("/blog/5/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(content().string("Post updated successfully"));
    }

    @Test
    void deletePost_returns200() throws Exception {
        mockMvc.perform(delete("/blog/5/delete"))
                .andExpect(status().isOk())
                .andExpect(content().string("Post deleted successfully"));
    }

    @Test
    void methodNotAllowed_forPostingToWrongPath() throws Exception {
        mockMvc.perform(post("/blog/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isMethodNotAllowed());
    }
}