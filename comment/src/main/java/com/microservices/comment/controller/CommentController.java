package com.microservices.comment.controller;

import com.microservices.comment.entity.Comment;
import com.microservices.comment.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {
    private CommentService commentService;
//http://localhost:8082/api/comments
    @PostMapping
    public ResponseEntity<Comment> saveComment(@RequestBody Comment comment){
        Comment comment1 = commentService.saveComment(comment);
        return new ResponseEntity<>(comment1, HttpStatus.OK);
    }
    //http://localhost:8082/api/comments/postid
    @GetMapping("/{postid}")
    public List<Comment> getAllCommentByPostId(@PathVariable String postid){
        List<Comment> allComment = commentService.getAllComment(postid);
        return allComment;
    }
}