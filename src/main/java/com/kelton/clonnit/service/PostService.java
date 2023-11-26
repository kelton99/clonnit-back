package com.kelton.clonnit.service;

import com.kelton.clonnit.dto.PostRequest;
import com.kelton.clonnit.dto.PostResponse;
import com.kelton.clonnit.dto.SubclonnitResponse;
import com.kelton.clonnit.exception.ClonnitException;
import com.kelton.clonnit.model.Clonnitor;
import com.kelton.clonnit.model.Post;
import com.kelton.clonnit.model.Vote;
import com.kelton.clonnit.model.VoteType;
import com.kelton.clonnit.repository.PostRepository;
import com.kelton.clonnit.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AuthService authService;
    private final VoteService voteService;
    private final SubclonnitService subclonnitService;
    private final CommentService commentService;
    private final VoteRepository voteRepository;

    @Transactional(readOnly = true)
    public List<PostResponse> getAll() {
        return postRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(final Long id) {
        final var post = postRepository.findById(id)
                .orElseThrow(() -> new ClonnitException("Post not found"));
        return this.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubclonnit(final Long id) {
        final var posts = postRepository.findAllBySubclonnitId(id);
        return posts.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(final String username) {
        final var posts = postRepository.findAllByClonnitor_Username(username);
        return posts.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public PostResponse save(final PostRequest postRequest) {
        final var subclonnit = subclonnitService.getSubclonnit(postRequest.getSubclonnitId());
        final var post = this.dtoToPost(postRequest, subclonnit, authService.getCurrentUser());
        this.subclonnitService.increasePostCount(postRequest.getSubclonnitId());
        return this.mapToDto(postRepository.save(post));
    }

    @Transactional
    public void deletePostById(final Long id) {
        final var post = postRepository.findById(id)
                .orElseThrow(() -> new ClonnitException(id.toString()));
        final var clonnitor = this.authService.getCurrentUser();

        if (!post.getClonnitor().getId().equals(clonnitor.getId())) {
            throw new ClonnitException(id.toString());
        }

        this.commentService.deleteAllCommentByPost(post);
        this.voteService.deleteAllVotesByPost(post);
        this.subclonnitService.decreasePostCount(post.getSubclonnit().getId());
        this.postRepository.deleteById(id);
    }

    public PostResponse mapToDto(Post post) {
        if (post == null) {
            return null;
        }

        final PostResponse postResponse = new PostResponse();
        postResponse.setId(post.getId());
        postResponse.setPostName(post.getPostName());
        postResponse.setDescription(post.getDescription());
        postResponse.setVoteCount(post.getVoteCount());
        postResponse.setSubclonnitName(post.getSubclonnit().getName());
        postResponse.setUsername(post.getClonnitor().getUsername());
        postResponse.setCommentCount(this.getCommentCount(post.getId()));
        postResponse.setUpVote(this.checkVoteType(post, VoteType.UPVOTE));
        postResponse.setDownVote(this.checkVoteType(post, VoteType.DOWNVOTE));
        return postResponse;
    }

    private Integer getCommentCount(Long id) {
        return commentService.getCommentCount(id);
    }

    public Post dtoToPost(PostRequest postRequest, SubclonnitResponse subclonnitResponse, Clonnitor clonnitor) {
        final Post post = new Post();
        post.setCreatedDate(new Date());
        post.setDescription(postRequest.getDescription());
        post.setSubclonnit(SubclonnitResponse.mapToSubclonnit(subclonnitResponse));
        post.setPostName(postRequest.getPostName());
        post.setVoteCount(0);
        post.setClonnitor(clonnitor);
        return post;
    }

    public boolean checkVoteType(Post post, VoteType voteType) {
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