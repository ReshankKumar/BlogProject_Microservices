package com.microservices.post.controller;

import com.microservices.post.entity.Post;
import com.microservices.post.payload.PostDto;
import com.microservices.post.service.PostService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

        @Autowired
        private PostService postService;


        @PostMapping
        public ResponseEntity<Post> savePost(@RequestBody Post post){
            Post savePost = postService.savePost(post);

            return new ResponseEntity<>(savePost, HttpStatus.CREATED);
        }


        // http://localhost:8081/api/post/{postId}
        @GetMapping("/{postId}")
        public Post getPostById(@PathVariable String postId){
            Post postById = postService.getPostById(postId);
            return postById;
        }

        // http://localhost:8081/api/post/{postId}/comments
        @GetMapping("/{postId}/comments")
        @CircuitBreaker(name="commentBreaker", fallbackMethod = "commentFallback")
        public ResponseEntity<PostDto> getPostWithComments(@PathVariable String postId){
            PostDto postDto = postService.getPostWithComments(postId);
            return new ResponseEntity<>(postDto,HttpStatus.OK);
        }

        public ResponseEntity<PostDto> commentFallback(String postId, Exception ex){
            System.out.print("Fallback is execuited because service is down: "+ex.getMessage());
            ex.printStackTrace();

            PostDto dto=new PostDto();
            dto.setId("1234");
            dto.setTitle("service down");
            dto.setContent("Service Down");
            return new ResponseEntity<>(dto,HttpStatus.BAD_REQUEST);
        }

    }
