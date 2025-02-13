package com.myblog.service;
import com.myblog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    public CommentDto createComment(long postId, CommentDto commentDto);
    public void deleteCommentById(long commentId, long postId);
    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto updateComment(long commentId, CommentDto commentDto);
}
