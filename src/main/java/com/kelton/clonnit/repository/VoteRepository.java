package com.kelton.clonnit.repository;

import com.kelton.clonnit.model.Clonnitor;
import com.kelton.clonnit.model.Post;
import com.kelton.clonnit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findTopByPostAndClonnitorOrderByIdDesc(Post post, Clonnitor clonnitor);
}
