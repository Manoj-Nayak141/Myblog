package com.myblog.controller;
import com.myblog.payload.CommentDto;
import com.myblog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8080/api/comments?postId=1
    //http://localhost:8080/api/comments/1
    //@PostMapping("/{postId}")
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestParam long postId , @RequestBody CommentDto commentDto){
        CommentDto dto = commentService.createComment(postId, commentDto);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
//    http://localhost:8080/api/comments?postId=&commentId=
    @DeleteMapping
    public ResponseEntity<String> deleteCommentById(@RequestParam long postId , @RequestParam long commentId){
        commentService.deleteCommentById(postId,commentId);
        return new ResponseEntity<>("comment is deleted", HttpStatus.OK);
    }
    //http://localhost:8080/api/comments?postId=1
    @GetMapping
    public List<CommentDto> getCommentsByPostId(@RequestParam long postId){
        List<CommentDto> commentDtos = commentService.getCommentsByPostId(postId);
        return commentDtos;
    }
    //http://localhost:8080/api/comments?commentId=1
    @PutMapping
    public ResponseEntity<CommentDto> updateComment(@RequestParam long commentId , @RequestBody CommentDto commentDto){
        CommentDto dto = commentService.updateComment(commentId , commentDto);
        return new ResponseEntity<>(dto , HttpStatus.OK);
    }
}
