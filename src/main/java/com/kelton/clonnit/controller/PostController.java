package com.kelton.clonnit.controller;

import com.kelton.clonnit.dto.PostRequest;
import com.kelton.clonnit.dto.PostResponse;
import com.kelton.clonnit.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPost() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("/subclonnit/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubclonnit(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsBySubclonnit(id));
    }

    @GetMapping("/clonnitor/{username}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUsername(username));
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.save(postRequest));
    }
}