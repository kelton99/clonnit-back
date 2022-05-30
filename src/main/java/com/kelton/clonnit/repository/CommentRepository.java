package com.kelton.clonnit.repository;

import com.kelton.clonnit.dto.CommentsDto;
import com.kelton.clonnit.model.Clonnitor;
import com.kelton.clonnit.model.Comment;
import com.kelton.clonnit.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByClonnitor(Clonnitor clonnitor);
}
