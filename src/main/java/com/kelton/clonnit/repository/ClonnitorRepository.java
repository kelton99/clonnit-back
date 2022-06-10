package com.kelton.clonnit.repository;

import com.kelton.clonnit.model.Clonnitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClonnitorRepository extends JpaRepository<Clonnitor, Long> {
	Optional<Clonnitor> findByUsername(String username);
}
