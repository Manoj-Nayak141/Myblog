package com.myblog.service;
import com.myblog.entity.Comment;
import com.myblog.entity.Post;
import com.myblog.exception.ResourceNotFound;
import com.myblog.payload.CommentDto;
import com.myblog.repository.CommentRepository;
import com.myblog.repository.PostRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    public CommentServiceImpl(PostRepository postRepo, CommentRepository commentRepo) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
    }

   private PostRepository postRepo;

   private CommentRepository commentRepo;

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post Not Found With Id:" + postId)
        );

       Comment comment = mapToEntity(commentDto);
       comment.setPost(post);
       Comment c = commentRepo.save(comment);

       return mapToDto(c);

    }

    @Override
    public void deleteCommentById(long postId,long commentId) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post Not Found With Id:" + postId)
        );
        commentRepo.deleteById(commentId);

    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepo.findByPostId(postId);
        List<CommentDto> dtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public CommentDto updateComment(long commentId, CommentDto commentDto) {
        Comment com = commentRepo.findById(commentId).get();
        Post post = postRepo.findById(com.getId()).get();
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        comment.setId(commentId);
        Comment savedComment = commentRepo.save(comment);
        CommentDto dto = mapToDto(savedComment);
        return dto;
    }


    Comment mapToEntity(CommentDto dto){
        Comment comment = new Comment();
        comment.setName(dto.getName());
        comment.setEmail(dto.getEmail());
        comment.setBody(dto.getBody());

        return comment;

    }
    CommentDto mapToDto(Comment comment){
        CommentDto dto = new CommentDto();
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());

        return dto;

    }

}
