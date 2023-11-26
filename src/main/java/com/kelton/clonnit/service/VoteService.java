package com.kelton.clonnit.service;

import com.kelton.clonnit.dto.VoteDto;
import com.kelton.clonnit.exception.ClonnitException;
import com.kelton.clonnit.model.Post;
import com.kelton.clonnit.model.Vote;
import com.kelton.clonnit.model.VoteType;
import com.kelton.clonnit.repository.PostRepository;
import com.kelton.clonnit.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        final Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new ClonnitException("Post not found"));
        Optional<Vote> voteByPostAndUser = voteRepository
                .findTopByPostAndClonnitorOrderByIdDesc(post, authService.getCurrentUser());

        if (voteByPostAndUser.isPresent()) {
            if (isTheSameVoteType(voteByPostAndUser.get(), voteDto)) {
                cancelVote(post, voteDto, voteByPostAndUser.get());
            } else {
                switchVote(post, voteDto, voteByPostAndUser.get());
            }
            return;
        }

        if (VoteType.UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }

        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private boolean isTheSameVoteType(Vote voteByPostAndUser, VoteDto voteDto) {
        return voteByPostAndUser.getVoteType().equals(voteDto.getVoteType());
    }

    private void cancelVote(Post post, VoteDto voteDto, Vote vote) {
        if (VoteType.UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() - 1);
        } else {
            post.setVoteCount(post.getVoteCount() + 1);
        }
        this.voteRepository.deleteById(vote.getId());
        this.postRepository.save(post);
    }

    private void switchVote(Post post, VoteDto voteDto, Vote vote) {
        if (VoteType.UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 2);
            vote.setVoteType(VoteType.UPVOTE);
        } else {
            post.setVoteCount(post.getVoteCount() - 2);
            vote.setVoteType(VoteType.DOWNVOTE);
        }
        voteRepository.save(vote);
        postRepository.save(post);
    }

    public void deleteAllVotesByPost(Post post) {
        this.voteRepository.deleteAllByPost(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        final Vote vote = new Vote();
        vote.setVoteType(voteDto.getVoteType());
        vote.setPost(post);
        vote.setClonnitor(authService.getCurrentUser());
        return vote;
    }
}