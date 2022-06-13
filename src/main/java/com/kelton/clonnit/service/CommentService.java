package com.kelton.clonnit.service;

import com.kelton.clonnit.dto.CommentsDto;
import com.kelton.clonnit.exception.ClonnitException;
import com.kelton.clonnit.model.Clonnitor;
import com.kelton.clonnit.model.Comment;
import com.kelton.clonnit.model.Post;
import com.kelton.clonnit.repository.ClonnitorRepository;
import com.kelton.clonnit.repository.CommentRepository;
import com.kelton.clonnit.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ClonnitorRepository clonnitorRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    public CommentService(CommentRepository commentRepository, ClonnitorRepository clonnitorRepository, PostRepository postRepository, AuthService authService) {
        this.commentRepository = commentRepository;
        this.clonnitorRepository = clonnitorRepository;
        this.postRepository = postRepository;
        this.authService = authService;
    }

    public void save(CommentsDto commentsDto) {
        final Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new ClonnitException(commentsDto.getPostId().toString()));
        final Comment comment = this.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ClonnitException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(this::mapToDto).collect(Collectors.toList());
    }

    public List<CommentsDto> getAllCommentsForClonnitor(String username) {
        Clonnitor clonnitor = clonnitorRepository.findByUsername(username)
                .orElseThrow(() -> new ClonnitException(username));
        return commentRepository.findAllByClonnitor(clonnitor)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
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
}