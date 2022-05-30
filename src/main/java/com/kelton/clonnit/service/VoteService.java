package com.kelton.clonnit.service;

import com.kelton.clonnit.dto.VoteDto;
import com.kelton.clonnit.exception.ClonnitException;
import com.kelton.clonnit.model.Post;
import com.kelton.clonnit.model.Vote;
import com.kelton.clonnit.model.VoteType;
import com.kelton.clonnit.repository.PostRepository;
import com.kelton.clonnit.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    public VoteService(VoteRepository voteRepository, PostRepository postRepository, AuthService authService) {
        this.voteRepository = voteRepository;
        this.postRepository = postRepository;
        this.authService = authService;
    }

    @Transactional
    public void vote(VoteDto voteDto) {
        final Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new ClonnitException("Post not found"));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndClonnitorOrderByIdDesc(post, authService.getCurrentUser());

        if(voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new ClonnitException("You have voted already");
        }
        if(VoteType.UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }

        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        final Vote vote = new Vote();
        vote.setVoteType(voteDto.getVoteType());
        vote.setPost(post);
        vote.setClonnitor(authService.getCurrentUser());
        return vote;
    }
}