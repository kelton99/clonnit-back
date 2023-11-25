package com.kelton.clonnit.service;

import com.kelton.clonnit.dto.PostRequest;
import com.kelton.clonnit.dto.PostResponse;
import com.kelton.clonnit.exception.ClonnitException;
import com.kelton.clonnit.model.*;
import com.kelton.clonnit.repository.CommentRepository;
import com.kelton.clonnit.repository.PostRepository;
import com.kelton.clonnit.repository.SubclonnitRepository;
import com.kelton.clonnit.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final AuthService authService;
    private final VoteRepository voteRepository;
    private final SubclonnitRepository subclonnitRepository;
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository, AuthService authService, VoteRepository voteRepository, SubclonnitRepository subclonnitRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.authService = authService;
        this.voteRepository = voteRepository;
        this.subclonnitRepository = subclonnitRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAll() {
        return postRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        final Post post = postRepository.findById(id)
                .orElseThrow(() -> new ClonnitException("Post not found"));
        return this.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubclonnit(Long id) {
        final Subclonnit subclonnit = subclonnitRepository.findById(id).get();
        List<Post> posts = postRepository.findAllBySubclonnit(subclonnit);
        return posts.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        List<Post> posts = postRepository.findAllByClonnitor_Username(username);
        return posts.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public PostResponse save(PostRequest postRequest) {
        final Subclonnit subclonnit = subclonnitRepository.findByName(postRequest.getSubclonnitName())
                .orElseThrow(() -> new ClonnitException("Subclonnit not found"));
        final Post post = this.dtoToPost(postRequest, subclonnit, authService.getCurrentUser());
        postRepository.save(post);
        return this.mapToDto(post);
    }

    @Transactional
    public void deletePostById(final Long id) {
        final Post post =  postRepository.findById(id).orElseThrow(() -> new ClonnitException(id.toString()));
        final Clonnitor clonnitor = this.authService.getCurrentUser();
        if (!post.getClonnitor().getId().equals(clonnitor.getId())) {
            throw new ClonnitException(id.toString());
        }
        this.commentRepository.deleteAllByPost(post);
        this.voteRepository.deleteAllByPost(post);
        this.postRepository.deleteById(id);
    }

    public PostResponse mapToDto(Post post) {
        if(post == null) {
            return null;
        }

        final PostResponse postResponse = new PostResponse();
        postResponse.setId(post.getId());
        postResponse.setPostName(post.getPostName());
        postResponse.setDescription(post.getDescription());
        postResponse.setVoteCount(post.getVoteCount());
        postResponse.setSubclonnitName(post.getSubclonnit().getName());
        postResponse.setUsername(post.getClonnitor().getUsername());
        postResponse.setCommentCount(this.getCommentCount(post));
        postResponse.setUpVote(this.checkVoteType(post, VoteType.UPVOTE));
        postResponse.setDownVote(this.checkVoteType(post, VoteType.DOWNVOTE));
        return postResponse;
    }

    private Integer getCommentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }
    public Post dtoToPost(PostRequest postRequest, Subclonnit subclonnit, Clonnitor clonnitor) {
        final Post post = new Post();
        post.setCreatedDate(new Date());
        post.setDescription(postRequest.getDescription());
        post.setSubclonnit(subclonnit);
        post.setPostName(postRequest.getPostName());
        post.setVoteCount(0);
        post.setClonnitor(clonnitor);
        return post;
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser =
                    voteRepository.findTopByPostAndClonnitorOrderByIdDesc(post,
                            authService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }
}