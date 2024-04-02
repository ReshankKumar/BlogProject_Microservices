package com.microservices.comment.service;


import com.microservices.comment.entity.Comment;
import com.microservices.comment.payload.Post;
import com.microservices.comment.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service

public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private RestTemplate restTemplate;
    public Comment saveComment(Comment comment){
        Post post=restTemplate.getForObject("http://POST-SERVICE/api/posts/"+comment.getPostId(), Post.class);
        if(post!=null){
            String commentId1 = UUID.randomUUID().toString();
            comment.setCommentId(commentId1);
            Comment saved = commentRepository.save(comment);
            return saved;
        }
        else{
            return null;
        }

    }

    public List<Comment> getAllComment(String postid) {
        List<Comment> byPostId = commentRepository.findByPostId(postid);
        return byPostId;


    }
}
