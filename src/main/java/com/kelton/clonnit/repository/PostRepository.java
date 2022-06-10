package com.kelton.clonnit.repository;

import com.kelton.clonnit.model.Post;
import com.kelton.clonnit.model.Subclonnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubclonnit(Subclonnit subclonnit);
    List<Post> findAllByClonnitor_Username(String username);

}
