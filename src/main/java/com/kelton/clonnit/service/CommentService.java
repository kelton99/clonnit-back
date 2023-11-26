package com.kelton.clonnit.service;

import com.kelton.clonnit.dto.CommentsDto;
import com.kelton.clonnit.exception.ClonnitException;
import com.kelton.clonnit.model.Clonnitor;
import com.kelton.clonnit.model.Comment;
import com.kelton.clonnit.model.Post;
import com.kelton.clonnit.repository.ClonnitorRepository;
import com.kelton.clonnit.repository.CommentRepository;
import com.kelton.clonnit.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ClonnitorRepository clonnitorRepository;
    private final PostRepository postRepository;
    private final AuthService authService;


    public CommentsDto save(final CommentsDto commentsDto) {
        final Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new ClonnitException(commentsDto.getPostId().toString()));
        final Comment comment = this.map(commentsDto, post, authService.getCurrentUser());
        //this.postService.increaseCommentCount(post.getId());
        return this.mapToDto(commentRepository.save(comment));
    }

    public List<CommentsDto> getAllCommentsForPost(final Long postId) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ClonnitException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(this::mapToDto).collect(Collectors.toList());
    }

    public List<CommentsDto> getAllCommentsForClonnitor(final String username) {
        final Clonnitor clonnitor = clonnitorRepository.findByUsername(username)
                .orElseThrow(() -> new ClonnitException(username));
        return commentRepository.findAllByClonnitor(clonnitor)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public void deleteCommentById(final Long id) {
        final Comment comment =  commentRepository.findById(id).orElseThrow(() -> new ClonnitException(id.toString()));
        final Clonnitor clonnitor = this.authService.getCurrentUser();
        if (!comment.getClonnitor().getId().equals(clonnitor.getId())) {
            throw new ClonnitException(id.toString());
        }
        //this.postService.decreaseCommentCount(comment.getPost().getId());
        this.commentRepository.deleteById(id);
    }

    public void deleteAllCommentByPost(final Post post) {
        this.commentRepository.deleteAllByPost(post);
    }

    private Comment map(CommentsDto commentsDto, Post post, Clonnitor currentUser) {
        final Comment comment = new Comment();
        comment.setId(commentsDto.getId());
        comment.setText(commentsDto.getText());
        comment.setCreatedDate(commentsDto.getCreatedDate());
        comment.setPost(post);
        comment.setClonnitor(currentUser);
        return comment;
    }

    private CommentsDto mapToDto(Comment comment) {
        final CommentsDto commentsDto = new CommentsDto();
        commentsDto.setId(comment.getId());
        commentsDto.setPostId(comment.getPost().getId());
        commentsDto.setUsername(comment.getClonnitor().getUsername());
        commentsDto.setText(comment.getText());
        return commentsDto;
    }

    public Integer getCommentCount(final Long id) {
        return this.commentRepository.countByPostId(id);
    }
}