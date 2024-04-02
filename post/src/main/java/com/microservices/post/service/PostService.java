package com.microservices.post.service;

import com.microservices.post.entity.Post;
import com.microservices.post.payload.PostDto;
import com.microservices.post.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PostService {
    @Autowired
    private PostRepository postRepo;

    @Autowired
    private RestTemplate restTemplate;

    // Save a new post
    public Post savePost(Post post) {
        // Generate a random postId using UUID
        String randomPostId= UUID.randomUUID().toString();
        post.setId(randomPostId);
        // Save the post to the repository
        Post savePost = postRepo.save(post);
        return savePost;
    }

    // Get a post by its postId
    public Post getPostById(String postId) {
        // Find the post by its postId
        Post post = postRepo.findById(postId).get();
        return post;

    }

    // Get a post with its associated comments
    public PostDto getPostWithComments(String postId) {
        // Find the post by its postId
        Post post=postRepo.findById(postId).get();
        // Get comments for the post from a RESTful API
        ArrayList comments = restTemplate.getForObject("http://COMMENT-SERVICE/api/comments/" + postId, ArrayList.class);
        // Create a PostDto object to hold post and comments information
        PostDto postDto =new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());

        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        postDto.setComments(comments);

        return postDto;
    }

    }

